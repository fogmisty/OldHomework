import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
	
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
	
	
/**
* MyZip
*/
public class bestFrame {
	
	private JFrame jf = null;
	private JLabel leftInfo = new JLabel("状态栏:");
	private JLabel pathInfo = new JLabel(" ");
	private JLabel timeInfo = new JLabel(" ");
	
	private JMenuItem openFileItem, exitItem, findFileItem, viewLogItem, delLogItem, aboutItem, returnway;
	private JMenuItem switchSuanfa;
	
	
	ActionHandle handle = new ActionHandle();
	
	private JTextField srcFiles;
	private JTextField destZip;
	private JTextField srcZip;
	private JTextField destFiles;
	
	JButton btn_srcFiles,btn_destZip,compact,btn_srcZip,btn_destFiles,unCompact;
	JFileChooser fileChooser = new JFileChooser();
	
	JCheckBox checkBox = null;
	
	File src = null;
	File dest = null;
	File src2 = null;
	File dest2 = null;
    
	public bestFrame() {
	
		fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);//可选择目录
	
		jf = new JFrame("可爱的压缩工具之特别篇");
		jf.setSize(750,510 );
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setLocationRelativeTo(null);
	
		JPanel topJPanel = new JPanel();
		topJPanel.setLayout(new GridLayout(2, 1));
	
		JRootPane rootPane = new JRootPane(); // 此panel，添加菜单
	    rootPane.setBackground(Color.gray);
	    JMenuBar menuBar = new JMenuBar();
	
	    JMenu fileMenu = new JMenu("文件(F)");
	    JMenu commandMenu = new JMenu("命令(C)");
	    JMenu toolMenu = new JMenu("工具(S)");
	    JMenu optionMenu = new JMenu("选项(N)");
	    JMenu helpMenu = new JMenu("帮助(H)");
	
	    rootPane.setJMenuBar(menuBar);
	    menuBar.add(fileMenu);
	    menuBar.add(commandMenu);
	    menuBar.add(toolMenu);
	    menuBar.add(optionMenu);
	    menuBar.add(helpMenu);
	
	    findFileItem = new JMenuItem("查找文件");
	    switchSuanfa = new JMenuItem("转换压缩格式");
	    findFileItem.addActionListener(handle);
	    switchSuanfa.addActionListener(handle);
	    toolMenu.add(findFileItem);
	    toolMenu.add(switchSuanfa);
	    
	    //add
	    returnway = new JMenuItem("返回正常模式");
	    commandMenu.add(returnway);
	    returnway.addActionListener(handle);
	
	    viewLogItem = new JMenuItem("查看日志");
	    delLogItem = new JMenuItem("清除日志");
	    viewLogItem.addActionListener(handle);
	    delLogItem.addActionListener(handle);
	    optionMenu.add(viewLogItem);
	    optionMenu.add(delLogItem);
	
	    openFileItem = new JMenuItem("打开压缩文件");
	    exitItem = new JMenuItem("退出");
	    openFileItem.addActionListener(handle);
	    exitItem.addActionListener(handle);
	
	    aboutItem = new JMenuItem("关于");
	    helpMenu.add(aboutItem);
	    aboutItem.addActionListener(handle);
	
	    // 给菜单 添加菜单项
	    fileMenu.add(openFileItem);
	    fileMenu.add(exitItem);
		
	    topJPanel.add(rootPane);// 工具panel :文件、编辑、查看
	    jf.getContentPane().add(topJPanel, BorderLayout.NORTH);
	
	    // center
	    JPanel centerP = new JPanel();
	    jf.getContentPane().add(centerP, BorderLayout.CENTER);
	
	    JLabel label = new JLabel("待压缩文件源:");//待压缩的文件源
		
	    srcFiles = new JTextField();
	    srcFiles.setColumns(10);
	
	    //浏览srcFiles--压缩
	    btn_srcFiles = new JButton("浏览");//浏览
	
	    JLabel label_1 = new JLabel("压缩到:");//压缩到:
	
	    destZip = new JTextField();
	    destZip.setColumns(10);
	
	    //view destZip
	    btn_destZip = new JButton("浏览");//浏览
	
