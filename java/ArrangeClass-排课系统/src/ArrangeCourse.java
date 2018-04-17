import java.util.Vector;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
//import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

//可爱的算法
class cTask{
	String cID, cName;//课程
	String major, Class;//专业，班级
	int hour, praticedhour;//课时
	String tID, tName;//老师
	Vector<cPos> tuplec; //数组MAX_SIZE=2
	public cTask(){
		tuplec = new Vector<cPos>();
	}
	int start, end;
	public String rname;
}
class cTable{//course
	//3D: (Week,Day,Course)
	int weekNum = 25, dayNum = 5, courseNum = 4;//25周，5工作日，4节课
	Vector<Vector<Vector<String>>> table;
	public cTable(){
		table = new Vector<Vector<Vector<String>>>();
		table.setSize(weekNum+1);
		for(int i = 0; i <= weekNum; i++){
			table.set(i, new Vector<Vector<String>>());//week 变量赋值
			table.get(i).setSize(dayNum+1);
			for(int j = 0; j <= dayNum; j++){
				table.get(i).set(j, new Vector<String>());//day
				table.get(i).get(j).setSize(courseNum+1);//course
			}
		}
	}
}
class tSchedule extends cTable{//teacher
	public tSchedule(){
		table = new Vector<Vector<Vector<String>>>();
		table.setSize(weekNum+1);
		for(int i = 0; i <= weekNum; i++){
			table.set(i, new Vector<Vector<String>>());//week
			table.get(i).setSize(dayNum+1);
			for(int j = 0; j <= dayNum; j++){
				table.get(i).set(j, new Vector<String>());//day
				table.get(i).get(j).setSize(courseNum+1);
				for(int k = 0; k <= courseNum; k++){
					table.get(i).get(j).set(k,"空闲");//course new 都是空闲
				}
			}
		}
	}
}
class cPos{
	int day,pos;
	public cPos(int day,int pos){
		this.day = day; //星期
		this.pos = pos; //第几节
	}
}

