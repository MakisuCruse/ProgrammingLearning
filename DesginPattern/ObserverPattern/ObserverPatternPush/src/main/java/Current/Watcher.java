package Current;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by makisucruse on 2016/10/19.
 */
public class Watcher implements Observer {
    public Watcher(Observable o) {
        o.addObserver(this);
    }

    @Override
    public void update(Observable o, Object arg) {
        System.out.println("状态:"+((Watched) o).getData());
    }
}
