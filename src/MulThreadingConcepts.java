import java.util.ArrayList;
import java.util.List;

import javax.swing.plaf.synth.SynthLookAndFeel;

/**
 * 
 */

/**
 * @author lenovo
 *
 */
//Simple multithreading

//if there are multiple threads and they are working on some common  same object then in this scenerio syncronized are used
class Singleton
{
	public volatile static  Singleton Obj=null;
	private volatile static int i=0;

	private Singleton()
	{

	}
	//if there is some static field then there must be  lock using syncronized on class
	public static Singleton GetInstance()
	{

		if(Obj==null)
		{
			synchronized(Singleton.class)//double checking 
			{
				if(Obj==null)
				{
					Obj=new Singleton();
				}


			}

		}

		return Obj;

	}

	public static void IncrementCounter()
	{
		synchronized (Singleton.class) {
			setI(getI() + 1);
		}

	}

	public  void IncrementCounter1()
	{
		synchronized (Singleton.class) {
			setI(getI() + 1);
		}

	}
	public static int getI() {
		return i;
	}
	public synchronized static void setI(int i) {
		Singleton.i = i;
	}
}

class Counter
{
	public volatile int counter;
	 private ThreadLocal<Integer> threadLocal =    new ThreadLocal<Integer>();
	/*public synchronized int increment()
	{
		return counter++;
	}*/

	public  int increment()
	{
		synchronized(this)
		{
			return counter++;
		}

	}

}

class IncrementalThread extends Thread
{
	Counter Obj=null;
	public IncrementalThread(Counter Obj)
	{
		this.Obj=Obj;
	}
	public void run()
	{
		for(int i=0;i<10;i++)
		{
			System.out.println(Obj.increment());


		}
	}
}



public class MulThreadingConcepts {

	Boolean status=true;
	
	class EvenThread extends Thread
	{
	
		
		Object lock;
		public EvenThread(Object lock)
		{
			
			this.lock=lock;
		}
		
		public void run()
		{
			for(int i=2;i<=10;)
			{
				synchronized (lock) {
					while(status)
					{
						try {
							lock.wait();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}  
					status=true;
					System.out.println(i +" ");
					i=i+2;
					lock.notify();
				}
				
			}
		}
				
		
		
	}
	class OddThread extends Thread
	{
		
		Object lock;
		public OddThread(Object lock)
		{
			
			this.lock=lock;
		}
		
		public void run()
		{
			for(int i=1;i<=10;)
			{
				
				synchronized (lock) {
					while(!status)
					{
						try {
							lock.wait();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					status=false;
					System.out.println(i +" ");
					i=i+2;
					lock.notify();
				}
				
				
			}
			
		}
	}
	
	public void EvenOddPrint()
	{
		Object lock=new Object();
		EvenThread  ObjEvenThread=new EvenThread(lock);
		OddThread  ObjOddThread=new OddThread(lock);
		ObjEvenThread.start();
		ObjOddThread.start();
	}
	/**
	 * @param args 
	 * @throws InterruptedException 
	 */
	public static void main(String[] args)  {
	
		System.out.println("answer is:"+12345%		62);
		List<Character>ls=new ArrayList<Character>();
		ls.add('h');	ls.add('a');	ls.add('r');
		
		String s="h";
		int i=s.charAt(0)-'a';
		
		System.out.println("Start Multithreading Concepts:"+i);
		Counter ObjCounter=new Counter();
		IncrementalThread ObjThread1=new IncrementalThread(ObjCounter);
		IncrementalThread ObjThread2=new IncrementalThread(ObjCounter);
		/*ObjThread1.start();
		try {
			ObjThread1.join();//now t2 run after t1 died
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ObjThread2.start();
		*/
		
		//Print Even odd threads
		
		MulThreadingConcepts ObjMulThreadingConcepts=new MulThreadingConcepts();
		ObjMulThreadingConcepts.EvenOddPrint();
		
		Object lock=new Object();
		EvenThread  ObjEvenThread= ObjMulThreadingConcepts.new EvenThread(lock);
		OddThread  ObjOddThread=ObjMulThreadingConcepts.new OddThread(lock);
		ObjEvenThread.start();
		ObjOddThread.start();
		JavaConurrentPKGUtilities ObjJavaConurrentPKGUtilities=new JavaConurrentPKGUtilities();
	}

	
	
}
