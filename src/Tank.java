import java.awt.*;
import java.awt.event.KeyEvent;


public class Tank {
	
	public static final int XSPEED = 5;
	public static final int YSPEED = 5;
	public static final int WIDTH = 30;
	public static final int HEIGHT = 30;
	int x, y;
	private boolean bL=false,bU=false,bR=false,bD=false;
	
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
	
	public void move(){
		
	}
	
	public void tcDir(){
		
	}

	public void keyPress(KeyEvent e) {
		int dir = e.getKeyCode();
		switch (dir){
		case KeyEvent.VK_LEFT :
			bL = true;
			break;
		case KeyEvent.VK_RIGHT :
			bR = true;
			break;
		case KeyEvent.VK_UP :
			bU = true;
			break;
		case KeyEvent.VK_DOWN :
			bD = true;
			break;	
		}
		
	}

	public void keyReleased(KeyEvent e) {
		
	}
}
