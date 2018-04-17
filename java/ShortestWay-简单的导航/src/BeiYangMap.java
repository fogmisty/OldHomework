//import java.awt.GridLayout;
import java.awt.Font;
import java.util.*;

import javax.swing.*;

//最短路径问题
public class BeiYangMap extends JFrame{
	
	//JLabel fromchoose, tochoose;
	//JPanel jp1,jp2,jp3;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public BeiYangMap(){
		//设置标题  
        super("简单的结果");  
        //设置大小  
        setSize(540, 360);  
        //设置可见  
        setVisible(true);  
        //点关闭按钮时退出  
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
	}
	 
	public class Way{
		//对象
		String from;//起点
		String to;//终点
		int cost;//权
	}
	
	Map map = new HashMap();//存储所有路线
	List reachedWay = new ArrayList();//存储到达目的地所经过的地点
	Map routeMap = new HashMap();//存储到达目的地所经过的地点和路程，key为路程，value为reachedWay
	//int shortestTime = 0;//最短时间，只输出最短路径
	
	//添加路线，双向添加
	public void addRoute(String p1, String p2, int cost)//地点1，地点2，权（时间／长度）
	{
		List plist1 = (List)map.get(p1);//地点1路线集合
		if(plist1 == null){
			plist1 = new ArrayList();
			map.put(p1, plist1);
		}
		
		Way way1 = new Way();
		way1.from = p1;
		way1.to = p2;
		way1.cost = cost;
		
		//不存在该路线则添加
		if(!plist1.contains(way1)){
			plist1.add(way1);
		}
		
		//
		List plist2 = (List)map.get(p2);//地点2路线集合
		if(plist2 == null){
			plist2 = new ArrayList();
			map.put(p2, plist2);
		}
		
		Way way2 = new Way();
		way2.from = p2;
		way2.to = p1;
		way2.cost = cost;
		
		//不存在该路线则添加
		if(!plist2.contains(way2)){
			plist2.add(way2);
		}
	}
	
	//计算最短路径，最短时间
	public void run(String from, String to){
		int tempTime = 0;//存储所花时间的临时变量
		if(reachedWay.contains(from)){//走过的不走
			return;
		}
		reachedWay.add(from);//存储经过的地方
		if(reachedWay.size() > 1){
			//计算所花时间
			List initList = (List)map.get(reachedWay.get(0));
			for(int i = 0; i < initList.size(); i++){
				Way w = (Way)initList.get(i);
				if(w.to.equals(reachedWay.get(1))){
					tempTime += w.cost;
					
					//only 最短路径
					//if(shortestTime != 0 && tempTime > shortestTime){
						//return;
					//}
				}
			}
			for(int i = 1; i < reachedWay.size(); i++){
				//所经过的城市用时加起来
				List toList = (List)map.get(reachedWay.get(i));
				for(int j = 0; j < toList.size(); j++){
					Way w = (Way)toList.get(j);
					if(i + 1 < reachedWay.size()){
						if(w.to.equals(reachedWay.get(i + 1))){
							tempTime += w.cost;
							
							//
							//if(shortestTime != 0 && tempTime > shortestTime){
								//return;
							//}
						}
					}
				}
			}
		}
		
		//到达
		if(from.equals(to)){
			//shortestTime = tempTime;//
			String route = reachedWay.get(0).toString();
			for(int i = 1; i < reachedWay.size(); i++){
				route += "→" + reachedWay.get(i).toString();
			}
			//System.out.println(route + "\t距离: " + tempTime + "米");//test
			
			//可爱的输出
			//有毛病啊啊啊啊啊啊啊啊啊啊啊啊啊啊
			/*
			this.getContentPane().setLayout(null);
			String rs1 = route + "\t距离: " + tempTime + "米";
			JLabel print1 = new JLabel(rs1);
			print1.setBounds(10, 10, 520, 200);
			print1.setFont(new Font("宋体", Font.PLAIN, 16));
			print1.setHorizontalAlignment(SwingConstants.CENTER);
			this.getContentPane().add(print1);*/
			
			routeMap.put(tempTime, route);
			tempTime = 0;
			reachedWay.remove(reachedWay.size() - 1);//到达后，退回去，走下一路线
			return;
		}
		//没到达
		//获得from城市能够到达的城市列表
		List routeList = (List)map.get(from);
		for(Iterator iterator = routeList.iterator(); iterator.hasNext();){
			Way way = (Way)iterator.next();
			run(way.to, to);
		}
		reachedWay.remove(reachedWay.size() - 1);//走完退回去走下一路线
	}
	
	//输出用时最短的路线
	public void show(String p1, String p2){
		try{
			run(p1, p2);
			Set s = routeMap.keySet();
			Object[] a = s.toArray();
			int shortestTime = (Integer)a[0];
			for(int i = 0; i < a.length; i++){
				if((Integer)a[i] < shortestTime){
					shortestTime = (Integer)a[i];
				}
			}
			//System.out.println("\n最短路径为: \n" + routeMap.get(shortestTime)
			//+ "\t距离: " + shortestTime + "米");
			
					
			//另一个可爱的输出
			String rs2 = "最短路径为: \n" + routeMap.get(shortestTime) + "\t距离: " + shortestTime + "米";
			JLabel print2 = new JLabel(rs2);
			print2.setBounds(10, 10, 520, 100);
			print2.setFont(new Font("宋体", Font.PLAIN, 16));
			print2.setHorizontalAlignment(SwingConstants.CENTER);
			this.getContentPane().add(print2);
		
		}
		catch(Exception e1){
			JOptionPane.showMessageDialog(null, "抱歉，此行程尚未添加");
		}		
	}
	
	/*public static void main(String[] args){
		new BeiYangMap();
		BeiYangMap map = new BeiYangMap();
		map.addRoute("格园", "诚园", 1024);
		map.addRoute("诚园七斋", "第五十教学楼", 473);
		map.addRoute("诚园七斋", "诚园六斋", 350);
	}*/
}
























