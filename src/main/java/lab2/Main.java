package lab2;

import java.time.LocalTime;
import java.util.function.Function;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        Function longRunningTask = (x) -> {
            try {
                Thread.sleep(2000); // wait for 2 seconds
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return ((int) x) * 2;
        };
        CustomExecutor exec = new CustomExecutor(2);
        FutureResult[] futures = exec.map(longRunningTask,new int[]{1, 2, 3, 4});
        for (FutureResult f : futures) {
            System.out.println(f.getResult() + " - " + LocalTime.now());
            System.out.println(f.getResult());
        }
        exec.shutdown();
// output should be
// after 2 seconds
// 15:35:00 - 1
// 15:35:00 - 4
// and after another 2 seconds
// 15:35:02 - 6
// 15:35:02 - 8
    }
}
