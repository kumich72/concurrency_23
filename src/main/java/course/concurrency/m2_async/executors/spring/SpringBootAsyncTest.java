package course.concurrency.m2_async.executors.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class SpringBootAsyncTest {

    @Autowired
    private AsyncClassTest testClass;

    // this method executes after application start
    @EventListener(ApplicationReadyEvent.class)
    public void actionAfterStartup() {
        testClass.runAsyncTask();
    }

//    public static long longTask() throws InterruptedException {
//        Thread.sleep(1000); // + try-catch
//        return ThreadLocalRandom.current().nextInt();
//    }

    public static void main(String[] args) {
//
//        ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 6, 60, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(1));
//
//        for (int i = 0; i < 10; i++) {
//            executor.submit(() -> longTask());
//            System.out.print(executor.getPoolSize() + " ");
//        }
//
//        executor.shutdown();

        SpringApplication.run(SpringBootAsyncTest.class, args);
    }
}