public class ArrangeCourse {
	   Map<String,Vector<cTask>> task = new HashMap<String,Vector<cTask>>();
	   Map<String,cTable> cSchedule = new HashMap<String,cTable>();
	   Map<String,tSchedule> tSchedule = new HashMap<String,tSchedule>();
	   @SuppressWarnings("rawtypes")
	Map<String,LinkedList> Q1 = new HashMap<String,LinkedList>();
	   @SuppressWarnings("rawtypes")
	Map<String,LinkedList> Q2 = new HashMap<String,LinkedList>();
	   @SuppressWarnings("rawtypes")
	static Map<String,LinkedList> display = new HashMap<String,LinkedList>();
	   public Excel fileEditor;
	   DisplayS mainFrame;
	   public NewDisplay mainFrame2;
	   protected static Object doc;
	   ArrangeCourse() throws IOException
	   {
		   fileEditor = new Excel();
		   Serialize();//将Java对象序列化为二进制文件的序列化技术
		   loadTask();
	   }
	   public cTask getCourseFromMainQ(int week,int day,int pos,String cName){
		   //从主队链中取出第一个符合条件的课，条件：教师空闲
		   @SuppressWarnings("rawtypes")
		LinkedList mQ = Q1.get(cName);
		   cTask nowTask;
		   tSchedule nowTeacher;
		   for(int i = 0; i < mQ.size(); i++){
			   nowTask = (cTask)mQ.get(i);
			   nowTeacher = tSchedule.get(nowTask.tName);
			   if(nowTeacher.table.get(week).get(day).get(pos).equals("空闲")){
				   nowTeacher.table.get(week).get(day).set(pos,cName);
				   mQ.remove(i);
				   nowTask.start = week; //首次出列，添加起始周标记，修改主队链和教师计划
				   Q1.put(cName, mQ);
				   tSchedule.put(nowTask.tName, nowTeacher);
				   return nowTask;
			   }
		   }
		   nowTask = new cTask();
		   nowTask.hour = -1;
		   return nowTask;
	   }
	   public cTask getCourseFromSubQ(int week,int day,int pos,String cName,Map<String,Integer> status){ 
		   //从次队链中取出第一个符合条件的课，条件：教师空闲，当周次数少于2
		   LinkedList sQ = Q2.get(cName);
		   cTask nowTask;
		   tSchedule nowTeacher;
		   for(int i = 0; i < sQ.size(); i++){
			   nowTask = (cTask)sQ.get(i);
			   //检查该课当周情况
			   if(status.containsKey(nowTask.cName) && status.get(nowTask.cName) >= 2){ 
				   //读取到下周队列，拒绝排课
				   continue;
			   }
			   boolean offsetFlag = true;
			   if(nowTask.tuplec.size() == 2){//初始位置设置完，开始执行位置检查
				   for(int x = 0; x < 2; x++){
					   if(nowTask.tuplec.get(x).day == day && nowTask.tuplec.get(x).pos == pos){
						   offsetFlag = false;
					   }		   
				   }
				   if(offsetFlag){
					   continue;
				   }
			   }
			   nowTeacher = tSchedule.get(nowTask.tName);
			   if(nowTeacher.table.get(week).get(day).get(pos).equals("空闲")){
				   nowTeacher.table.get(week).get(day).set(pos,cName);
				   sQ.remove(i);
				   if(nowTask.tuplec.size() < 2){ //初始位置未设置完
					   nowTask.tuplec.add(new cPos(day,pos));
				   }
				   //修改次队链和教师计划
				   Q2.put(cName, sQ);
				   tSchedule.put(nowTask.tName, nowTeacher);
				   return nowTask;
			   }
		   }
		   nowTask = new cTask();
		   nowTask.hour = -1;
		   return nowTask;
	   }
	   public void changeCourseStatus(Map<String,Integer> status,String cName){
		   if(!status.containsKey(cName)){
			   status.put(cName, 1);
		   }
		   else{
			   Integer x = status.get(cName);
			   status.put(cName, x+1);
		   }
	   }
	   public void weekNext(String cName,int week){ 
		   Map<String,Integer> courseStatus = new HashMap<String,Integer>();
		   cTable table3D = cSchedule.get(cName);
		   LinkedList  mQ = new LinkedList(), sQ = new LinkedList();
		   //整体策略：先处理次对列，如果次对列发现某门课课时用完，则不再添加，并且从主队列中取出一门新课
		   
		   for(int i = 1; i <= table3D.dayNum; i++){
				   for(int j = 1;j <= table3D.courseNum; j++){
					   cTask c = getCourseFromSubQ(week,i,j,cName,courseStatus);
					   sQ = Q2.get(cName);//读取最新次链队
					   if(c.hour != -1){//排次课
						   c.hour--;
						   if(c.hour == 0){  
							   //课时为0,从主队链中取出新课，顶替当前位置，保持第一周的结构不乱
							   c.end = week;//添加结束周标记
							   if(!display.containsKey(cName)){//对排完的课，移入显示队链
								   display.put(cName,new LinkedList());
								   display.get(cName).add(c);//
							   }
							   else{
								   display.get(cName).add(c);
							   }
							   cTask x = getCourseFromMainQ(week+1,i,j,cName);//周定位在下一周
							   if(x.hour == -1){
								   continue;
							   }
							   changeCourseStatus(courseStatus,x.cName);
							   changeCourseStatus(courseStatus,x.cName);
							   x.tuplec = c.tuplec; //课程位置不变
							   sQ.add(x);
							   Q2.put(cName, sQ); //更新次链队
						   }
						   else{ 
							   //课时不为0，继续工作
							   sQ.add(c);
							   Q2.put(cName, sQ);
							   changeCourseStatus(courseStatus,c.cName);
						   }
					   }
				   }
			   }
	   }
	   public void weekInit(String cName){//第一周，初始化队列
		   cTable table3D = cSchedule.get(cName);
		   Vector<Vector<String>> table2D = table3D.table.get(1);
		   LinkedList  mQ = new LinkedList(), sQ = new LinkedList();
		   //初始化主链队
		   for(int i = 0; i < task.get(cName).size(); i++){ 
			   mQ.addLast(task.get(cName).get(i));
		   }
		   Q1.put(cName, mQ);
		   Map<String,Integer> courseStatus = new HashMap<String,Integer>();
		   //初始化第一周
		   for(int i = 1;i <= table3D.dayNum; i++){
			   switch(i){
			   case 1:  //周一，三节新课
				   int cnt = 0;
				   for(int j = 1; j <= table3D.courseNum && cnt < 3; j++){
					   cTask c = getCourseFromMainQ(1,i,j,cName);//
					   if(c.hour == -1){
						   continue;
					   }
					   //mainFrame.mainTable.getModel().setValueAt(c.courseName, j-1, i-1);
					   c.hour--;
					   cnt++;
					   if(c.tuplec.size() < 2){
						   c.tuplec.add(new cPos(i,j));
						   System.out.printf("%d %d\n",i,j);//111213
					   }  
					   sQ.add(c);
					   Q2.put(cName, sQ); //更新次链队
					   changeCourseStatus(courseStatus,c.cName);
				   }
				   break; 
			   case 2:   //周二，最多满新课
				   for(int j = 1;j <= table3D.courseNum; j++){
					   Q2.get(cName);  //读取最新次链队
					   cTask c = getCourseFromMainQ(1,i,j,cName);
					   if(c.hour == -1){
						   continue;
					   }
					   //mainFrame.mainTable.getModel().setValueAt(c.courseName, j-1, i-1);
					   c.hour--;
					   if(c.tuplec.size()<2){
						   c.tuplec.add(new cPos(i,j));
					   }  
					   sQ.add(c);
					   Q2.put(cName, sQ);
					   changeCourseStatus(courseStatus,c.cName);
				   }
				   break;
			   case 3://周三，最多两节新课，两节次课
				   //先处理次课,后处理新课
				   int nCnt = 0, oCnt = 0;
				   for(int j = 1; j <= table3D.courseNum; j++){
					   if(oCnt < 2){//拍次课
						   cTask c = getCourseFromSubQ(1,i,j,cName,courseStatus);
						   sQ = Q2.get(cName);//读取最新次链队
						   if(c.hour != -1){//排次课
							   //mainFrame.mainTable.getModel().setValueAt(c.courseName, j-1, i-1);
							   c.hour--;
							   oCnt++;
							   sQ.add(c);
							   Q2.put(cName, sQ);
							   changeCourseStatus(courseStatus,c.cName);
							   continue;
						   }
					   }
					   else{//排主课
						   if(nCnt < 2){
							   cTask c = getCourseFromMainQ(1,i,j,cName);
							   if(c.hour == -1){
								   continue;
							   }
							   //mainFrame.mainTable.getModel().setValueAt(c.courseName, j-1, i-1);
							   c.hour--;
							   if(c.tuplec.size() < 2){
								   c.tuplec.add(new cPos(i,j));
								   System.out.printf("%d %d\n",i,j);//333334
							   } 
							   sQ.add(c);
							   Q2.put(cName, sQ);
							   changeCourseStatus(courseStatus,c.cName);
						   }
					   }
				   }
				   break;
			   case 4://全是次课
				   for(int j = 1; j <= table3D.courseNum; j++){
						   cTask c = getCourseFromSubQ(1,i,j,cName,courseStatus);
						   sQ = Q2.get(cName);//读取最新次链队
						   if(c.hour != -1){//排次课
							   //mainFrame.mainTable.getModel().setValueAt(c.courseName, j-1, i-1);
							   c.hour--;
							   sQ.add(c);
							   Q2.put(cName, sQ);
							   changeCourseStatus(courseStatus,c.cName);
						   }
					   }
				   break;
			   case 5: //全是次课
				   for(int j = 1; j <= table3D.courseNum; j++){
					   cTask c = getCourseFromSubQ(1,i,j,cName,courseStatus);
					   sQ = Q2.get(cName);//读取最新次链队
					   if(c.hour != -1){//排次课
						   //mainFrame.mainTable.getModel().setValueAt(c.courseName, j-1, i-1);
						   c.hour--;
						   sQ.add(c);
						   Q2.put(cName, sQ);
						   changeCourseStatus(courseStatus,c.cName);
					   }
				   }
			   break;
			   }
		   }
	   }
	   public void loadTask(){
		   Sheet st = fileEditor.book.getSheetAt(0);
			for(int i = 1; i <= st.getLastRowNum(); i++)
			{
				Row row = st.getRow(i);
				if(row == null){
					continue;
				}
				cTask task = new cTask();
				for(int j = 0; j < row.getLastCellNum(); j++){
					/*
					if(row.getCell(j)!=null){
						//先设置Cell的类型，然后就可以把纯数字作为String类型读进来了：
						row.getCell(j).setCellType(Cell.CELL_TYPE_STRING);
						Cell cell = row.getCell(j);
						System.out.println((String)cell.getStringCellValue());
						}
					}*/
					if(row.getCell(j) != null){
						//row.getCell(j).setCellType(Cell.CELL_TYPE_STRING);
						Cell cell = row.getCell(j);
						switch (j){
						case 0:  //课程ID
							row.getCell(j).setCellType(Cell.CELL_TYPE_STRING);
							task.cID = (String)cell.getStringCellValue();
							break;
						case 1:  //课程Name
							row.getCell(j).setCellType(Cell.CELL_TYPE_STRING);
							task.cName = cell.getStringCellValue();
							break;
						case 2:  //专业
							row.getCell(j).setCellType(Cell.CELL_TYPE_STRING);
							task.major = cell.getStringCellValue();
							break;
						case 3:  //班级
							row.getCell(j).setCellType(Cell.CELL_TYPE_STRING);
							task.Class = cell.getStringCellValue();
							initClass(task.Class);
							break;
						case 4:  //理论课时
							row.getCell(j).setCellType(Cell.CELL_TYPE_NUMERIC);
							task.hour = (int) cell.getNumericCellValue();
							task.hour /= 2;
							break;
						case 5:  //实验课时
							row.getCell(j).setCellType(Cell.CELL_TYPE_NUMERIC);
							task.praticedhour = (int) cell.getNumericCellValue();
							task.praticedhour /= 2;
							break;
						case 6:  //教师ID
							row.getCell(j).setCellType(Cell.CELL_TYPE_STRING);
							row.getCell(j).setCellType(Cell.CELL_TYPE_STRING);
							task.tID = (String)cell.getStringCellValue();
							break;
						case 7:  //教师姓名
							row.getCell(j).setCellType(Cell.CELL_TYPE_STRING);
							task.tName = cell.getStringCellValue();
							initTeacher(task.tName);
							break;
						}
						//System.out.println(task.Class);//
					}
				}
				if(!this.task.containsKey(task.Class)){
					this.task.put(task.Class, new Vector<cTask>());
					this.task.get(task.Class).add(task);
				}
				else{
					this.task.get(task.Class).add(task);
				}
			}
	   }
	   public void mergeDisplay(String className){//
		   String str;
		   String str2 = null;
		   @SuppressWarnings("rawtypes")
		   LinkedList Q = display.get(className);
		   for(int i = 0; i < Q.size(); i++)
		   {
			   cTask c = (cTask)Q.get(i);
			   if(c.Class.equals("15-软工-1"))
			   {
				   //System.out.println(c.Class);//--------------------------		   
				   for(int x = 0; x < 2; x++){
					   int day = c.tuplec.get(x).day-1, pos = c.tuplec.get(x).pos-1;
					   String pstr = mainFrame.mainTable.getModel().getValueAt(pos, day).toString();//jtable; row column
					   if(i == 0)
						   str2 = "40教学楼";
					   else if(i > 0 && i < 10)
						   str2 = "4" + i + "教学楼";
					   else if(i > 9)
						   str2 = 40 + i + "教学楼";
					   str = c.cName + "[" + str2 + "(" + String.valueOf(c.start)
					   + "-" + String.valueOf(c.end) + "周)" + "]";
					   mainFrame.mainTable.getModel().setValueAt(pstr.isEmpty()?"<html>"+str:pstr+"<br>"+str, pos,day);
				   }
			   }
			   if(c.Class.equals("15-软工-2"))
			   {
				   for(int x = 0; x < 2; x++){
				   int day = c.tuplec.get(x).day-1, pos = c.tuplec.get(x).pos-1;
				   String pstr = mainFrame.mainTable.getModel().getValueAt(pos, day).toString();
				   if(i == 0)
					   str2 = "40教学楼";
				   else if(i > 0 && i < 10)
					   str2 = "4"+i+"教学楼";
				   else if(i > 9)
					   str2 = 40+i+"教学楼";
				   str = c.cName+"["+str2+"("+String.valueOf(c.start)+"-"
					   		+String.valueOf(c.end)+"周)"+"]";
				   mainFrame.mainTable.getModel().setValueAt
			   					(pstr.isEmpty()?"<html>"+str:pstr+"<br>"+str, pos,day);
				   }		   
			   }
			   if(c.Class.equals("15-软工-3"))
			   {
				   for(int x = 0; x < 2; x++){
				   int day = c.tuplec.get(x).day-1, pos = c.tuplec.get(x).pos-1;
				   String pstr = mainFrame.mainTable.getModel().getValueAt(pos, day).toString();
				   if(i == 0)
					   str2 = "40教学楼";
				   else if(i > 0 && i < 10)
					   str2 = "4"+i+"教学楼";
				   else if(i > 9)
					   str2 = 40+i+"教学楼";
				   str = c.cName+"["+str2+"("+String.valueOf(c.start)+"-"
					   		+String.valueOf(c.end)+"周)"+"]";
				   mainFrame.mainTable.getModel().setValueAt
			   					(pstr.isEmpty()?"<html>"+str:pstr+"<br>"+str, pos,day);
				   }
			   }
		   }
	   }
	   
