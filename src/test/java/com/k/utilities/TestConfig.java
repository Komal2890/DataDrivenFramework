package com.k.utilities;

public class TestConfig{


	
	public static String server="smtp.gmail.com";
	public static String from = "komal.choudhary2811@gmail.com";
	public static String password = "Tushar@2811";
	public static String[] to ={"komalchoudhary2212@gmail.com"};
	public static String subject = "Test Report";
	
	public static String messageBody ="TestMessage";
	public static String attachmentPath="C:\\Users\\Komal\\Desktop\\shots";
	public static String attachmentName="Error.jpg";
	
	
	
	//SQL DATABASE DETAILS	
	public static String driver="net.sourceforge.jtds.jdbc.Driver"; 
	public static String dbConnectionUrl="jdbc:jtds:sqlserver://192.168.1.103;DatabaseName=monitor_eval"; 
	public static String dbUserName="sa"; 
	public static String dbPassword="$ql$!!1"; 
	
	
	//MYSQL DATABASE DETAILS
	public static String mysqldriver="com.mysql.cj.jdbc.Driver";
	public static String mysqluserName = "root";
	public static String mysqlpassword = "selenium";
	public static String mysqlurl = "jdbc:mysql://localhost:3306/batchoct2021";
	
	
}
