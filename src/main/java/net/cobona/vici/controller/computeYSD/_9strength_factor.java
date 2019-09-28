package net.cobona.vici.controller.computeYSD;

import java.util.List;

import org.apache.flink.api.java.DataSet;

import com.alibaba.fastjson.JSONObject;


/**
 * @ClassName: _9strength_factor
 * @Description:TODO(6.11.4.9 强度因子计算)
 * @author: 96427
 * @date: 2019年4月18日 上午10:52:24
 * @version 2.0
 * @Copyright: 2019 www.cobona.net Inc. All rights reserved.
 */
public class _9strength_factor {
	/**
	 * 
	 * @Title: compute   
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @author: 96427
	 * @date:   2019年4月25日 下午2:30:31    
	 * @param: @param pierData     墙体信息
	 * @param: @param tw 		     墙体厚度
	 * @param: @param fs		     初始值
	 * @param: @param fcm          混凝土抗拉强度
	 * @param: @param fym          钢筋抗拉强度
	 * @param: @param VEQ          平面地震剪力
	 * @param: @param Pse          墙片的竖向钢筋面积及墙片指数Asv   Pse
	 * @param: @param NEQM_pier    轴向力(turning moment)
	 * @param: @param NEQF_pier    轴向力(地震垂直压力)NEQf_pier
	 * @param: @param NS_pier      轴向力（非地震载荷）Nspier 
	 * @param: @param k1
	 * @param: @param k2
	 * @param: @param k3
	 * @param: @return
	 * @param: @throws Exception      
	 * @return: _9strength_factor_pojo      
	 * @throws
	 */

	public static List<JSONObject> compute(DataSet<JSONObject> alldata) throws Exception {
		//分别计算两个不同公式的fs值，取最小值和公式标志符
		
		List<JSONObject> alldata1 = get_fs.coumpte_fs(true,alldata);
		List<JSONObject> alldata2 = get_fs.coumpte_fs(false,alldata);
		
		//alldata1和alldata2中各有多个墙，需要对比的是对应的墙的fs
		
		for(int i=0;i<alldata1.size();i++) {
			boolean type;
			double FS111;
			Double fs1 = alldata1.get(i).getJSONObject("fsData").getDouble("fs");
			Double fs2 = alldata2.get(i).getJSONObject("fsData").getDouble("fs");


			
			if (fs1 < fs2) {
				type = true;
				FS111 = fs1;
			} else {
				FS111 = fs2;
				type = false;
			}

			
			alldata1.get(i).getJSONObject("fsData").put("FS111", FS111);
			alldata1.get(i).getJSONObject("fsData").put("type", type);
			
			
			
			
		}

		return alldata1;

	}


}
