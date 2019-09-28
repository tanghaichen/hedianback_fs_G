package net.cobona.vici.controller.computeYSD;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class RestUtils {

	
	public  String doRequest(String location , String requestMethod , String params ,String encoding) throws Exception{
	    URL url = new URL(location);
		HttpURLConnection connection =(HttpURLConnection) url.openConnection();
		connection.setRequestMethod(requestMethod); // 设置请求方式 GET、POST 等
		connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		connection.setRequestProperty("Connection", "Keep-Alive");
		connection.setConnectTimeout(20000); // 设置连接超时时间，单位：ms 。
		connection.setReadTimeout(20000); // 设置读取超时时间，单位：ms 。
		connection.setDoInput(true); // 设置打开输入流 ： default = true 
		connection.setDoOutput(true); // 设置打开输出流：default = false
		connection.setUseCaches(false); // 设置是否启用用户缓存： default = false
		OutputStream outputStream = connection.getOutputStream(); // 获取输出流对象，准备往服务器写数据
		outputStream.write(params.getBytes());
		outputStream.flush();
		outputStream.close();
			
		// 获取服务器返回的响应状态
		if(connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
			System.out.println(" request fail ...");
			return connection.getResponseMessage();
		}
			
		// 读取服务器返回的数据
		BufferedReader reader = new BufferedReader(new		 	 
	 InputStreamReader(connection.getInputStream(), encoding));
			String line ;
			StringBuffer sb = new StringBuffer();
			while((line = reader.readLine()) != null) {
				sb.append(line);
			}
			reader.close();
			String result = sb.toString();
			System.out.println(result);
			return result ;
		}
	public static void main(String[] args) {
	}

}
