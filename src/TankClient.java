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
	}
	
	@Override
	public void paint(Graphics g) {
		tc.draw(g);
	}
	
	public static void main(String[] args) {
		new TankClient().launchFrame();
	}

}
