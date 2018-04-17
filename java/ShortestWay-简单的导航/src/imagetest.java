import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class imagetest {

	static int flag = 0;
	public static void line(String fp, String tp) {
		// TODO Auto-generated method stub
	    if((fp == "诚园" && tp == "格园") || (fp == "格园" && tp == "诚园")) {
	    	 flag = 1;
	    	 imagetest.paint();
	    }	   
	}

	public static void paint(){
		Graphics g = null;
		g.drawLine(40,40,40,250); 
		g.drawLine(40,250,300,250); 
		g.setColor(Color.RED);
	}
}
