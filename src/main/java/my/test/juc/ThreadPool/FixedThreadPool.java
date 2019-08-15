package my.test.juc.ThreadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FixedThreadPool {
	
	 private ExecutorService executor = Executors.newFixedThreadPool(3);
	 
	 public static void main(String[] args) throws InterruptedException {
		 FixedThreadPool t1 = new FixedThreadPool();
		 for(int i = 0; i < 10; i++) {
			 t1.asynTask(i);
		 }
		 System.out.println("---------------");
		 
	}
	    
	 public void asynTask(final int i) throws InterruptedException {
	    	
        executor.submit(new Runnable() {
            
            @Override
            public void run() {
                
                try {
                    Thread.sleep(1000);//方便观察结果
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                
                	System.out.println("第"+i);
                
            }
        });
        
    }

}
