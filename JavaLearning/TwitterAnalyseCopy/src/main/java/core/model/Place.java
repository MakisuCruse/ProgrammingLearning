package core.model;

/**
 * Created by makisucruse on 2017/5/22.
 */
public class Place {
    private String placeName;
    private String trendName;

    public Place() {
    }

    public Place(String placeName, String trendName) {
        this.placeName = placeName;
        this.trendName = trendName;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getTrendName() {
        return trendName;
    }

    public void setTrendName(String trendName) {
        this.trendName = trendName;
    }

    @Override
    public String toString() {
        return "Place{" +
                "placeName='" + placeName + '\'' +
                ", trendName='" + trendName + '\'' +
                '}';
    }
}
