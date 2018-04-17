import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.*;
import java.awt.Color;
public class DisplayS extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2297879244509983788L;
	ArrangeCourse pDoc;
	JTable mainTable,rowTable;
	JScrollPane tableFrame;
	DisplayS(ArrangeCourse doc)
	{
		setResizable(false);
		getContentPane().setFont(new Font("宋体", Font.BOLD, 9));
		pDoc = doc;
		createTable();
		setBounds(0,0,840,480);
		setTitle("智能排课系统-课表展示");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	private void createTable(){
		Object name[] = {"星期一","星期二","星期三","星期四","星期五"};
		Object a[][] = new Object[4][5];
		mainTable = new JTable(a,name);
		tableFrame = new JScrollPane(mainTable);
		tableFrame.setBounds(8, 76, 824, 311);
		mainTable.putClientProperty("Quaqua.Table.style", "striped");
		getContentPane().setLayout(null);
		mainTable.setRowHeight(70);
		getContentPane().add(tableFrame);
		for(int i = 0; i < 4; i++)
		{
			for(int j = 0; j < 5; j++)
			{
				mainTable.getModel().setValueAt("",i, j);
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
	public static void main(String args[]) throws IOException, UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException{
		ArrangeCourse doc = new ArrangeCourse();
		DisplayS mainFrame = new DisplayS(doc);
		doc.mainFrame = mainFrame;
		
		
		mainFrame.setTitle("智能排课系统-"+"15-软工-1"+"班-课表展示");
		//mainFrame.setTitle("智能排课系统-"+"15-软工-2"+"班-课表展示");
		//mainFrame.setTitle("智能排课系统-"+"15-软工-3"+"班-课表展示");
		
		
		doc.weekInit("15-软工-1");
		for(int i = 2; i < 22; i++) 
			doc.weekNext("15-软工-1", i);
	        doc.mergeDisplay("15-软工-1");
	    doc.weekInit("15-软工-2");
		for(int i=2;i<22;i++) 
			doc.weekNext("15-软工-2", i);
			//doc.mergeDisplay("15-软工-2");		
		doc.weekInit("15-软工-3");
		for(int i=2;i<22;i++) 
			doc.weekNext("15-软工-3", i);
			//doc.mergeDisplay("15-软工-3");
			mainFrame.setVisible(true);
		//doc.mergeDisplay("15-软工-1");
		//doc.mergeDisplay("15-软工-2");
		//doc.mergeDisplay("15-软工-3");
		mainFrame.setVisible(true);
	}

}