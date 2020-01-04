package ch15;

import java.util.concurrent.*;

public class ScheduledExecutorServiceExample {

    public static void main(String[] args) {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);

        work1();
        scheduledExecutorService.schedule(ScheduledExecutorServiceExample::work2, 5, TimeUnit.SECONDS);
        scheduledExecutorService.shutdown();
    }


    public static void work1() {
        int cnt = 0;
        while(cnt < 5) {
            System.out.println("Work #1 is doing.");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            cnt++;
        }
        System.out.println("Work #1 has done.");
    }

    public static void work2() {
        int cnt = 0;
        while(cnt < 5) {
            System.out.println("Work #2 is doing.");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            cnt++;
        }
        System.out.println("Work #2 has done.");
    }
}
