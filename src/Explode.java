import java.awt.*;


public class Explode {
	int x,y;
	// The diameter of explode
	// int[] diameter = {4, 7, 12, 18, 26, 32, 49, 52, 47, 30, 14, 6};	// ��ΪͼƬ
	int step = 0;
	private boolean live = true;
	private TankClient tc;

	private static Toolkit tk = Toolkit.getDefaultToolkit();

	private static Image[] imgs ={
		tk.getImage("")
	};
	
	public Explode(int x, int y, TankClient tc){
		this.x = x;
		this.y = y;
		this.tc = tc;
	}
	
	
	public void draw(Graphics g){
		if(!live) return;
		if(step == diameter.length){
			live = false;
			step = 0;
			return;
		}
		
		Color c = g.getColor();
		g.setColor(Color.ORANGE);
		g.fillOval(x, y, diameter[step], diameter[step]);
		g.setColor(c);
		
		step ++;
	}


	public boolean isLive() {
		// TODO Auto-generated method stub
		return live;
	}
	
}
