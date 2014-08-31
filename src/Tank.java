import java.awt.*;


public class Tank {
	
	public static final int XSPEED = 5;
	public static final int YSPEED = 5;
	public static final int WIDTH = 30;
	public static final int HEIGHT = 30;
	int x, y;
	
	public Tank(int x,int y){
		this.x = x;
		this.y = y;
	}
	
	public void draw(Graphics g){
		Color c = g.getColor();
		g.setColor(Color.RED);
		g.fillOval(x, y, WIDTH, HEIGHT);
		g.setColor(c);
	}
	
	public static void main(String[] args) {
		
	}
}
