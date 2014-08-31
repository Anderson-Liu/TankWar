import java.awt.*;
import java.awt.event.*;

public class TankClient extends Frame{
	Tank tc = new Tank(50,50);
	
	public void launchFrame(){
		this.setLocation(400, 300);
		this.setSize(800, 600);
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
	
	public static void main(String[] args) {
		new TankClient().launchFrame();
	}

}
