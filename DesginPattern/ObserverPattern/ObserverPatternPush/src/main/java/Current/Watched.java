package Current;

import java.util.Observable;

/**
 * Created by makisucruse on 2016/10/19.
 */
public class Watched extends Observable {
    //被观察者
    private String data = "";

    public String getData() {
        return data;
    }

    public void setData(String date) {
        if (!this.data.equals(date)) {
            this.data = date;
            setChanged();
        }
        notifyObservers();
    }

}
