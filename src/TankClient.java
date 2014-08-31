import java.awt.*;
import java.awt.event.*;

public class TankClient extends Frame{
	Tank tc = new Tank(50,50);
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
		this.addWindowListener(new WindowAdapter(){

			@Override
			public void windowClosing(WindowEvent arg0) {
				System.exit(0);
			}
		});
		this.addKeyListener(new KeyMonitor());
		new Thread(new PaintThread()).start();
	}
	
	@Override
	public void paint(Graphics g) {
		tc.draw(g);
	}
	
	private class KeyMonitor extends KeyAdapter{

		@Override
		public void keyPressed(KeyEvent e) {
			tc.keyPress(e);
		}

		@Override
		public void keyReleased(KeyEvent e) {
			tc.keyReleased(e);
		}
		
	}
	
	private class PaintThread implements Runnable{

		@Override
		public void run() {
			while(true){
				try {
					repaint();
					Thread.sleep(30);
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
		paint(gOffScreen);
		g.drawImage(offScreenImage, 0, 0, null);
	}
	
	public static void main(String[] args) {
		new TankClient().launchFrame();
	}

}
