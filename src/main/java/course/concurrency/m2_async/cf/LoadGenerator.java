package course.concurrency.m2_async.cf;

import java.util.stream.IntStream;

public class LoadGenerator {

    public static void work() {
        //sleep();
         compute();
    }

    private static void sleep() {
        try {
            //System.out.print("START : "+Thread.currentThread().getName()+"\n");
            Thread.sleep(1500);
            //System.out.print("END : " + Thread.currentThread().getName()+"\n");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static int compute() {
        //System.out.print("START : "+Thread.currentThread().getName()+"\n");
        int temp = IntStream.range(0, 5_000_000).boxed().filter(i -> i % 2 == 0).reduce((a, b) -> b).get() ;
        //System.out.print("END : " +Thread.currentThread().getName()+"\n");
        return temp;
    }
}