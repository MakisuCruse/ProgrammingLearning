import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by makisucruse on 2017/6/7.
 */
public class TestThread {
    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            service.submit((Runnable) () -> {
                try {
                    Thread.sleep(1000 * 5);
                    System.out.println(Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        System.out.println("here1?");
        try {
            Thread.sleep(1000 * 60);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("here2");
        service.shutdown();
    }
}
