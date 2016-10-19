package Current;

/**
 * Created by makisucruse on 2016/10/19.
 */
public class Client {
    public static void main(String[] args) {
        Watched watched = new Watched();
        Watcher watcher=new Watcher(watched);
        watched.setData("state1");
        watched.setData("state2");
        watched.setData("state3");
    }
}
