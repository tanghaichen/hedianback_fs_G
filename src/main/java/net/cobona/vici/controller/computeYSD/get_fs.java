package net.cobona.vici.controller.computeYSD;

import java.util.List;

import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.operators.FilterOperator;
import org.apache.flink.api.java.operators.IterativeDataSet;
import org.apache.flink.api.java.operators.MapOperator;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import net.cobona.vici.controller.computeYSD.pojo._7axial_force;

/**
 * @ClassName: get_fs
 * @Description:(通过传参和迭代计算最终值fs)
 * @author: 96427
 * @date: 2019年4月17日 下午12:13:10
 * @version 2.0
 * @Copyright: 2019 www.cobona.net Inc. All rights reserved.
 */
public class get_fs {
	
	
	public static double getCap_pier_sum(boolean type, List<JSONObject> pierData, Double tw, double fs, Double fcm,
			Double fym, Double vEQ, int βu_M, int βu_V, List<JSONObject> PseL, List<Double> NEQM_pierL,
			List<Double> NEQf_pierL, List<Double> NS_pierL, Double K1, Double K2, Double K3, int fs_type) {


		double Vcap_pier_sum = 0;
		
		for(int count=0;count<NEQf_pierL.size();count++) {
			
			double NEQf_pieri = NEQf_pierL.get(count);// kn
			double NEQM_pieri = NEQM_pierL.get(count);// kn
			double NS_pieri = NS_pierL.get(count);// kn

			Double l = pierData.get(count).getDouble("Lpier");
			Double h = pierData.get(count).getDouble("Hpier");

			double Ac = l * tw;
			double V_type = 0;
			if (fs_type != 1) {
				V_type =  pierData.get(count).getDouble("V_type");
			}

			double Psei = PseL.get(count).getDouble("Psei");
			double Asv = PseL.get(count).getDouble("Asv");
			double Pvi = PseL.get(count).getDouble("Pv");

			//根据type值取Npier值计算公式
			double Npier;
			if (type) {
				Npier = NS_pieri - (fs * (NEQf_pieri + NEQM_pieri));// 56478
			} else {
				Npier = NS_pieri - (fs * (NEQf_pieri - NEQM_pieri));// 56478
			}
			double Vcap_pier = 0;
			//根据fs_type值确定Vcap_pier值
			if (fs_type == 1) {// 原本
				// 计算三个值，最小值为cap_pier
				double VVpier = VVBarda(fcm, fym, h, l, tw, Npier, Psei);// hltp n fc_fy
				double VMpier = VM(Asv, fym, Npier, fcm, tw, l, h);
				double VFpier = VF(Ac, fcm, fym, Npier, Pvi, K1, K2, K3);
				Vcap_pier = Math.min(Math.min(VVpier, VMpier), VFpier);
			} else if (fs_type == 2) {// true
					//根据V_type值确定vcap_pier值确定哪个值
				if (V_type == 1.1) {
					Vcap_pier = VM(Asv, fym, Npier, fcm, tw, l, h) * Math.exp(-βu_M);
				} else if (V_type == 2.2) {
					Vcap_pier = VVBarda(fcm, fym, h, l, tw, Npier, Psei) * Math.exp(-βu_V);
				} else if (V_type == 3.3) {
					Vcap_pier = VF(Ac, fcm, fym, Npier, Pvi, K1, K2, K3);
				}
				
			} else {// false
				if (V_type == 1.1) {
					Vcap_pier = VM(Asv, fym, Npier, fcm, tw, l, h);
				} else if (V_type == 2.2) {
					Vcap_pier = VVBarda(fcm, fym, h, l, tw, Npier, Psei);
				} else if (V_type == 3.3) {
					Vcap_pier = VF(Ac, fcm, fym, Npier, Pvi, K1, K2, K3);
				}
			}

			Vcap_pier_sum+=Vcap_pier;
			
		}
		
		
		
		
		return Vcap_pier_sum;
	}


	
	
