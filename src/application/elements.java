package application;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

// 定义墙体和障碍物
class Block{
	private double left,right,top,bottom;
	public Block(float l,float r,float t,float b) {
		// TODO Auto-generated constructor stub
		left=l;
		right=r;
		top=t;
		bottom=b;
	}
	public boolean inAera(double x,double y) {
		if(x>left&&x<right&&y>top&&y<bottom) return true;
		else {
			return false;
		}
	}
}

// 抽象类：地图点
abstract class Point{
	public double radius=20;// 这个半径是矩形半径
	public double x,y;// 位置坐标
	public boolean inAera(double a,double b) {//判断x，y是否处于触发范围
		return (a>=x-radius&&a<=x+radius&&b>=y-radius&&b<=y+radius);
	}
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
}

// 定义npc
class NPC extends Point{
	private String name;
	public NPC(double x,double y,String name) {
		// TODO Auto-generated constructor stub
		this.x=x;
		this.y=y;
		this.name=name;
	}
	public NPC() {}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void func() {
		
	}
}

// 定义地图传送点
class TP extends Point{
	private Image img;
	private String to;
	public TP(double x,double y,String to) {
		// TODO Auto-generated constructor stub
		radius=12;
		this.x=x;
		this.y=y;
		this.to=to+".fxml";
	}
	public Scene TPto(){
		Parent root = null;
		try {
			root = FXMLLoader.load(getClass().getResource(to));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return new Scene(root);
	}
}


////全局：
//public class elements{
//	
//}
