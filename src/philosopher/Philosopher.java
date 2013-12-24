package philosopher;

import java.awt.Font;
import java.util.concurrent.Semaphore;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;


public class Philosopher extends JButton implements Runnable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ImageIcon eat = new ImageIcon(
			getClass().getResource("/philosopher/image/eat.png"));
	private ImageIcon think = new ImageIcon(
			getClass().getResource("/philosopher/image/think.png"));
	
	/**
	 * 哲学家编号
	 */
	private int id = 0;
	
	private Semaphore leftFork, rightFork; 
	private JLabel leftLab, rightLab;
	
	/**
	 * suspend = ture 线程暂停
	 */
	private boolean suspend = false;

	/**
	 * 只是需要一个对象而已，这个对象没有实际意义
	 */
	private String control = "";
	
	public Philosopher(int id, Semaphore leftFork, Semaphore rightFork, 
			JLabel leftLab, JLabel rightLab) {
		this.id = id;
		this.leftFork = leftFork;
		this.rightFork = rightFork;
		this.leftLab = leftLab;
		this.rightLab = rightLab;
		this.setText("思考");
		this.setIcon(think);
		this.setFont(new Font("宋体", 0, 20));
		this.setBorderPainted(false);
		this.setContentAreaFilled(false);
		this.setIconTextGap(2);  
    	this.setHorizontalTextPosition(JButton.CENTER); 
    	this.setVerticalTextPosition(JButton.BOTTOM);
	}
	
	public void setSuspend(boolean suspend) {
		if (!suspend) {
			synchronized (control) {
				control.notifyAll();
			}
		}
		this.suspend = suspend;
	}

	public boolean isSuspend() {
		return this.suspend;
	}
	
	private void think() throws InterruptedException {
		say("思考");
		this.setIcon(think);
		Thread.sleep(3000);	
	}
	
	private void eat() throws InterruptedException {
		say("吃面");
		this.setIcon(eat);
		Thread.sleep(3000);	
	}
	
	private void say(String str) {
		this.setText(str);
	}
	
	/**
	 * 申请右叉子
	 * @throws InterruptedException
	 */
	private void waitRightFork() throws InterruptedException {
		say("等待右叉子");
		rightFork.acquire();
		rightLab.setVisible(false);
	}
	
	/**
	 * 申请左叉子
	 * @throws InterruptedException
	 */
	private void waitLeftFork() throws InterruptedException {
		say("等待左叉子");
		leftFork.acquire();
		leftLab.setVisible(false);
	}
	
	private void releaseFork() {
		rightLab.setVisible(true);
		rightFork.release();
		leftLab.setVisible(true);
		leftFork.release();
		
	}
	
	public void run() {
		try{
			while(true) {
				think();
				synchronized (control) {
					if (suspend) {
						try {
							control.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
				if(id % 2 == 1) {
					waitLeftFork();
					waitRightFork();
				}
				else {
					waitRightFork();
					waitLeftFork();
				}
				eat();
				releaseFork();
			}
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null, "发生错误了！");
			System.exit(0);
		}
		
	}
	
	

}
