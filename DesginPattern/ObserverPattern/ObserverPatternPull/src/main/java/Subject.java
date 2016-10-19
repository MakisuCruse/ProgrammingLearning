import java.util.ArrayList;
import java.util.List;

/**
 * Created by makisucruse on 2016/10/19.
 */
public abstract class Subject {
    private List<Observer> list = new ArrayList<>();

    public void attach(Observer observer) {
        list.add(observer);
        System.out.println("observer attach");
    }

    public void detach(Observer observer) {
        list.remove(observer);
        System.out.println("observer detach");
    }

    public void notifyObservers(String newState) {
        for (Observer observer : list) {
            observer.update(newState);
        }
    }

}
