package application;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

// ����ǽ����ϰ���
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

// �����ࣺ��ͼ��
abstract class Point{
	public double radius=20;// ����뾶�Ǿ��ΰ뾶
	public double x,y;// λ������
	public boolean inAera(double a,double b) {//�ж�x��y�Ƿ��ڴ�����Χ
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

// ����npc
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

// �����ͼ���͵�
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


////ȫ�֣�
//public class elements{
//	
//}
