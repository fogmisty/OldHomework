import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;  
  
public class MapFrame extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static void main(String[] args){  
    	new MapFrame();  
    } 
	
    public MapFrame(){
    	
    	super("北洋园导航");// 标题
    	
    	//setLayout(null);// 空布局
        
        //图片
        Image image = new ImageIcon("backgroundby.png").getImage();// 这是背景图片 .png .jpg .gif 等格式的图片都可以  
        JLabel imgLabel = new aLabel(image);// 将背景图放在"标签"里。  
        this.getLayeredPane().add(imgLabel, new Integer(Integer.MIN_VALUE));// 注意这里是关键，将背景标签添加到jfram的LayeredPane面板里。  
        Container cp = this.getContentPane();  
        ((JPanel) cp).setOpaque(false); // 注意这里，将内容面板设为透明。这样LayeredPane面板中的背景才能显示出来。  
  
        this.setLocation(200,100);  
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
        this.setSize(1200, 622);  
        this.setResizable(false);  
        this.setVisible(true);
        
        imgLabel.setBounds(0, 0, 800, 600);// 设置背景标签的位置
        
        
        //-----------------------各种文字-------------------------------
        
		//按钮
		JButton button = new JButton("导航开始");
		button.setBounds(950, 250, 100, 25);
		button.setFont(new Font("宋体", Font.PLAIN, 14));
		/*
		button.addMouseListener(new MouseAdapter(){
		    public void mouseClicked(MouseEvent e){
		    	new BeiYangMap();
		    }
		});*/
		this.getContentPane().add(button);
		
        //上面文字
        this.setResizable(false);
        this.setTitle("简单的导航系统");
        this.getContentPane().setBackground(UIManager.getColor("FormattedTextField.inactiveBackground"));
        this.setBackground(SystemColor.control);
        this.getContentPane().setForeground(new Color(0,0,0));
        this.getContentPane().setFont(new Font("Batang", Font.PLAIN, 15));
        //this.setBounds(1000, 100, 438, 300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setLayout(null);
		
        //选项的题目文字
 		JLabel from = new JLabel("起始地：");
		from.setBounds(850, 80, 80, 25);
		from.setFont(new Font("宋体", Font.PLAIN, 16));
		from.setHorizontalAlignment(SwingConstants.CENTER);
		this.getContentPane().add(from);
		
		JLabel to = new JLabel("目的地：");
		to.setBounds(850, 120, 80, 25);
		to.setFont(new Font("宋体", Font.PLAIN, 16));
		to.setHorizontalAlignment(SwingConstants.CENTER);
		this.getContentPane().add(to);
		
		JLabel by = new JLabel("出行方式：");
		by.setBounds(850, 160, 100, 25);
		by.setFont(new Font("宋体", Font.PLAIN, 16));
		by.setHorizontalAlignment(SwingConstants.CENTER);
		this.getContentPane().add(by);
		
		//标题文字		
        JLabel title = new JLabel("简单的导航系统");
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setForeground(new Color(0,100,0));
        title.setFont(new Font("楷体", Font.BOLD, 25));
        title.setBounds(900, 0, 200, 70);
 		this.getContentPane().add(title);
 		
		//提示
 		JLabel tip = new JLabel("地点涉及有限，详情请查看地图，抱歉～");
		tip.setBounds(800, 180, 400, 40);
		tip.setFont(new Font("宋体", Font.PLAIN, 12));
		tip.setHorizontalAlignment(SwingConstants.CENTER);
		this.getContentPane().add(tip);
		
		//下拉框框
		JComboBox<String> comboBox = new JComboBox<String>();
		comboBox.setBounds(1030, 80, 137, 21);
		comboBox.setFont(new Font("宋体", Font.PLAIN, 16));
		comboBox.setToolTipText("");
		this.getContentPane().add(comboBox);
		comboBox.addItem("格园");
		comboBox.addItem("知园");
		comboBox.addItem("平园");
		comboBox.addItem("诚园");
		comboBox.addItem("正园");//中文???
		comboBox.addItem("修园");
		comboBox.addItem("齐园");
		comboBox.addItem("治园");
		comboBox.addItem("青园餐厅");	
		comboBox.addItem("梅园餐厅");
		comboBox.addItem("兰园餐厅");
		comboBox.addItem("棠园餐厅");
		comboBox.addItem("竹园餐厅");
		comboBox.addItem("桃园餐厅");
		comboBox.addItem("菊园餐厅");
		comboBox.addItem("留园餐厅");
		comboBox.addItem("郑东图书馆");
		comboBox.addItem("行政服务中心");
		comboBox.addItem("学生中心");
		comboBox.addItem("体育场");
		comboBox.addItem("青年教师公寓");
		comboBox.addItem("太雷广场");
		comboBox.addItem("北洋广场");
		comboBox.addItem("宣怀广场");
		comboBox.addItem("东门");
		comboBox.addItem("南门");
		comboBox.addItem("北门");
		comboBox.addItem("第三十四教学楼");
		comboBox.addItem("第三十五教学楼");
		comboBox.addItem("第三十六教学楼");
		comboBox.addItem("第三十七教学楼");
		comboBox.addItem("第三十八教学楼");
		comboBox.addItem("第三十九教学楼");
		comboBox.addItem("第四十一教学楼");
		comboBox.addItem("第四十二教学楼");
		comboBox.addItem("第四十三教学楼");
		comboBox.addItem("第四十四教学楼");
		comboBox.addItem("第四十五教学楼");
		comboBox.addItem("第四十六教学楼");
		comboBox.addItem("第四十七教学楼");
		comboBox.addItem("第四十八教学楼");
		comboBox.addItem("第四十九教学楼");
		comboBox.addItem("第五十教学楼");
		comboBox.addItem("第五十一教学楼");
		comboBox.addItem("第五十二教学楼");
		comboBox.addItem("第五十三教学楼");
		comboBox.addItem("第五十四教学楼");
		comboBox.addItem("第五十五教学楼");
		
		JComboBox<String> comboBox2 = new JComboBox<String>();
		comboBox2.setBounds(1030, 120, 137, 21);
		comboBox2.setFont(new Font("宋体", Font.PLAIN, 16));
		comboBox2.setToolTipText("");
		this.getContentPane().add(comboBox2);
		comboBox2.addItem("格园");
		comboBox2.addItem("知园");
		comboBox2.addItem("平园");
		comboBox2.addItem("诚园");
		comboBox2.addItem("正园");//中文???
		comboBox2.addItem("修园");
		comboBox2.addItem("齐园");
		comboBox2.addItem("治园");
		comboBox2.addItem("青园餐厅");	
		comboBox2.addItem("梅园餐厅");
		comboBox2.addItem("兰园餐厅");
		comboBox2.addItem("棠园餐厅");
		comboBox2.addItem("竹园餐厅");
		comboBox2.addItem("桃园餐厅");
		comboBox2.addItem("菊园餐厅");
		comboBox2.addItem("留园餐厅");
		comboBox2.addItem("郑东图书馆");
		comboBox2.addItem("行政服务中心");
		comboBox2.addItem("学生中心");
		comboBox2.addItem("体育场");
		comboBox2.addItem("青年教师公寓");
		comboBox2.addItem("太雷广场");
		comboBox2.addItem("北洋广场");
		comboBox2.addItem("宣怀广场");
		comboBox2.addItem("东门");
		comboBox2.addItem("南门");
		comboBox2.addItem("北门");
		comboBox2.addItem("第三十四教学楼");
		comboBox2.addItem("第三十五教学楼");
		comboBox2.addItem("第三十六教学楼");
		comboBox2.addItem("第三十七教学楼");
		comboBox2.addItem("第三十八教学楼");
		comboBox2.addItem("第三十九教学楼");
		comboBox2.addItem("第四十一教学楼");
		comboBox2.addItem("第四十二教学楼");
		comboBox2.addItem("第四十三教学楼");
		comboBox2.addItem("第四十四教学楼");
		comboBox2.addItem("第四十五教学楼");
		comboBox2.addItem("第四十六教学楼");
		comboBox2.addItem("第四十七教学楼");
		comboBox2.addItem("第四十八教学楼");
		comboBox2.addItem("第四十九教学楼");
		comboBox2.addItem("第五十教学楼");
		comboBox2.addItem("第五十一教学楼");
		comboBox2.addItem("第五十二教学楼");
		comboBox2.addItem("第五十三教学楼");
		comboBox2.addItem("第五十四教学楼");
		comboBox2.addItem("第五十五教学楼");
		
		JComboBox<String> comboBox3 = new JComboBox<String>();
		comboBox3.setBounds(1030, 160, 137, 21);
		comboBox3.setFont(new Font("宋体", Font.PLAIN, 16));
		comboBox3.setToolTipText("");
		this.getContentPane().add(comboBox3);
		comboBox3.addItem("机动车");
		comboBox3.addItem("自行车");
		
		//关键性的按钮监听事件！ love!
		button.addMouseListener(new MouseAdapter(){
		    public void mouseClicked(MouseEvent e){
		    	//各种各样的操作
		    	String fp = (String) comboBox.getSelectedItem();
		    	//System.out.print(fp);//test
		    	String tp = (String) comboBox2.getSelectedItem();
		    	//System.out.print(tp);//test
		    	String bw = (String) comboBox3.getSelectedItem();
		    	//System.out.print(bw);//test
		    	//new BeiYangMap();
		    	if(bw.equals("自行车")) {
		    		BeiYangMap map = new BeiYangMap();
		    		map.addRoute("知园", "格园", 132);
		    		map.addRoute("诚园", "第五十教学楼", 196);
		    		map.addRoute("诚园", "第五十五教学楼", 197);
		    		map.addRoute("知园", "诚园", 40);
		    		map.addRoute("诚园", "梅园餐厅", 81);
		    		map.addRoute("知园", "第五十三教学楼", 76);
		    		map.addRoute("知园", "第五十四教学楼", 101);
		    		map.addRoute("第五十一教学楼", "第五十三教学楼", 138);
		    		map.addRoute("第五十二教学楼", "第五十四教学楼", 158);
		    		map.addRoute("第五十二教学楼", "青园餐厅", 140);
		    		map.addRoute("第五十二教学楼", "青年教师公寓", 140);
		    		map.addRoute("第五十一教学楼", "体育场", 108);
		    		map.addRoute("格园", "留园餐厅", 26);
		    		map.addRoute("平园", "第五十五教学楼", 160);
		    		map.addRoute("梅园餐厅", "菊园餐厅", 28);
		    		map.addRoute("平园", "棠园餐厅", 114);
		    		map.addRoute("第五十教学楼", "行政服务中心", 119);
		    		map.addRoute("棠园餐厅", "郑东图书馆", 118);
		    		map.addRoute("郑东图书馆", "第四十六教学楼", 100);
		    		map.addRoute("郑东图书馆", "第四十五教学楼", 100);
		    		map.addRoute("第四十六教学楼", "兰园餐厅", 177);
		    		map.addRoute("诚园", "兰园餐厅", 163);
		    		map.addRoute("第四十九教学楼", "兰园餐厅", 81);
		    		map.addRoute("第四十六教学楼", "第四十五教学楼", 31);
		    		map.addRoute("第四十八教学楼", "第四十九教学楼", 81);
		    		
		    		map.show(fp, tp);
		    		//imagetest.line(fp, tp);//
		    	}
		    	else if(bw.equals("机动车")) {
		    		BeiYangMap map = new BeiYangMap();
		    		map.addRoute("知园", "格园", 132);
		    		map.addRoute("诚园", "第五十教学楼", 196);
		    		map.addRoute("诚园", "第五十五教学楼", 197);
		    		map.addRoute("知园", "诚园", 40);
		    		map.addRoute("诚园", "梅园餐厅", 81);
		    		map.addRoute("知园", "第五十三教学楼", 76);
		    		map.addRoute("知园", "第五十四教学楼", 101);
		    		map.addRoute("第五十一教学楼", "第五十三教学楼", 138);
		    		map.addRoute("第五十二教学楼", "第五十四教学楼", 158);
		    		map.addRoute("第五十二教学楼", "青园餐厅", 140);
		    		map.addRoute("第五十二教学楼", "青年教师公寓", 140);
		    		map.addRoute("第五十一教学楼", "体育场", 108);
		    		map.addRoute("格园", "留园餐厅", 26);
		    		map.addRoute("平园", "第五十五教学楼", 160);
		    		map.addRoute("梅园餐厅", "菊园餐厅", 28);
		    		map.addRoute("平园", "棠园餐厅", 114);
		    		map.addRoute("第五十教学楼", "行政服务中心", 119);
		    		map.addRoute("棠园餐厅", "郑东图书馆", 118);
		    		//map.addRoute("郑东图书馆", "第四十六教学楼", 100);
		    		//map.addRoute("郑东图书馆", "第四十五教学楼", 100);
		    		//map.addRoute("第四十六教学楼", "兰园餐厅", 177);
		    		//map.addRoute("诚园", "兰园餐厅", 163);
		    		map.show(fp, tp);
		    		//imagetest.line(fp, tp);//
		    	}
		    	
		    }
		});
		
    }
    
    //内部类  
    private class aLabel extends JLabel {  
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		private Image image;  
        public aLabel(Image image){  
            this.image = image;  
        }  
        @Override  
        public void paintComponent(Graphics g){  
            super.paintComponent(g);  
            int x = this.getWidth();  
            int y = this.getHeight();  
  
            g.drawImage(image, 0, 0, x, y, null);  
        }  
    }  
}