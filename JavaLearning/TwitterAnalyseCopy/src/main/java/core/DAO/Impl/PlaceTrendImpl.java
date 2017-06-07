package core.DAO.Impl;

import core.DAO.IPlaceTrend;
import core.model.Place;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by makisucruse on 2017/5/22.
 */
public class PlaceTrendImpl extends JdbcDaoSupport implements IPlaceTrend {

    @Override
    public void addPlace(Place place) {
        List<Place> places = findAllPlace();
        for (Place p : places) {
            if (place.getPlaceName().equals(p.getPlaceName())) {
                //已经存在这个人物了,连接趋势
                //趋势在库中是否已经存在了
                String[] trends = p.getTrendName().split("  ");
                for (String s : trends) {
                    if (s.equals(place.getTrendName())) return;
                }
                //不存在,更新
                String trendName = p.getTrendName() + "  " + place.getTrendName();
                String sql = "update place set trend_name=\"" + trendName + "\" where place_name=\"" + place.getPlaceName() + "\"";
                this.getJdbcTemplate().update(sql);
                return;
            }
        }
        String sql = "insert into place(place_name,trend_name) values(?,?)";
        this.getJdbcTemplate().update(sql, place.getPlaceName(), place.getTrendName());
    }

    @Override
    public List<Place> findAllPlace() {
        String sql = "select * from place";
        return this.getJdbcTemplate().query(sql, new PlaceRowMapper());
    }
}

class PlaceRowMapper implements RowMapper<Place> {
    @Override
    public Place mapRow(ResultSet resultSet, int i) throws SQLException {
        Place place = new Place(resultSet.getString("place_name"), resultSet.getString("trend_name"));
        return place;
    }
}
