package core.DAO;

import core.model.Place;

import java.util.List;

/**
 * Created by makisucruse on 2017/5/22.
 */
public interface IPlaceTrend {
    void addPlace(Place place);

    List<Place> findAllPlace();
}