	/**
	 * 
	 * @param type     根据type选择Npier值
	 * @param alldata  所有数据
	 * @return
	 * @throws Exception
	 */
	public static List<JSONObject> coumpte_fs(boolean type, DataSet<JSONObject> alldata) throws Exception {


		List<JSONObject> collect2 = alldata.collect();
		System.out.println(collect2);
		
		IterativeDataSet<JSONObject> iterate = alldata.iterate(100);
		
		MapOperator<JSONObject, JSONObject> fsIterate = iterate.map(new RichMapFunction<JSONObject, JSONObject>() {

			@Override
			public JSONObject map(JSONObject value) throws Exception {
				
				JSONObject data = value.getJSONObject("data");
				
				_7axial_force result =(_7axial_force) value.get("compute");
				JSONObject fsData = value.getJSONObject("fsData");
				Double fs = fsData.getDouble("fs");
				
				List<JSONObject> pierData = JSON.parseArray(data.getString("pierData"), JSONObject.class);
				Double tw = data.getDouble("tw");
				Double fcm = data.getDouble("fcm");
				Double fym = data.getDouble("fym");
				Double VEQ = data.getDouble("VEQ");
				Double K1 = data.getDouble("K1");
				Double K2 = data.getDouble("K2");
				Double K3 = data.getDouble("K3");
				
				List<Double> NEQf_pierL = result.getNEQf_pier();
				List<Double> NEQM_pierL = result.getNEQM_pier();
				List<Double> NS_pierL = result.getNS_pier();
				List<JSONObject> pseL = result.getPse();
				
				
				double Vcap_pier_sum = get_fs.getCap_pier_sum(type, pierData, tw, fs, fcm, fym, VEQ, 0, 0,pseL, NEQM_pierL, NEQf_pierL, NS_pierL, K1, K2, K3, 1);
				
				double new_fs=Vcap_pier_sum/VEQ;
				
				fsData.put("fs", new_fs);
				fsData.put("cha",Math.abs(new_fs - fs));

				return value;
			}
		});
		
		//终止条件
		  FilterOperator<JSONObject> filter = fsIterate.filter(new FilterFunction<JSONObject>() {

			@Override
			public boolean filter(JSONObject value) throws Exception {
				Double cha = value.getJSONObject("fsData").getDouble("cha");
				if(cha<0.01) {
					return false;
				}else {
					return true;
				}
			}

		});
		
	DataSet<JSONObject> fsResult = iterate.closeWith(fsIterate, filter);
	List<JSONObject> collect = fsResult.collect();
	return collect;
		
	}


	
	
	
	
	
	public static double VM(double Asv, double fym, double Npier, double fcm, double tw, double l, double h) {

		double a = ((Asv * fym) + Npier) / (0.85 * fcm * tw);// 3108.96

		double MMpier = (Asv * fym + Npier) * ((l / 2) - a / 2);

		return MMpier * 2 / h;
	}

	public static double VVBarda(double fcm, double fym, double h, double l, double tw, double N, double psei) {

		return VVBarda_C(fcm, h, l, tw, N) + psei * fym * tw * l * 0.6;
	}

	public static double VVBarda_C(double fcm, double h, double l, double tw, double Npier) {

		return ((Math.sqrt(fcm) * 8.3) - (3.4 * Math.sqrt(fcm) * ((h / l) - 0.5)) + (Npier / (4 * l * tw))) * 0.6 * l
				* tw;
	}

	public static double VF(double ac, double fcm, double fym, double N, double pvi, double k1, double k2, double k3) {
		double tempData;

		if (1.45 * ((ac * pvi * fym) + N) > k1 * ac) {
			tempData = (Math.min(((pvi * fym + (N / ac)) * 0.8) + k1, Math.min(k2 * fcm, k3))) * ac;
		} else {
			tempData = Math.max(2.25 * ((ac * pvi * fym) + N), 0);
		}

		return tempData;
	}
	
	
	
	
	
	
	
	
	
	
	
	
}