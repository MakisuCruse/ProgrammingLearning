/**
 * Created by makisucruse on 2016/10/19.
 */
public class ConcreteObserver implements Observer {
    private String newState;
    
    @Override
    public void update(Subject subject) {
        ConcreteSubject concreteSubject = (ConcreteSubject) subject;
        newState = concreteSubject.getState();
        System.out.println("观察者状态:" + newState);
    }
}