	   public void mergeNewDisplay(String className){
		   String str;
		   String str2 = null;
		   LinkedList Q = display.get(className);
		   for(int i = 0; i < Q.size(); i++)
		   {
			   cTask c = (cTask)Q.get(i);
			   if(c.Class.equals("15-软工-1"))
			   {
				   //System.out.println(c.Class);//--------------------------
				   
				   for(int x = 0; x < 2; x++){
				   int day = c.tuplec.get(x).day-1, pos = c.tuplec.get(x).pos-1;
				   String pstr = mainFrame2.mainTable2.getModel().getValueAt(pos, day).toString();
				   if(i == 0)
					   str2 = "40教学楼";
				   else if(i > 0 && i < 10)
					   str2 = "4"+i+"教学楼";
				   else if(i > 9)
					   str2 = 40+i+"教学楼";
				   str = c.cName+"["+str2+"("+String.valueOf(c.start)+"-"
					   		+String.valueOf(c.end)+"周)"+"]";
				   mainFrame2.mainTable2.getModel().setValueAt
			   					(pstr.isEmpty()?"<html>"+str:pstr+"<br>"+str, pos,day);
				   }
			   }
			   if(c.Class.equals("15-软工-2"))
			   {
				   for(int x = 0; x < 2; x++){
				   int day = c.tuplec.get(x).day-1,pos=c.tuplec.get(x).pos-1;
				   String pstr = mainFrame2.mainTable2.getModel().getValueAt(pos, day).toString();
				   if(i == 0)
					   str2 = "40教学楼";
				   else if(i > 0 && i < 10)
					   str2 = "4"+i+"教学楼";
				   else if(i > 9)
					   str2 = 40+i+"教学楼";
				   str = c.cName+"["+str2+"("+String.valueOf(c.start)+"-"
					   		+String.valueOf(c.end)+"周)"+"]";
				   mainFrame2.mainTable2.getModel().setValueAt
			   					(pstr.isEmpty()?"<html>"+str:pstr+"<br>"+str, pos,day);
				   }		   
			   }
			   if(c.Class.equals("15-软工-3"))
			   {
				   for(int x = 0; x < 2; x++){
				   int day = c.tuplec.get(x).day-1, pos = c.tuplec.get(x).pos-1;
				   String pstr = mainFrame2.mainTable2.getModel().getValueAt(pos, day).toString();
				   if(i == 0)
					   str2 = "40教学楼";
				   else if(i > 0 && i < 10)
					   str2 = "4"+i+"教学楼";
				   else if(i > 9)
					   str2 = 40+i+"教学楼";
				   str = c.cName+"["+str2+"("+String.valueOf(c.start)+"-"
					   		+String.valueOf(c.end)+"周)"+"]";
				   mainFrame2.mainTable2.getModel().setValueAt
			   					(pstr.isEmpty()?"<html>"+str:pstr+"<br>"+str, pos,day);
				   }
			   }		 
		   }
	   }
	   