	    //压缩
	    compact = new JButton("压缩");
	
	
	    JLabel label_2 = new JLabel("待解压文件源:");
	
	    srcZip = new JTextField();
	    srcZip.setColumns(10);
	
	    //浏览--srcZip
	    btn_srcZip = new JButton("浏览");
	
	    JLabel label_3 = new JLabel("解压到:");
	
	    destFiles = new JTextField();
	    destFiles.setColumns(10);
		
	    //浏览--destFiles
	    btn_destFiles = new JButton("浏览");
	
	    //解压
	    unCompact = new JButton("解压");
	
	    btn_srcFiles.addActionListener(handle);
	    btn_destZip.addActionListener(handle);
	    btn_srcZip.addActionListener(handle);
	    btn_destFiles.addActionListener(handle);
	
	    compact.addActionListener(handle);
	    unCompact.addActionListener(handle);
	
	    //解压后删除源
	    checkBox = new JCheckBox("解压后删除源");
	
	
	    GroupLayout gl_centerP = new GroupLayout(centerP);
	    gl_centerP.setHorizontalGroup(
	    gl_centerP.createParallelGroup(Alignment.LEADING)
	    .addGroup(gl_centerP.createSequentialGroup()
	    .addContainerGap()
	    .addGroup(gl_centerP.createParallelGroup(Alignment.TRAILING)
	    .addComponent(label_3)
	    .addComponent(label_2)
	    .addComponent(label_1)
	    .addComponent(label))
	    .addPreferredGap(ComponentPlacement.RELATED)
	    .addGroup(gl_centerP.createParallelGroup(Alignment.LEADING, false)
	    .addComponent(destFiles)
	    .addComponent(srcZip)
	    .addComponent(destZip)
	    .addComponent(srcFiles, GroupLayout.DEFAULT_SIZE, 367, Short.MAX_VALUE))
	    .addPreferredGap(ComponentPlacement.RELATED)
	    .addGroup(gl_centerP.createParallelGroup(Alignment.LEADING)
	    .addGroup(gl_centerP.createSequentialGroup()
	    .addComponent(btn_srcZip)
	    .addContainerGap())
	    .addGroup(gl_centerP.createParallelGroup(Alignment.TRAILING)
	    .addGroup(gl_centerP.createSequentialGroup()
	    .addComponent(btn_srcFiles)
	    .addContainerGap(206, Short.MAX_VALUE))
	    .addGroup(gl_centerP.createSequentialGroup()
	    .addGroup(gl_centerP.createParallelGroup(Alignment.LEADING)
	    .addComponent(btn_destZip)
	    .addComponent(btn_destFiles))
	    .addPreferredGap(ComponentPlacement.RELATED)
	    .addComponent(checkBox)
	    .addPreferredGap(ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
	    .addGroup(gl_centerP.createParallelGroup(Alignment.LEADING)
	    .addComponent(unCompact)
	    .addComponent(compact))
	    .addGap(15))))));
	    
	    gl_centerP.setVerticalGroup(
	    gl_centerP.createParallelGroup(Alignment.LEADING)
	    .addGroup(gl_centerP.createSequentialGroup()
	    .addGap(47)
	    .addGroup(gl_centerP.createParallelGroup(Alignment.BASELINE)
	    .addComponent(label)
	    .addComponent(srcFiles, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
	    .addComponent(btn_srcFiles))
	    .addPreferredGap(ComponentPlacement.UNRELATED)
	    .addGroup(gl_centerP.createParallelGroup(Alignment.BASELINE)
	    .addComponent(label_1)
	    .addComponent(destZip, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
	    .addComponent(btn_destZip)
	    .addComponent(compact))
	    .addGap(59)
	    .addGroup(gl_centerP.createParallelGroup(Alignment.LEADING)
	    .addComponent(label_2)
	    .addGroup(gl_centerP.createParallelGroup(Alignment.BASELINE)
	    .addComponent(srcZip, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
	    .addComponent(btn_srcZip)))
	    .addGap(18)
	    .addGroup(gl_centerP.createParallelGroup(Alignment.BASELINE)
	    .addComponent(label_3)
	    .addComponent(destFiles, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
	    .addComponent(btn_destFiles)
	    .addComponent(unCompact)
	    .addComponent(checkBox))
	    .addContainerGap(184, Short.MAX_VALUE)));
	    
	    centerP.setLayout(gl_centerP);
	
	
	    // south--状态栏
	    JToolBar bottomToolBar = new JToolBar();
	    bottomToolBar.setFloatable(false);// 设置JToolBar不可拖动
	
	    bottomToolBar.setPreferredSize(new Dimension(jf.getWidth(), 20));
	    bottomToolBar.add(leftInfo);
	
	    // bottomToolBar.addSeparator(); //此方法添加分隔符 无效
	    JSeparator jsSeparator = new JSeparator(SwingConstants.VERTICAL);
	    bottomToolBar.add(jsSeparator);// 添加分隔符
	
	    leftInfo.setPreferredSize(new Dimension(200, 20));
	    leftInfo.setHorizontalTextPosition(SwingConstants.LEFT);
	
	    bottomToolBar.add(pathInfo);
	    pathInfo.setHorizontalTextPosition(SwingConstants.LEFT);
	    bottomToolBar.add(new JSeparator(SwingConstants.VERTICAL));// 添加分隔符
	
	    bottomToolBar.add(timeInfo);
	    timeInfo.setPreferredSize(new Dimension(70, 20));
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    timeInfo.setText(sdf.format(new Date()));
	
	    jf.getContentPane().add(bottomToolBar, BorderLayout.SOUTH);// 下面--放“状态栏”
	
	    jf.setVisible(true);
	}
	
	/**
	* 主界面菜单--事件监听
	*/
	class ActionHandle implements ActionListener {
	
		File f = null;
	
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == returnway) {// viewItem
				new UIFrame();
				jf.dispose();  
			}
			
			if (e.getSource() == openFileItem) { //
				JOptionPane.showMessageDialog(jf, "此功能尚未开放");
			}
	
			if (e.getSource() == exitItem) {// exit application
				System.exit(0);
				}
	
			if (e.getSource() == aboutItem) {// show about
				JOptionPane.showMessageDialog(jf, "@Author:GJN\n 2017.9");
			}
	
			if (e.getSource() == findFileItem) {// viewItem
				jf.repaint();
			}
	
			if (e.getSource() == switchSuanfa) {// 切换压缩算法
				//
				new UIFrame2();
				//System.exit(0);  
				jf.dispose();  
			}
	
			if (e.getSource() == viewLogItem) {// 查看日志
				JOptionPane.showMessageDialog(jf, "此功能尚未开放");
			}
	
			if (e.getSource() == delLogItem) {// 删除日志
				JOptionPane.showMessageDialog(jf, "此功能尚未开放");
			}
			
			long before = 0, afterz = 0, aftert = 0;
			
			//路径
			if (e.getSource() == btn_srcFiles){//选择将要压缩的文件路径
				fileChooser.showOpenDialog(jf);
				src = fileChooser.getSelectedFile();
				System.out.println(src);
				srcFiles.setText(src.getAbsolutePath());
			}
	
			if (e.getSource() == btn_destZip){//选择压缩的文件--目的路径
				fileChooser.showOpenDialog(jf);
				f = fileChooser.getSelectedFile();
				destZip.setText(f.getAbsolutePath());
			}
			//好乱好乱
			if (e.getSource() == compact){//压缩文件			
				dest = new File(f.getAbsolutePath(), src.getName()+".zip");
				System.out.println(dest.getName());
				new CompactAlgorithm(dest).zipFiles(src);			
				//---------------------------------------------------------------------
				before = UIFrame.getFileSize(src);//before
				afterz = UIFrame.getFileSize(dest);//after
				//System.out.println(before);
				//System.out.println(afterz);
				JOptionPane.showMessageDialog(jf, ".zip压缩率为" + afterz*100/before + "%");
					
				//=====================================================================
				
				dest = new File(f.getAbsolutePath(), src.getName()+".tar.gz");
				System.out.println(dest.getName());
				String archive;
				try {
					archive = GZIPUtil.archive(src.getAbsolutePath());//生成tar包
					@SuppressWarnings("unused")
					String path = GZIPUtil.compressArchive(archive);//生成gz包
					//JOptionPane.showMessageDialog(jf, "压缩完毕");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					JOptionPane.showMessageDialog(jf, "出现错误");
				}
				//---------------------------------------------------------------------
				before = UIFrame.getFileSize(src);//before
				aftert = UIFrame.getFileSize(dest);//after
				//System.out.println(before);
				//System.out.println(aftert);
				JOptionPane.showMessageDialog(jf, ".tar.gz压缩率为" + aftert*100/before + "%");
				
				//~~~~~~~~~~~~~~~~~~~~~~~~~~`
				if(afterz > aftert) {
					File file = new File(f.getAbsolutePath(), src.getName()+".zip");
					file.delete();
					JOptionPane.showMessageDialog(jf, "采用.tar.gz");
				}
				else {
					File file = new File(f.getAbsolutePath(), src.getName()+".tar.gz");
					file.delete();
					JOptionPane.showMessageDialog(jf, "采用.zip");
				}
				JOptionPane.showMessageDialog(jf, "压缩完毕");
			}
	
			if (e.getSource() == btn_srcZip){//选择将要解缩的文件
				fileChooser.showOpenDialog(jf);
				src2 = fileChooser.getSelectedFile();
				srcZip.setText(src2.getAbsolutePath());
			}
			
			if (e.getSource() == btn_destFiles){//选择解缩文件--目的路径
				fileChooser.showOpenDialog(jf);
				dest2 = fileChooser.getSelectedFile();
				destFiles.setText(dest2.getAbsolutePath());
			}
			
			if (e.getSource() == unCompact){//解压文件
				File src22 = new File(src2.getAbsolutePath());
				//src2 = null;
				System.gc();
				String str = src22.getName();
				System.out.println(str.substring(str.length()-4));//.zip
				System.out.println(str.substring(str.length()-7));//.tar.gz
				if(str.substring(str.length()-4).equals(".zip"))//啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊
				{
					try {
						UnZipFile.unZipFiles(src22, dest2.getAbsolutePath().replaceAll("\\*", "/")+"/");
						if(checkBox.isSelected()){
							boolean b = src22.delete();//解压后删除源					
							System.out.println("解压后删除源"+b);
						}
						JOptionPane.showMessageDialog(jf, "解压完毕");
					}
					catch (IOException e1) {
						e1.printStackTrace();
						JOptionPane.showMessageDialog(jf, "文件打不开，请确认是否为.zip文件\n" + "Error: "+e1.getMessage());
					}
				}
				/*/Users/apple/compresstest//Users/apple/compresstest/test的副本.txt/test的副本.txt
******************解压完毕********************
解压后删除源true*/
	
				else if(str.substring(str.length()-7).equals(".tar.gz"))
				{
					try {
						//GZIPUtil.deGzipArchive(dest2.getAbsolutePath(), src2.getAbsolutePath());/////////////////////
						//GZIPUtil.unCompressArchiveGz(dest2.getAbsolutePath().replaceAll("\\*", "/")+"/");
						GZIPUtil.unTarGz(src2.getAbsolutePath(), dest2.getAbsolutePath());
						JOptionPane.showMessageDialog(jf, "解压完毕");
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						JOptionPane.showMessageDialog(jf, "出现错误");
					}
					//UnZipFile.unZipFiles(src22, dest2.getAbsolutePath().replaceAll("\\*", "/")+"/");
					if(checkBox.isSelected()){
						boolean b = src22.delete();//解压后删除源					
						System.out.println("解压后删除源"+b);
					}
				}
				else {
					JOptionPane.showMessageDialog(jf, "这文件是真打不开！");
				}
				
			}
		}
	}

	/**
     * 获取文件长度
     * @param file
	 * @return 
     */
	public static long getFileSize(File file) {//
		// TODO Auto-generated method stub
		if (file.exists() && file.isFile()) {
            String fileName = file.getName();
            System.out.println("文件"+fileName+"的大小是："+file.length());
        }
		return file.length();//
	}
}