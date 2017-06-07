import core.DAO.IPlaceTrend;
import core.model.Place;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by makisucruse on 2017/5/22.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:applicationContext.xml")
public class PlaceTrendTest {
    @Autowired
    private IPlaceTrend placeTrend;

    @Test
    public void placeShouldNotBeNull() {
        Assert.assertNotNull(placeTrend);
    }

    @Test
    public void findAllPlace() {
        List<Place> placeTrendAllPlace = placeTrend.findAllPlace();
        placeTrendAllPlace.forEach(System.out::println);
    }

    @Test
    public void addPlace() {
        placeTrend.addPlace(new Place("new york", "#ny"));
    }

    @Test
    public void addRepatePlace() {
        placeTrend.addPlace(new Place("new york", "new york"));
    }
}