	   public void Serialize() throws IOException
	   {
		   if(!fileEditor.load("course.xlsx"))
		   {
			   fileEditor.create("course.xlsx");
			   fileEditor.load("course.xlsx");
		   }
	   }
	   public void initTeacher(String tName){
		   if(!tSchedule.containsKey(tName)){
			   tSchedule.put(tName, new tSchedule());
		   }
	   }
	   public void initClass(String cName){
		   if(!cSchedule.containsKey(cName)){
			   cSchedule.put(cName, new cTable());
		   }
	   }
	
	   public static void main(String args[]) throws IOException, UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException{
			
		    ArrangeCourse doc = new ArrangeCourse();
			//System.out.println("success1");//test
			DisplayS mainFrame = new DisplayS(doc);
			mainFrame.setResizable(false);
			doc.mainFrame = mainFrame;
			//System.out.println("success2");//test
			mainFrame.setTitle("智能排课系统-"+"15-软工-1"+"班-课表展示");
			//mainFrame.setTitle("智能排课系统-"+"15-软工-2"+"班-课表展示");
			//mainFrame.setTitle("智能排课系统-"+"15-软工-3"+"班-课表展示");
			
			JButton button = new JButton("调整课表");
			button.setFont(new Font("宋体", Font.PLAIN, 12));
			button.setBounds(555, 27, 82, 25);
			mainFrame.getContentPane().add(button);
			//System.out.println("success3");//test
			button.addMouseListener(new MouseAdapter(){
			    public void mouseClicked(MouseEvent e){
			       ChangeWindow.main(args);
			       mainFrame.dispose();
			    }
			});
			//22
			doc.weekInit("15-软工-1");
			for(int i = 2; i < 22; i++) //到2-22周
				doc.weekNext("15-软工-1", i);
		        doc.mergeDisplay("15-软工-1");//BUGBUGBUGBUGBUGBUGBUGBUGBUGAAAAAAAA
		        
			doc.weekInit("15-软工-2");
			for(int i = 2; i < 22; i++) 
				doc.weekNext("15-软工-2", i);
			    //doc.mergeDisplay("15-软工-2");
			
			doc.weekInit("15-软工-3");
			for(int i = 2;i < 22; i++) 
				doc.weekNext("15-软工-3", i);
			    //doc.mergeDisplay("15-软工-3");
			    mainFrame.setVisible(true);
		}   
}

