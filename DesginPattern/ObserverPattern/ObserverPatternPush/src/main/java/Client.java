/**
 * Created by makisucruse on 2016/10/19.
 */
public class Client {
    public static void main(String[] args) {
        Observer observer=new ConcreteObserver();
        ConcreteSubject subject=new ConcreteSubject();
        subject.attach(observer);
        subject.change("new state");

    }
}
