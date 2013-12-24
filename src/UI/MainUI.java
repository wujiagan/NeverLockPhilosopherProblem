package UI;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.Semaphore;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import philosopher.Philosopher;

public class MainUI extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static BackgroundPanel backgroundPanel = new BackgroundPanel();;
	
	/**
	 * 定义5个信号量来分别代表5把fork
	 */
	private final Semaphore[] fork = {
			new Semaphore(1, true),
			new Semaphore(1, true),
			new Semaphore(1, true),
			new Semaphore(1, true),
			new Semaphore(1, true),
	};
	private ImageIcon forkIcon = new ImageIcon(
			getClass().getResource("/UI/image/fork.png"));
	private	ImageIcon play = new ImageIcon(
			getClass().getResource("/UI/image/play.png"));
	private	ImageIcon stop = new ImageIcon(
					getClass().getResource("/UI/image/pause.png"));
	
	/**
	 * 标志当前状态true为运行, false为暂停
	 */
	private boolean status = true;
	
	private JButton playBtn = new JButton(stop);
	
	/**
	 * 显示5把叉子
	 */
	private JLabel forkLab[] = {
			new JLabel(forkIcon),
			new JLabel(forkIcon),
			new JLabel(forkIcon),
			new JLabel(forkIcon),
			new JLabel(forkIcon)
	};
	
	/**
	 * 显示5个哲学家
	 */
	private Philosopher[] philosopher = {
			new Philosopher(1, fork[0], fork[1], forkLab[0], forkLab[1]),
			new Philosopher(2, fork[1], fork[2], forkLab[1], forkLab[2]),
			new Philosopher(3, fork[2], fork[3], forkLab[2], forkLab[3]),
			new Philosopher(4, fork[3], fork[4], forkLab[3], forkLab[4]),
			new Philosopher(5, fork[4], fork[0], forkLab[4], forkLab[0])
	};
	
	public MainUI() {
		this.setTitle("不死锁的哲学家问题");
		
		this.setLayout(new GridLayout(1 , 1));
		this.add(backgroundPanel);
		init();
		
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				System.exit(1);
			}
		});
	
		this.setSize(860,620);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation((screen.width-this.getSize().width)/2,
				(screen.height-this.getSize().height)/2);
		this.setResizable(false);
		this.setVisible(true);
	}
	
	private void init() {
		backgroundPanel.setLayout(null);
		
		for(int i = 0; i< 5; i++)
			forkLab[i].setIcon(forkIcon);		
		
		philosopher[0].setBounds(330, 40, 180, 130);
		philosopher[1].setBounds(90, 200, 180, 130);
		philosopher[2].setBounds(200, 430, 180, 130);
		philosopher[3].setBounds(480, 430, 180, 130);
		philosopher[4].setBounds(590, 200, 180, 130);
		
		forkLab[1].setBounds(260, 120, 20, 80);
		forkLab[2].setBounds(220, 335, 20, 80);
		forkLab[3].setBounds(410, 450, 20, 80);
		forkLab[4].setBounds(605, 315, 20, 80);
		forkLab[0].setBounds(545, 120, 20, 80);
		
		for(int i = 0; i < 5; i++) {
			backgroundPanel.add(philosopher[i]);
			backgroundPanel.add(forkLab[i]);
		}
		
		for(int i = 0; i < 5; i++)
			new Thread((Runnable)philosopher[i]).start();
		
		backgroundPanel.add(playBtn);
		playBtn.setBounds(750, 500, 60, 60);
		playBtn.setContentAreaFilled(false);
		playBtn.setBorderPainted(false);
		playBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
		
		playBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(isPlay()) {
					stop();
					setStatus(false);
				}
				else {
					play();
					setStatus(true);
				}
			}
		});
	}
	
	public boolean isPlay() {
		return status;
	}
	
	public void setStatus(boolean status) {
		this.status = status;
	}
	
	public void play() {
		playBtn.setIcon(stop);
		for(int i = 0; i < 5; i++)
			philosopher[i].setSuspend(false);
	}
	
	public void stop() {
		playBtn.setIcon(play);
		for(int i = 0; i < 5; i++)
			philosopher[i].setSuspend(true);
	}
	
}
