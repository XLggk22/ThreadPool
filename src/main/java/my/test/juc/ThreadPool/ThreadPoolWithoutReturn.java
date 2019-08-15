package my.test.juc.ThreadPool;

import java.util.Random;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import io.netty.util.concurrent.DefaultThreadFactory;

/**
 * Hello world!
 *
 */
public class ThreadPoolWithoutReturn {
	public static void main(String[] args) {
		ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(5, 5, 0, TimeUnit.MINUTES,
				new LinkedBlockingDeque<Runnable>(10), new DefaultThreadFactory("NotifyCTS"));

		for (int i = 0; i < 10; i++) {
			try {
				poolExecutor.execute(new Runnable() {
					@Override
					public void run() {
						Random random = new Random();
						int nextInt = random.nextInt(2);
						if (nextInt % 2 == 0) {
//							int kk = nextInt / 0;
							System.out.println("---异常一次---");
						} else {
							System.out.println("+++正常一次+++");
						}
					}
				});
			} catch (Exception e) {
				System.out.println("捕获异常");
			}

		}
		
		poolExecutor.shutdown();
		try {// 等待直到所有任务完成
			poolExecutor.awaitTermination(10, TimeUnit.MINUTES);
		} catch (InterruptedException e) {
			e.printStackTrace();
			System.out.println("线程池关闭异常！");
		}

		System.out.println("你妹-++++++++++++++++++++++++++++++++");
	}
}
