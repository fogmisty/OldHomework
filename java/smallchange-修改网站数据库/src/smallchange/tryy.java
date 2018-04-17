package smallchange;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class tryy{

	public static void main(String[] args){
		
		Array cut = new Array();
		String str = "<br>初审组1审查员1<font color=red>通过</font>(论文选题良好,文献综述良好,论文在本领域种的应用性良好,论文研究方法良好,论文写作良好): ";
		//cut.array1(str);
		System.out.println("CHAIFEN" + cut.array1(str));
		
		//连接
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url="jdbc:mysql://localhost:3306/com_twt_gcss?characterEncoding=utf-8";    //JDBC的URL
			//String url="jdbc:mysql://localhost:3306/Hotel";    //JDBC的URL  
			conn = DriverManager.getConnection(url,"root","toor");
			//Statement stmt = conn.createStatement(); //创建Statement对象
			System.out.println("成功连接到数据库！");
			
			//查询
			String av = "select * from pg_file";
			PreparedStatement ava = conn.prepareStatement(av);
			//执行查询
			ResultSet avai = ava.executeQuery();
			
			String a1 = null, a2 = null;
		    while(avai.next())//不太懂，迷茫了，能运行
		    {
			    a1 = String.valueOf(avai.getString(1));//游标在哪里呀
			    a2 = String.valueOf(avai.getString(8));
			    System.out.println(a1);
			    System.out.println(a2);
			    new add(a1, a2, conn);
		    }
		    
        }
		catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
