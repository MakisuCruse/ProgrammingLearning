package core.model;

/**
 * Created by makisucruse on 2017/6/2.
 */
public class Topic {
    private String topicName;
    private String description;
    private String topicType;
    private String onlineUrl;
    private String localUrl;

    public Topic(String topicName, String description, String topicType, String onlineUrl, String localUrl) {
        this.topicName = topicName;
        this.description = description;
        this.topicType = topicType;
        this.onlineUrl = onlineUrl;
        this.localUrl = localUrl;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTopicType() {
        return topicType;
    }

    public void setTopicType(String topicType) {
        this.topicType = topicType;
    }

    public String getOnlineUrl() {
        return onlineUrl;
    }

    public void setOnlineUrl(String onlineUrl) {
        this.onlineUrl = onlineUrl;
    }

    public String getLocalUrl() {
        return localUrl;
    }

    public void setLocalUrl(String localUrl) {
        this.localUrl = localUrl;
    }

    @Override
    public String toString() {
        return "Topic{" +
                "topicName='" + topicName + '\'' +
                ", description='" + description + '\'' +
                ", topicType='" + topicType + '\'' +
                ", onlineUrl='" + onlineUrl + '\'' +
                ", localUrl='" + localUrl + '\'' +
                '}';
    }
}
