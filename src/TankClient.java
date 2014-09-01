import java.awt.*;
import java.awt.event.*;

public class TankClient extends Frame{
	Tank MyTank = new Tank(50,50);
	Image offScreenImage = null;
	private final static int Game_wigth = 800;
	private final static int Game_height = 600;
	
	public void launchFrame(){
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
		MyTank.draw(g);
	}
	
	private class KeyMonitor extends KeyAdapter{

		@Override
		public void keyPressed(KeyEvent e) {
			MyTank.keyPress(e);
		}

		@Override
		public void keyReleased(KeyEvent e) {
			MyTank.keyReleased(e);
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
