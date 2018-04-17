import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.UnsupportedLookAndFeelException;
//import javax.swing.JTextField;
//import java.awt.SystemColor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextPane;
//import javax.swing.DropMode;

public class ChangeWindow {

	private JFrame frame;
	private JComboBox<String> comboBox;
	private JComboBox<String> comboBox_1;
	private JComboBox<String> comboBox_2;
	private JComboBox<String> comboBox_3;
	private JButton button;
	private JLabel label_3;
	private JLabel label_4;
	private JLabel label_5;
	private JLabel label_6;
	private JTable table;
	private JTextPane textPane;
	static String a,b,c,d;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChangeWindow window = new ChangeWindow();
					window.frame.setVisible(true);
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ChangeWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setTitle("智能排课系统-课表调整");
		frame.setBounds(100, 100, 488, 330);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel label = new JLabel("智能排课系统");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setForeground(new Color(30, 144, 255));
		label.setFont(new Font("楷体", Font.BOLD, 40));
		label.setBounds(96, 0, 252, 79);
		frame.getContentPane().add(label);
		
		comboBox_1 = new JComboBox<String>();
		comboBox_1.setFont(new Font("宋体", Font.PLAIN, 15));
		comboBox_1.setModel(new DefaultComboBoxModel<String>(new String[] {"星期一", "星期二", "星期三", "星期四", "星期五"}));
		comboBox_1.setBounds(96, 213, 105, 31);
		frame.getContentPane().add(comboBox_1);
		
		comboBox_2 = new JComboBox<String>();
		comboBox_2.setFont(new Font("宋体", Font.PLAIN, 15));
		comboBox_2.setModel(new DefaultComboBoxModel<String>(new String[] {"第一节", "第二节", "第三节", "第四节"}));
		comboBox_2.setBounds(301, 153, 105, 31);
		frame.getContentPane().add(comboBox_2);
		
		comboBox_3 = new JComboBox<String>();
		comboBox_3.setFont(new Font("宋体", Font.PLAIN, 15));
		comboBox_3.setModel(new DefaultComboBoxModel<String>(new String[] {"第一节", "第二节", "第三节", "第四节"}));
		comboBox_3.setBounds(301, 213, 105, 31);
		frame.getContentPane().add(comboBox_3);
		
		comboBox = new JComboBox<String>();
		comboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"星期一", "星期二", "星期三", "星期四", "星期五"}));
		comboBox.setFont(new Font("宋体", Font.PLAIN, 15));
		comboBox.setBounds(96, 153, 105, 31);
		frame.getContentPane().add(comboBox);
		
		JButton button_1 = new JButton("安全登出");
		button_1.setFont(new Font("宋体", Font.PLAIN, 12));
		button_1.setBounds(384, 0, 88, 29);
		frame.getContentPane().add(button_1);
		button_1.addMouseListener(new MouseAdapter(){
		    public void mouseClicked(MouseEvent e){
		       Logout.main3();//退出窗口	
		       frame.setVisible(false);
		    }
		});
		
		button = new JButton("调整");
		button.setFont(new Font("宋体", Font.PLAIN, 15));
		button.setBounds(344, 76, 105, 34);
		frame.getContentPane().add(button);
		button.addMouseListener(new MouseAdapter(){
		    public void mouseClicked(MouseEvent e){
		    	 a = (String)comboBox.getSelectedItem();
		    	 b = (String)comboBox_1.getSelectedItem();
		    	 c = (String)comboBox_2.getSelectedItem();
		    	 d = (String)comboBox_3.getSelectedItem();
		    	textPane.setText("调整成功！");
		    	button.setText("刷新课表");
		    	button.addMouseListener(new MouseAdapter(){
				    public void mouseClicked(MouseEvent e){
				    	frame.dispose();
				    	try{
							NewDisplay.main();
						}
				    	catch (ClassNotFoundException | InstantiationException | IllegalAccessException | IOException
								| UnsupportedLookAndFeelException e1) {
							// TODO 自动生成的 catch 块
							e1.printStackTrace();
						}
				    }
		    	});
		    }
		});
				
		label_3 = new JLabel("原时间");
		label_3.setFont(new Font("宋体", Font.PLAIN, 16));
		label_3.setBounds(39, 155, 64, 24);
		frame.getContentPane().add(label_3);
		
		label_4 = new JLabel("现时间");
		label_4.setFont(new Font("宋体", Font.PLAIN, 16));
		label_4.setBounds(39, 220, 54, 15);
		frame.getContentPane().add(label_4);
		
		label_5 = new JLabel("节次");
		label_5.setFont(new Font("宋体", Font.PLAIN, 16));
		label_5.setBounds(245, 153, 46, 29);
		frame.getContentPane().add(label_5);
		
		label_6 = new JLabel("节次");
		label_6.setFont(new Font("宋体", Font.PLAIN, 16));
		label_6.setBounds(245, 216, 46, 23);
		frame.getContentPane().add(label_6);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 63, 445, 79);
		frame.getContentPane().add(separator);
		
		table = new JTable();
		table.setBounds(20, 133, 429, 131);
		frame.getContentPane().add(table);
		
		textPane = new JTextPane();
		textPane.setFont(new Font("宋体", Font.PLAIN, 13));
		textPane.setText("请根据实际情况做出适当调整～");
		textPane.setBounds(20, 76, 286, 47);
		frame.getContentPane().add(textPane);
	}
}