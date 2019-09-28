package net.cobona.vici.controller.computeYSD.pojo;

import java.util.List;


import com.alibaba.fastjson.JSONObject;

/**   
 * @ClassName:  axial_force   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 96427
 * @date:   2019年4月15日 上午9:25:41   
 * @version 2.0 
 * @Copyright: 2019 www.cobona.net Inc. All rights reserved. 
 */
public class _7axial_force {

private double ApierSum;
private List<Double> NEQM_pier;
private List<Double> NEQf_pier;
private List<Double> Rpier;
private List<Double> NS_pier;
private List<JSONObject> Pse;
private double Itot;
public double getApierSum() {
	return ApierSum;
}
public void setApierSum(double apierSum) {
	ApierSum = apierSum;
}
public List<Double> getNEQM_pier() {
	return NEQM_pier;
}
public void setNEQM_pier(List<Double> nEQM_pier) {
	NEQM_pier = nEQM_pier;
}
public List<Double> getNEQf_pier() {
	return NEQf_pier;
}
public void setNEQf_pier(List<Double> nEQf_pier) {
	NEQf_pier = nEQf_pier;
}
public List<Double> getRpier() {
	return Rpier;
}
public void setRpier(List<Double> rpier) {
	Rpier = rpier;
}
public double getItot() {
	return Itot;
}
public void setItot(double itot) {
	Itot = itot;
}

public List<Double> getNS_pier() {
	return NS_pier;
}
public void setNS_pier(List<Double> nS_pier) {
	NS_pier = nS_pier;
}
public List<JSONObject> getPse() {
	return Pse;
}
public void setPse(List<JSONObject> pse) {
	Pse = pse;
}
@Override
public String toString() {
	return "_7axial_force [ApierSum=" + ApierSum + ", NEQM_pier=" + NEQM_pier + ", NEQf_pier=" + NEQf_pier + ", Rpier="
			+ Rpier + ", NS_pier=" + NS_pier + ", Pse=" + Pse + ", Itot=" + Itot + "]";
}


	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/*
	private   DataSet<Tuple2<String,Double>> ApierSum;
	private  DataSet<Tuple2<String,Double>> NEQM_pier;
	private  DataSet<Tuple2<String,Double>> NEQf_pier;
	private  DataSet<Tuple2<String,Double>> rpier;
	private  DataSet<Tuple2<String,Double>> Itot;
	private  DataSet<Tuple2<String,Double>> NEQ_totSet;
	public DataSet<Tuple2<String, Double>> getApierSum() {
		return ApierSum;
	}
	public void setApierSum(DataSet<Tuple2<String, Double>> apierSum) {
		ApierSum = apierSum;
	}
	public DataSet<Tuple2<String, Double>> getNEQM_pier() {
		return NEQM_pier;
	}
	public void setNEQM_pier(DataSet<Tuple2<String, Double>> nEQM_pier) {
		NEQM_pier = nEQM_pier;
	}
	public DataSet<Tuple2<String, Double>> getNEQf_pier() {
		return NEQf_pier;
	}
	public void setNEQf_pier(DataSet<Tuple2<String, Double>> nEQf_pierSet) {
		NEQf_pier = nEQf_pierSet;
	}
	public DataSet<Tuple2<String, Double>> getRpier() {
		return rpier;
	}
	public void setRpier(DataSet<Tuple2<String, Double>> rpier) {
		this.rpier = rpier;
	}
	public DataSet<Tuple2<String, Double>> getItot() {
		return Itot;
	}
	public void setItot(DataSet<Tuple2<String, Double>> itot) {
		Itot = itot;
	}
	public DataSet<Tuple2<String, Double>> getNEQ_totSet() {
		return NEQ_totSet;
	}
	public void setNEQ_totSet(DataSet<Tuple2<String, Double>> nEQ_totSet) {
		NEQ_totSet = nEQ_totSet;
	}
	@Override
	public String toString() {
		return "axial_force [ApierSum=" + ApierSum + ", NEQM_pier=" + NEQM_pier + ", NEQf_pier=" + NEQf_pier
				+ ", rpier=" + rpier + ", Itot=" + Itot + ", NEQ_totSet=" + NEQ_totSet + "]";
	}
	
	*/
	
	
	
	
	
	
	
	
	
	
}
