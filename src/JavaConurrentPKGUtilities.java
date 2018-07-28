import java.sql.Time;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

final class BankAccount
{
	public volatile int balance;
	Lock ObjLock=new ReentrantLock();
	ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

	
	public BankAccount(int balance)
	{
		this.balance=balance;
	}
	
	public static boolean BankAmountTransferFromOneActToOther(final BankAccount from,final BankAccount to,int Amt)
	{	
		System.out.println("BankAmountTransferFromOneActToOther");
		
		/*try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}*/
		while(true)
		{
		
			
			if(from.ObjLock.tryLock())
			{
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try
				{
					if(to.ObjLock.tryLock())
					{
						try
						{
							if(from.balance<Amt)
							{
								System.out.println("Cant transfer");
								return false;
								
							}
							from.balance=from.balance-Amt;
							to.balance=to.balance+Amt;
//							
							return true;
							
						}finally {
							
							to.ObjLock.unlock();
						}
						
						
					}
				}
				finally {
					from.ObjLock.unlock();
				}
				

				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
		
		
	}
	
	
}
public class JavaConurrentPKGUtilities {

	BlockingQueue<Integer> ObjQueue = new LinkedBlockingQueue<Integer>(3);
	
	public JavaConurrentPKGUtilities()
	{
		ProducerConsmerProblem();
		System.out.println("Producer consumer");
		TransferMoney();
		
		
		
	}
	private void TransferMoney() {
		BankAccount From=new BankAccount(1000);
		BankAccount To=new BankAccount(1000);
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				System.out.println("First");
				boolean status=BankAccount.BankAmountTransferFromOneActToOther(From, To, 100);
				if(status)
				{
					System.out.println("From act balance1:"+From.balance);
					System.out.println("to act balance1:"+To.balance);
				}
				else
				{
					System.out.println("cant transfer money less balance1");
				}
			}
		}).start();
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				System.out.println("Second");
				boolean status1=BankAccount.BankAmountTransferFromOneActToOther(From, To, 100);
				
				if(status1)
				{
					System.out.println("From act balance2:"+From.balance);
					System.out.println("to act balance2:"+To.balance);
				}
				else
				{
					System.out.println("cant transfer money less balance2");
				}
			}
		}).start();
	
	}
	private void ProducerConsmerProblem() {
		
	Runnable producer=	new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				for(int i=0;i<10;i++)
				{
					try {
						ObjQueue.put(i);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				
			}
		};
		
  Runnable Consumer=	new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				while(true)
				{
					try {
						System.out.println(ObjQueue.take());
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				
			}
		};
		
		Thread t1=new Thread(producer);
		Thread t2=new Thread(Consumer);
		Thread t3=new Thread(
				()->{
					System.out.println("harsh");
					}
				
				);
		t1.start();
		t2.start();
		try {
			t1.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		t3.start();
	}
		
}
