package core.model;

/**
 * Created by makisucruse on 2017/5/22.
 */
public class Person {
    private String personName;
    private String trendName;
    private int followerCount;

    public Person() {
    }

    public Person(String personName, String trendName, int followerCount) {
        this.personName = personName;
        this.trendName = trendName;
        this.followerCount = followerCount;
    }

    public int getFollowerCount() {
        return followerCount;
    }

    public void setFollowerCount(int followerCount) {
        this.followerCount = followerCount;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getTrendName() {
        return trendName;
    }

    public void setTrendName(String trendName) {
        this.trendName = trendName;
    }

    @Override
    public String toString() {
        return "Person{" +
                "personName='" + personName + '\'' +
                ", trendName='" + trendName + '\'' +
                ", followerCount=" + followerCount +
                '}';
    }
}
