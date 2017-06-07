package core.model;

/**
 * Created by makisucruse on 2017/4/12.
 */
public class TweetDescription {
    private String position;
    private String time;
    private String person;
    private String event;

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    @Override
    public String toString() {
        return "TweetDescription{" +
                "position='" + position + '\'' +
                ", time='" + time + '\'' +
                ", person='" + person + '\'' +
                ", event='" + event + '\'' +
                '}';
    }
}
