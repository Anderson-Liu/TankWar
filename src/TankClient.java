import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class TankClient extends Frame{
	Tank myTank = new Tank(500,500,true,Tank.Direction.STOP,this);
	Wall w1 = new Wall(220, 150, 10, 250, this);
	Wall w2 = new Wall(400, 200, 220, 10, this);
	//Tank enemyTank = new Tank(80,110,false,this);
	
	Image offScreenImage = null;
	public final static int Game_wigth = 800;
	public final static int Game_height = 600;
	List<Missile> msList = new ArrayList<Missile>(); 
	List<Explode> explodes = new ArrayList<Explode>();
	List<Tank> tanks = new ArrayList<Tank>();
	
	public void launchFrame(){
		for(int i=0; i<10; i++){
			tanks.add(new Tank(50 + 40*(i+1), 90, false,Tank.Direction.D,this));
		}
		this.setLocation(400, 300);
		this.setSize(Game_wigth, Game_height);
		this.setVisible(true);
		this.setResizable(false);
		this.setTitle("TankWar");
		this.setBackground(Color.GREEN);
		new Thread(new WindowThread(this)).start();
		new Thread(new KeyThread(this)).start();
		new Thread(new PaintThread()).start();
	}
	
	@Override
	public void paint(Graphics g) {
		g.drawString("missiles count: " + msList.size(), 40, 60);
		g.drawString("explodes count: " + explodes.size(), 40, 70);
		g.drawString("Tank count: " + tanks.size(), 40, 80);
		myTank.draw(g);
		myTank.hitWall(w1);
		myTank.hitWall(w2);
		w1.draw(g);
		w2.draw(g);
		//enemyTank.draw(g);
		
		for(int i=0; i<msList.size(); i++){
			Missile m = msList.get(i);
			if(!m.isLive())	msList.remove(m);
			else m.draw(g);
			m.hitTanks(tanks);
			m.hitTank(myTank);
			m.hitWall(w1);
			m.hitWall(w2);
		}
		for(int i=0; i<explodes.size(); i++){
			Explode e = explodes.get(i);
			if(!e.isLive())	explodes.remove(e);
			//else e.draw(g);
			e.draw(g);
		}
		for(int i=0; i<tanks.size(); i++){
			Tank t = tanks.get(i);
			t.draw(g);
			t.hitWall(w1);
			t.hitWall(w2);
			t.hitEach(tanks);
			//t.hitEach(t1);
		}
	}
	
	private class KeyMonitor extends KeyAdapter{

		@Override
		public void keyPressed(KeyEvent e) {
			myTank.keyPress(e);
		}

		@Override
		public void keyReleased(KeyEvent e) {
			myTank.keyReleased(e);
		}
		
	}
	
	private class PaintThread implements Runnable{

		@Override
		public void run() {
			while(true){
				try {
					Thread.sleep(30);
					repaint();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void update(Graphics g){
		if(offScreenImage == null){
			offScreenImage = this.createImage(Game_wigth, Game_height);
		}
		Graphics gOffScreen =  offScreenImage.getGraphics();
		Color c = gOffScreen.getColor();
		gOffScreen.setColor(Color.GREEN);
		gOffScreen.fillRect(0, 0, Game_wigth, Game_height);
		gOffScreen.setColor(c);
		paint(gOffScreen);
		g.drawImage(offScreenImage, 0, 0, null);
	}
	
	private class KeyThread implements Runnable{
		TankClient tc_1 =  new TankClient();
		public KeyThread(TankClient tc_1){
			this.tc_1 = tc_1;
		}
		@Override
		public void run() {
			tc_1.addKeyListener(new KeyMonitor());
		}
	}
		
	private class WindowThread implements Runnable{
		TankClient tc_1 =  new TankClient();
		public WindowThread(TankClient tc_1){
			this.tc_1 = tc_1;
		}
		@Override
		public void run() {
			tc_1.addWindowListener(new WindowAdapter(){
				@Override
				public void windowClosing(WindowEvent arg0) {
					System.exit(0);
				}
			});
		}
	}
	
	public static void main(String[] args) {
		TankClient tc = new TankClient();
		tc.launchFrame();
	}

}
