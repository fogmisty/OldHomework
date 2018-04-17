package smallchange;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class add {
	
	public add(String a1, String a2, Connection conn) {
		// TODO Auto-generated constructor stub
			try {					
					Array cut = new Array();
					
					
					String str = cut.array1(a2);		
					System.out.println(str);
					//插入
					String sql1 = "insert into pg_new values('" + a1 + "','" 
						    + "','" + str + "')"; 
					PreparedStatement ps1 = conn.prepareStatement(sql1);
					int result1 = ps1.executeUpdate();//我也不知道他是干啥用的，应该是验证吧
					System.out.println(result1);//1==true			
					//((Statement) st).executeUpdate(sql); //插完为全null？
					//JOptionPane.showMessageDialog(null, "添加成功");
					
					
					str = cut.array2(a2);
					//插入
					String sql2 = "insert into pg_new values('" + a1 + "','" 
						    + "','" + str + "')"; 
					PreparedStatement ps2 = conn.prepareStatement(sql2);
					int result2 = ps2.executeUpdate();//我也不知道他是干啥用的，应该是验证吧
					System.out.println(result2);//1==true			
					//((Statement) st).executeUpdate(sql); //插完为全null？
					//JOptionPane.showMessageDialog(null, "添加成功");
					

					str = cut.array3(a2);
					//插入
					String sql3 = "insert into pg_new values('" + a1 + "','" 
						    + "','" + str + "')"; 
					PreparedStatement ps3 = conn.prepareStatement(sql3);
					int result3 = ps3.executeUpdate();//我也不知道他是干啥用的，应该是验证吧
					System.out.println(result3);//1==true			
					//((Statement) st).executeUpdate(sql); //插完为全null？
					//JOptionPane.showMessageDialog(null, "添加成功");
		        }
				catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
	}

}
