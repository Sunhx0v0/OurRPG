package application;

import java.sql.*;

public class DBOperator {
	private String DBDriver = "net.ucanaccess.jdbc.UcanaccessDriver"; // 数据库 JDBC-ODBC 装载
	private String DBUrl="jdbc:ucanaccess://application/Mytestdatabase.accdb";
    private String DBUser="", DBPassword="";
    Connection con;
    
    public DBOperator(){
    	try {
    		//加载驱动程序
			Class.forName(DBDriver);
    		//通过驱动程序管理器建立与数据源的连接
            con = DriverManager.getConnection(DBUrl, DBUser, DBPassword);
    	}
    	catch(ClassNotFoundException e) {
    	    System.out.println("错误：" + e);
    	}
    	catch(SQLException e) {
    	    System.out.println("错误：" + e);
    	}
	}
    
    public ResultSet sqlQuery(String sql) {
    	//创建执行查询的Statement对象
		try {
			Statement stmt = con.createStatement();
			//Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			
			//SQL语句，用于查询学生表中的学号、姓名、年龄、籍贯和系别
			//System.out.println("正在执行sql语句："+sql);
			
			//执行查询，查询结果放在ResultSet的对象中
			ResultSet rs = stmt.executeQuery(sql);
			
			stmt.close();
			return rs;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
    }

	public Connection getConnnect() {
		return con;
	}
    
}
