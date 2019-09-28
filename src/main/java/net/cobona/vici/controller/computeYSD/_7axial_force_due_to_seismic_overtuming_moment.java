package net.cobona.vici.controller.computeYSD;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;

import net.cobona.vici.controller.computeYSD.pojo._7axial_force;

/**
 * 
 * @ClassName:  demo61147   
 * @Description:TODO(地震荷载弯矩引起轴力计算)   
 * @author: thc
 * @date:   2019年4月8日 上午11:16:56   
 * @version 2.0 
 * @Copyright: 2019 www.cobona.net Inc. All rights reserved.
 */
public class _7axial_force_due_to_seismic_overtuming_moment {
/**
 * @Title: compute   
 * @Description: TODO(计算轴向力)
 * @author: 96427
 * @date:   2019年4月25日 上午11:43:56    
 * @param: @param flangeData    翼缘信息
 * @param: @param pierData		墙片信息
 * @param: @param tw			墙体厚度
 * @param: @param NEQ_wall		
 * @param: @param MEQ_inf		地震作用下弯矩引起轴力
 * @param: @return    ApierSum   轴向力(turning moment)NEQM_pier  轴向力(地震垂直压力)NEQf_pier  墙中心到每片墙中心的距离rpier   惯性矩Itot
 * @param: @throws Exception      
 * @return: _7axial_force      
 * @throws
 */
	

	
	
	public static _7axial_force compute(List<JSONObject> flangeData,List<JSONObject> pierData, double NS_wall,List<Double> pv, double ph,
			double tw, double NEQ_wall, double MEQ_inf){
		
		double AXpierSum=0d;
		double ApierSum=0d;
		
		
		double AXflangeSum=0d;
		double AflangeSum=0d;
		
		double IpierSum=0;
		double IflangeSum=0;
		
		List<JSONObject> Pse = new ArrayList<>();
		List<Double> NEQf_pier = new ArrayList<>();
		List<Double> NEQM_pier = new ArrayList<>();
		List<Double> Rpier = new ArrayList<>();
		List<Double> NS_pier = new ArrayList<>();
		
	//计算xcent参数
for(int i=0;i<pierData.size();i++) {
	double Apier = pierData.get(i).getDouble("Apier");
	double Xpier = pierData.get(i).getDouble("Xpier");
	double AXpier=Apier*Xpier;
	AXpierSum+=AXpier;
	ApierSum+=Apier;
	
	
	
	//_6放在此
	Double Hpier = pierData.get(i).getDouble("Hpier");
	Double Lpier = pierData.get(i).getDouble("Lpier");
	Double Pvi = pv.get(i);
	//墙片的竖向钢筋面积
	Double Asv = Pvi * tw * Lpier;
	//墙片指数
	Double Psei = Pvi * Math.min(Math.max(1.5 - Hpier / Lpier, 0), 1)
			+ ph * Math.min(Math.max(Hpier / Lpier - 0.5, 0), 1);
	JSONObject pse = new JSONObject();
	pse.put("Psei", Psei);
	pse.put("Asv", Asv);
	pse.put("Pv",Pvi);
	Pse.add(pse);
	
}

if(flangeData.isEmpty()||flangeData==null) {
	flangeData=new ArrayList<>();
}
for(int i=0;i<flangeData.size();i++) {
	double Aflange = flangeData.get(i).getDouble("Aflange");
	double Xflange = flangeData.get(i).getDouble("Xflange");
	double AXflange=Aflange*Xflange;
	AXflangeSum+=AXflange;
	AflangeSum+=Aflange;
}
//获得xcent
double Xcent=(AXpierSum+AXflangeSum)/(ApierSum+AflangeSum);

System.out.println(Xcent);
System.out.println("---");

for(int i=0;i<flangeData.size();i++) {
	double Xflange = flangeData.get(i).getDouble("Xflange");
	double bf=flangeData.get(i).getDouble("bf");
	double tf=flangeData.get(i).getDouble("tf");
	double Aflange = flangeData.get(i).getDouble("Aflange");
	
	double Rflange=Xflange-Xcent;
	//计算翼缘惯性矩之和
	double Iflange =(bf*Math.pow(tf,3))/12 + Aflange*Rflange*Rflange;
	IflangeSum+=Iflange;
}

for(int i=0;i<pierData.size();i++) {
	double Xpier = pierData.get(i).getDouble("Xpier");
	double Lpier = pierData.get(i).getDouble("Lpier");
	double Apier = pierData.get(i).getDouble("Apier");
	
	double Rpieri=Xpier-Xcent;
	Rpier.add(Rpieri);
	//pierData.get(i).put("Rpier", Rpier);
	
	//计算 墙中心到每片墙中心的距离rpier
	//double rpierData=Xpier-Xcent;
	
	//计算墙片惯性矩之和
	double Ipier= tw* Math.pow(Lpier, 3)/12 +Apier*Rpieri*Rpieri;
	IpierSum+=Ipier;
}


for(int i=0;i<pierData.size();i++) {
	 double Rpieri= Rpier.get(i);
	 double Apier= pierData.get(i).getDouble("Apier");

	 //计算轴向力(turning moment)
	 double NEQM_pieri=Apier*Rpieri*MEQ_inf/( IpierSum+IflangeSum);
	
	 //计算轴向力(地震垂直压力)
	 double NEQf_pieri=NEQ_wall*Apier/ApierSum;
	 
	 
	 //_8在此
	 double NS_pieri= NS_wall*Apier/ApierSum;
	 NS_pier.add(NS_pieri);
	 NEQM_pier.add(NEQM_pieri);
	 NEQf_pier.add(NEQf_pieri);
}

double Itot=IpierSum+IflangeSum;


		
		
		
		_7axial_force axial_force = new _7axial_force();

axial_force.setApierSum(ApierSum);
axial_force.setItot(Itot);
axial_force.setNEQf_pier(NEQf_pier);
axial_force.setNEQM_pier(NEQM_pier);
axial_force.setRpier(Rpier);
axial_force.setNS_pier(NS_pier);	
axial_force.setPse(Pse);
		return axial_force;
	}
	
}
