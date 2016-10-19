/**
 * Created by makisucruse on 2016/10/19.
 */
public class ConcreteSubject extends Subject {
    private String state;

    public String getState() {
        return state;
    }

    public void change(String newState) {
        state = newState;
        System.out.println("主题状态:" + state);
        notifyObservers();
    }

}
