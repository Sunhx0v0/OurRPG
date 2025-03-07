package application;

import java.sql.*;

public class DBOperator {
	private String DBDriver = "net.ucanaccess.jdbc.UcanaccessDriver"; // ���ݿ� JDBC-ODBC װ��
	private String DBUrl="jdbc:ucanaccess://application/Mytestdatabase.accdb";
    private String DBUser="", DBPassword="";
    Connection con;
    
    public DBOperator(){
    	try {
    		//������������
			Class.forName(DBDriver);
    		//ͨ�������������������������Դ������
            con = DriverManager.getConnection(DBUrl, DBUser, DBPassword);
    	}
    	catch(ClassNotFoundException e) {
    	    System.out.println("����" + e);
    	}
    	catch(SQLException e) {
    	    System.out.println("����" + e);
    	}
	}
    
    public ResultSet sqlQuery(String sql) {
    	//����ִ�в�ѯ��Statement����
		try {
			Statement stmt = con.createStatement();
			//Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			
			//SQL��䣬���ڲ�ѯѧ�����е�ѧ�š����������䡢�����ϵ��
			//System.out.println("����ִ��sql��䣺"+sql);
			
			//ִ�в�ѯ����ѯ�������ResultSet�Ķ�����
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
