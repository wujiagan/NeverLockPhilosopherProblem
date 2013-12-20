package UI;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.Semaphore;

import javax.swing.JFrame;

import philosopher.Philosopher;

public class MainUI extends JFrame{
	
	private BackgroundPanel backgroundPanel;
	private Semaphore[] fork = {
			new Semaphore(1),
			new Semaphore(1),
			new Semaphore(1),
			new Semaphore(1),
			new Semaphore(1)
	};
	public MainUI() {
		this.setTitle("不死锁的哲学家问题");
		backgroundPanel = new BackgroundPanel();
		
		this.setLayout(new GridLayout(1 , 1));
		this.add(backgroundPanel);
		backgroundPanel.setLayout(null);
		
		Philosopher[] philosopher = {
				new Philosopher(1, fork[0], fork[1]),
				new Philosopher(2, fork[1], fork[2]),
				new Philosopher(3, fork[2], fork[3]),
				new Philosopher(4, fork[3], fork[4]),
				new Philosopher(5, fork[4], fork[0]),
		};
		for(int i = 0; i < 5; i++)
			backgroundPanel.add(philosopher[i]);
		
		philosopher[0].setBounds(370, 50, 120, 50);
		philosopher[1].setBounds(100, 285, 120, 50);
		philosopher[2].setBounds(245, 520, 120, 50);
		philosopher[3].setBounds(525, 520, 120, 50);
		philosopher[4].setBounds(670, 285, 120, 50);
		
		for(int i = 0; i < 5; i++)
			new Thread((Runnable)philosopher[i]).start();
		
		
		
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				System.exit(1);
			}
		});
	
		
		this.setSize(860,600);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation((screen.width-this.getSize().width)/2,
				(screen.height-this.getSize().height)/2);
		this.setResizable(false);
		this.setVisible(true);

	}
	
}
