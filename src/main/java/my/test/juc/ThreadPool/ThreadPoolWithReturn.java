package my.test.juc.ThreadPool;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import io.netty.util.concurrent.DefaultThreadFactory;

public class ThreadPoolWithReturn {

	public static void main(String[] args) {
		ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(5, 5, 0, TimeUnit.MINUTES,
				new LinkedBlockingDeque<Runnable>(10), new DefaultThreadFactory("NotifyCTS"));
		List<Future<String>> list = new LinkedList<>();
		for (int i = 0; i < 5; i++) {
			ThreadTask task = new ThreadTask();
			Future<String> submit = poolExecutor.submit(task);
			list.add(submit);
		}
		
//		for (Future<String> future : list) {
//			try {
//				System.out.println(System.currentTimeMillis()+" 返回值："+future.get());
//			} catch (InterruptedException | ExecutionException e) {
//				e.printStackTrace();
//			}
//		}
		
		try {
			poolExecutor.shutdown();
			boolean awaitTermination = false;
			int shutdownCount = 0;
			while(!awaitTermination){
				if(shutdownCount == 3){
					System.out.println("强制关闭");
					poolExecutor.shutdownNow();
					break;
				}
				awaitTermination = poolExecutor.awaitTermination(2, TimeUnit.SECONDS);
				shutdownCount ++ ;
				System.out.println("awaitTermination:"+awaitTermination);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("线程池关闭失败");
		}
		System.out.println("结束--------------------");
		
	}
}

class ThreadTask implements Callable<String>{
	
	@Override
	public String call() throws Exception {
		Random random = new Random();
		int nextInt = random.nextInt(5);
		Thread.sleep(10000);
		System.out.println("ThreadTask call(),ThreadName:"+ Thread.currentThread().getName() + ",打印值:" +nextInt);
//		if(nextInt == 1){
////			throw new Exception("测试，抛出异常");
//		}
		return String.valueOf(nextInt);
	}
	
}
