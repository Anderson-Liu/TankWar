import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.exit;

/**
 *
 * @author Anderson
 *
 */

public class TankClient extends Frame{
	public final static int Game_wigth = 800;
	public final static int Game_height = 600;
	private static int time = 1;
	Tank myTank = new Tank(500, 500, true, Tank.Direction.STOP, this);
	Wall w1 = new Wall(220, 150, 10, 250, this);
	Wall w2 = new Wall(400, 200, 220, 10, this);
	Blood b = new Blood();
	Image offScreenImage = null;
	List<Missile> msList = new ArrayList<>();
	List<Explode> explodes = new ArrayList<>();
	List<Tank> tanks = new ArrayList<Tank>();

	public static void main(String[] args) {
		TankClient tc = new TankClient();
		tc.launchFrame();
	}

	// initial launch the main frame
	public void launchFrame(){
		for(int i=0; i<10; i++){
			tanks.add(new Tank(50 + 40*(i+1), 90, false,Tank.Direction.D,this));
		}
		this.setLocation(400, 300);
		this.setSize(Game_wigth, Game_height);
		this.setVisible(true);
		this.setResizable(false);
		this.setTitle("TankWar");
		this.setBackground(Color.WHITE);
		new Thread(new WindowThread(this)).start();
		new Thread(new KeyThread(this)).start();
		new Thread(new PaintThread()).start();
	}

	@Override
	public void paint(Graphics g) {
		g.drawString("missiles count: " + msList.size(), 40, 60);
		g.drawString("explodes count: " + explodes.size(), 40, 70);
		g.drawString("Tank count: " + tanks.size(), 40, 80);
		g.drawString("Life count: " + myTank.getLife(), 40, 90);
		myTank.draw(g);
		myTank.eat(b);
		myTank.hitWall(w1);
		myTank.hitWall(w2);
		w1.draw(g);
		w2.draw(g);
		if(b.isLive()){
			b.draw(g);
		}

		// While Missile done it's work, remove.
		for(int i=0; i<msList.size(); i++){
			Missile m = msList.get(i);
			if (!m.isLive())
				msList.remove(m);
			else m.draw(g);
			m.hitTanks(tanks);
			m.hitTank(myTank);
			m.hitWall(w1);
			m.hitWall(w2);
		}

		// While explodes done, remove it.
		for(int i=0; i<explodes.size(); i++){
			Explode e = explodes.get(i);
			if (!e.isLive())
				explodes.remove(e);
			e.draw(g);
		}

		for(int i=0; i<tanks.size(); i++){
			Tank t = tanks.get(i);
			t.draw(g);
			t.hitWall(w1);                // Whether the tank hit the wall,
			t.hitWall(w2);                // if true, let it stay at the place
			t.hitEach(tanks);            // Whether the tank hit each others.
			//t.hitEach(t1);
		}

		if(tanks.size() <= 0){
			if(time < 5){
				time ++;
				// 一次new太多个出来会导致Bug
				// 因为横向画不下，所以导致了画在主界面外面
				for (int i = 0; i < time * 3; i++) {
					if (40 * (i + 1) > this.Game_wigth) {
						JOptionPane.showMessageDialog(this, "The count of Tank is too many to show!\n" +
								"Please reduce the Tank's amount!");
						exit(0);
					}
					tanks.add(new Tank(50 + 40*(i+1), 90, false,Tank.Direction.D,this));
				}
			}

		}
	}

	public void update(Graphics g) {
		if (offScreenImage == null) {
			offScreenImage = this.createImage(Game_wigth, Game_height);
		}
		Graphics gOffScreen = offScreenImage.getGraphics();
		Color c = gOffScreen.getColor();
		gOffScreen.setColor(Color.WHITE);
		gOffScreen.fillRect(0, 0, Game_wigth, Game_height);
		gOffScreen.setColor(c);
		paint(gOffScreen);
		g.drawImage(offScreenImage, 0, 0, null);
	}

	private class KeyMonitor extends KeyAdapter {

		@Override
		public void keyPressed(KeyEvent e) {
			myTank.keyPress(e);
		}

		@Override
		public void keyReleased(KeyEvent e) {
			myTank.keyReleased(e);
		}

	}

	private class PaintThread implements Runnable {

		@Override
		public void run() {
			while (true) {
				try {
					Thread.sleep(40);
					repaint();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
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
					exit(0);
				}
			});
		}
	}

}
