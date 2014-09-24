import java.awt.*;
import java.util.List;

public class Missile {
	int x,y;
	Tank.Direction dir;
	private TankClient tc;
	private static final int XSPEED = 10;
	private static final int YSPEED = 10;
	public static final int WIDTH = 10;
	public static final int HEIGHT = 10;
	public boolean live = true;
	private boolean good;
	
	public Missile(int x, int y, Tank.Direction dir){
		this.x = x;
		this.y = y;
		this.dir = dir;
	}
	
	public Missile(int x, int y, boolean good,Tank.Direction dir,TankClient tc){
		this(x,y,dir);
		this.good = good;
		this.tc = tc;
	}
	
	public void draw(Graphics g){
		if(!live) return;
		Color c = g.getColor();
		if(good){
			g.setColor(Color.RED);
		}
		else g.setColor(Color.BLACK);
			
		g.fillOval(x, y, WIDTH, HEIGHT);
		g.setColor(c);
		
		move();
	}
	
	public void move(){
		switch(dir){
		case L :
			x -= XSPEED;
			break;
		case LU :
			x -= XSPEED;
			y -= YSPEED;
			break;
		case U :
			y -= YSPEED;
			break;
		case RU :
			x += XSPEED;
			y -= YSPEED;
			break;
		case R :
			x += XSPEED;
			break;
		case RD :
			x += XSPEED;
			y += YSPEED;
			break;
		case D :
			y += YSPEED;
			break;
		case LD :
			x -= XSPEED;
			y += YSPEED;
			break;	
		}	
		
		if (x < 0 || x > TankClient.Game_wigth || y < 0 || y > TankClient.Game_height ){
			live = false;
			tc.msList.remove(this);
		}
	}
	
	public boolean isLive(){
		return live;
	}

	public Rectangle  getRect(){
		return new Rectangle(x,y,WIDTH,HEIGHT);
	}
	
	public boolean hitTank(Tank t){
		if(this.live && this.getRect().intersects(t.getRect()) && t.isLive() && this.good != t.isGood()){
			t.setLive(false);
			this.live = false;
			Explode e = new Explode(x, y, tc);
			tc.explodes.add(e);
			return true;
		}
		return false;
	}
	
	public boolean hitTanks(List<Tank> tanks){
		for(int i=0; i<tanks.size(); i++){
			if(hitTank(tanks.get(i))){
				return true;
			}
		}
		return false;
	}
	
}
