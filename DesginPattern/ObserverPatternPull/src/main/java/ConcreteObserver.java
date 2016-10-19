/**
 * Created by makisucruse on 2016/10/19.
 */
public class ConcreteObserver implements Observer {
    private String observerState;

    @Override
    public void update(String state) {
        observerState = state;
        System.out.println("观察者的状态:" + observerState);
    }
}
