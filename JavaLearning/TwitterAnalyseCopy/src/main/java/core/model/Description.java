package core.model;

/**
 * Created by makisucruse on 2017/6/2.
 */
public class Description {
    private String description;
    private Integer score;

    public Description(String description, Integer score) {
        this.description = description;
        this.score = score;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Description{" +
                "description='" + description + '\'' +
                ", score=" + score +
                '}';
    }
}
