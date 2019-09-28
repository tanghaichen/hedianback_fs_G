package net.cobona.vici.controller.computeYSD.mysqlConfig;

public class mysqlConfig {
	public static String DRIVER_CLASS="com.mysql.jdbc.Driver";
	//public static String SOURCE_DRIVER_URL="jdbc:mysql://10.30.32.141:3306/yisundu?autoReconnect=true&useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull";
	//public static String SOURCE_DRIVER_URL="jdbc:mysql://47.93.32.243:3306/yisundu?autoReconnect=true&useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull";
	public static String SOURCE_DRIVER_URL="jdbc:mysql://172.19.62.61:3306/yisundu?autoReconnect=true&useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull";
	public static String SOURCE_USER="root";
	public static String SOURCE_PASSWORD="root";
	
	public static String SOURCE_SQL_GETCONSTANT="select * from double_constant";
	
	
	
}
