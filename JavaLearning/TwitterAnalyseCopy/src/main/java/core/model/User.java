package core.model;

import java.io.Serializable;

/**
 * Created by makisucruse on 2017/5/9.
 */
public class User implements Serializable{
    private String userId;
    private String screenName;
    private String description;
    private String timeZone;
    private String image;
    private Integer followerCount;
    private Integer userStatusCount;
    private String location;
    private Integer friendCount;
    private Integer favoriteCount;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getFollowerCount() {
        return followerCount;
    }

    public void setFollowerCount(Integer followerCount) {
        this.followerCount = followerCount;
    }

    public Integer getUserStatusCount() {
        return userStatusCount;
    }

    public void setUserStatusCount(Integer userStatusCount) {
        this.userStatusCount = userStatusCount;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getFriendCount() {
        return friendCount;
    }

    public void setFriendCount(Integer friendCount) {
        this.friendCount = friendCount;
    }

    public Integer getFavoriteCount() {
        return favoriteCount;
    }

    public void setFavoriteCount(Integer favoriteCount) {
        this.favoriteCount = favoriteCount;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", screenName='" + screenName + '\'' +
                ", description='" + description + '\'' +
                ", timeZone='" + timeZone + '\'' +
                ", image='" + image + '\'' +
                ", followerCount=" + followerCount +
                ", userStatusCount=" + userStatusCount +
                ", location='" + location + '\'' +
                ", friendCount=" + friendCount +
                ", favoriteCount=" + favoriteCount +
                '}';
    }
}
