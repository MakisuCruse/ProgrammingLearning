/**
 * Created by makisucruse on 2016/10/19.
 */
public class Client {
    public static void main(String[] args) {
        ConcreteObserver observer = new ConcreteObserver();
        Subject subject= new ConcreteSubject();
        subject.attach(observer);
        ((ConcreteSubject)subject).change("state change..");
    }
}
