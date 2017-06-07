import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by makisucruse on 2017/5/25.
 */
public class NIOTest {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        System.out.println(System.currentTimeMillis());
        for (int i = 0; i < 1000; i++) {
            System.out.println(i);
            executorService.submit((Runnable) () -> {
                try {
                    Thread.sleep(1000);
                    System.out.println("after sleep");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        executorService.shutdown();
    }
}
