package UI;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class BackgroundPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Image img = null;
	
	public BackgroundPanel() {
		this.setOpaque(false);
	}
	
	/**
	 * 设置背景图
	 * @param g
	 */
	private void paintBackgroundImage(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;
		int width = getWidth();
		int height = getHeight();
		try {
			img = ImageIO.read(getClass().getResource("/UI/image/background.png"));
		} catch (Exception e) {
		}    
		if(img != null)
			g2.drawImage(img, 0, 0, width, height, this);
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		paintBackgroundImage(g);   
	}
}
