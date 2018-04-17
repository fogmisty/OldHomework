import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
//import java.util.LinkedList;

import javax.swing.*;
//import javax.swing.table.TableModel;

import java.awt.Color;
//import java.awt.EventQueue;
public class NewDisplay extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2297879244509983788L;
	ArrangeCourse pDoc2;
	static JTable mainTable2;
	JTable rowTable2;
	JScrollPane tableFrame2;
//	private Object change;
	
	NewDisplay(ArrangeCourse doc)
	{
		setResizable(false);
		getContentPane().setFont(new Font("宋体", Font.BOLD, 9));
		pDoc2 = doc;
		createTable();
		setBounds(0,0,840,480);
		setTitle("智能排课系统-课表展示");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	private void createTable(){
		Object name[]={"星期一","星期二","星期三","星期四","星期五"};
		Object A[][] = new Object[4][5];
		mainTable2 = new JTable(A,name);
		tableFrame2 = new JScrollPane(mainTable2);
		tableFrame2.setBounds(8, 76, 824, 311);
		mainTable2.putClientProperty("Quaqua.Table.style", "striped");
		getContentPane().setLayout(null);
		mainTable2.setRowHeight(70);
		getContentPane().add(tableFrame2);
		for(int i = 0; i < 4; i++)
		{
			for(int j = 0; j < 5; j++)
			{
				mainTable2.getModel().setValueAt("",i, j);
			}
		}
		
		JLabel label = new JLabel("智能排课系统");
		label.setBounds(247, 0, 335, 69);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setForeground(new Color(30, 144, 255));
		label.setFont(new Font("楷体", Font.BOLD, 40));
		getContentPane().add(label);
		
		JButton button = new JButton("安全登出");
		button.setFont(new Font("宋体", Font.PLAIN, 12));
		button.setBounds(736, 0, 88, 29);
		getContentPane().add(button);
		button.addMouseListener(new MouseAdapter() {    // 对button按钮添加监听事件
		    public void mouseClicked(MouseEvent e) {    // 当鼠标点击时
		       Logout.main3();   // 退出窗口	
		       setVisible(false);       
		    }
		});
	}
	
	public static void main() throws IOException, UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException{
		ArrangeCourse doc2 = new ArrangeCourse();
		NewDisplay mainFrame2 = new NewDisplay(doc2);
		doc2.mainFrame2 = mainFrame2;
		doc2.mergeNewDisplay("15-软工-1");
		
		int j = 0;
		int p = 0;
		if(ChangeWindow.a.equals("星期一"))
		{
			j = 0;
			if(ChangeWindow.b.equals("星期一")){
				p = 0;
			}
			if(ChangeWindow.b.equals("星期二")){
				p = 1;
			}
			if(ChangeWindow.b.equals("星期三")){
				p = 2;
			}
			if(ChangeWindow.b.equals("星期四")){
				p = 3;
			}
			if(ChangeWindow.b.equals("星期五")){
				p=4;
			}
		}
		else if(ChangeWindow.a.equals("星期二"))
		{
			j = 1;
			if(ChangeWindow.b.equals("星期一")){
				p = 0;
			}
			if(ChangeWindow.b.equals("星期二")){
				p = 1;
			}
			if(ChangeWindow.b.equals("星期三")){
				p = 2;
			}
			if(ChangeWindow.b.equals("星期四")){
				p = 3;
			}
			if(ChangeWindow.b.equals("星期五")){
				p = 4;
			}
		}
		else if(ChangeWindow.a.equals("星期三"))
		{
			j = 2;
			if(ChangeWindow.b.equals("星期一")){
				p = 0;
			}
			if(ChangeWindow.b.equals("星期二")){
				p = 1;
			}
			if(ChangeWindow.b.equals("星期三")){
				p = 2;
			}
			if(ChangeWindow.b.equals("星期四")){
				p = 3;
			}
			if(ChangeWindow.b.equals("星期五")){
				p = 4;
			}
		}
		else if(ChangeWindow.a.equals("星期四"))
		{
			j = 3;
			if(ChangeWindow.b.equals("星期一")){
				p = 0;
			}
			if(ChangeWindow.b.equals("星期二")){
				p = 1;
			}
			if(ChangeWindow.b.equals("星期三")){
				p = 2;
			}
			if(ChangeWindow.b.equals("星期四")){
				p = 3;
			}
			if(ChangeWindow.b.equals("星期五")){
				p = 4;
			}
		}
		else if(ChangeWindow.a.equals("星期五"))
		{
			j = 4;
			if(ChangeWindow.b.equals("星期一")){
				p = 0;
			}
			if(ChangeWindow.b.equals("星期二")){
				p = 1;
			}
			if(ChangeWindow.b.equals("星期三")){
				p = 2;
			}
			if(ChangeWindow.b.equals("星期四")){
				p = 3;
			}
			if(ChangeWindow.b.equals("星期五")){
				p = 4;
			}
		}
		int i = 0;
	    int q = 0;
	    if(ChangeWindow.c.equals("第一节")){
	    	i = 0;
	    	if(ChangeWindow.d.equals("第一节")){
	    		q = 0;
	    	}
	    	if(ChangeWindow.d.equals("第二节")){
	    		q = 1;
	    	}
	    	if(ChangeWindow.d.equals("第三节")){
	    		q = 2;
	    	}
	    	if(ChangeWindow.d.equals("第四节")){
	    		q = 3;
	    	}
	    }
	    else if(ChangeWindow.c.equals("第二节")){
	    	i = 1;
	    	if(ChangeWindow.d.equals("第一节")){
	    		q = 0;
	    	}
	    	if(ChangeWindow.d.equals("第二节")){
	    		q = 1;
	    	}
	    	if(ChangeWindow.d.equals("第三节")){
	    		q = 2;
	    	}
	    	if(ChangeWindow.d.equals("第四节")){
	    		q = 3;
	    	}
	    }
	    else if(ChangeWindow.c.equals("第三节")){
	    	i = 2;
	    	if(ChangeWindow.d.equals("第一节")){
	    		q = 0;
	    	}
	    	if(ChangeWindow.d.equals("第二节")){
	    		q = 1;
	    	}
	    	if(ChangeWindow.d.equals("第三节")){
	    		q = 2;
	    	}
	    	if(ChangeWindow.d.equals("第四节")){
	    		q = 3;
	    	}
	    }
	    else if(ChangeWindow.c.equals("第四节")){
	    	i = 3;
	    	if(ChangeWindow.d.equals("第一节")){
	    		q = 0;
	    	}
	    	if(ChangeWindow.d.equals("第二节")){
	    		q = 1;
	    	}
	    	if(ChangeWindow.d.equals("第三节")){
	    		q = 2;
	    	}
	    	if(ChangeWindow.d.equals("第四节")){
	    		q = 3;
	    	}
	    }
		String change = mainTable2.getModel().getValueAt(i, j).toString(); 
		String change2 = mainTable2.getModel().getValueAt(q, p).toString(); 
		String str = "此处无安排,无法调课!";
		
		if(change.isEmpty()){
			mainTable2.getModel().setValueAt(str,i,j);
		}	   
		else{
			mainTable2.getModel().setValueAt(change2,i,j);
		    mainTable2.getModel().setValueAt(change,q,p);
		}
		    //doc.mergeNewDisplay("15-软工-2");
		    //doc.mergeNewDisplay("15-软工-3");
		    mainFrame2.setVisible(true);
		}
}














