package philosopher;

import java.awt.Font;
import java.util.concurrent.Semaphore;

import javax.swing.JButton;

public class Philosopher extends JButton implements Runnable{

	private boolean status = false;
	private String eat = "eatting";
	private String think = "thinking";
	private int id = 0;
	private Semaphore leftFork, rightFork; 
	
	
	public Philosopher(int id, Semaphore leftFork, Semaphore rightFork) {
		this.id = id;
		this.leftFork = leftFork;
		this.rightFork = rightFork;
		this.setText(think);
		this.setFont(new Font("宋体", 0, 20));
	}
	
	private void think() throws InterruptedException {
		this.setText(think);
		Thread.sleep(3000);	
	}
	
	private void eat() throws InterruptedException {
		this.setText(eat);
		Thread.sleep(3000);	
	}
	
	
	
	public void run() {
		try{
			while(true) {
				think();
				if(id % 2 == 1) {
					System.out.println(id + "等待左叉子！");
					leftFork.acquire();
					System.out.println(id + "拿到左叉子！");
					System.out.println(id + "等待右叉子！");
					rightFork.acquire();
					System.out.println(id + "拿到右叉子！");
				}
				else {
					System.out.println(id + "等待右叉子！");
					rightFork.acquire();
					System.out.println(id + "拿到右叉子！");
					System.out.println(id + "等待左叉子！");
					leftFork.acquire();
					System.out.println(id + "拿到左叉子！");
				}
				System.out.println(id + "吃面！");
				eat();	
				System.out.println(id + "释放左右叉子！");
				rightFork.release();
				leftFork.release();
			}
		}catch(Exception e) {
			
		}
		
	}
	
	

}
