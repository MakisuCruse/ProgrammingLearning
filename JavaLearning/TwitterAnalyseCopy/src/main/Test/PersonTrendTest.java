import core.DAO.Impl.PersonTrendImpl;
import core.model.Person;
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
public class PersonTrendTest {
    @Autowired
    private PersonTrendImpl personTrend;

    @Test
    public void testIPersonTrendShouldNotBeNull() {
        Assert.assertNotNull(personTrend);
    }

    @Test
    public void testFindAllPerson() {
        List<Person> lst = personTrend.findAllPerson();
        System.out.println(lst);
    }

    @Test
    public void testAddPersonThroughUpdate() {
        Person p = new Person();
        p.setPersonName("trump");
        p.setTrendName("#new Trump1");
        personTrend.addPerson(p);
    }

    @Test
    public void testAddPersonThroughInsert() {
        Person p = new Person();
        p.setPersonName("obama");
        p.setTrendName("happy");
        personTrend.addPerson(p);
    }

    @Test
    public void testFindPerson() {
        String personName = "@realDonaldTrump";
        System.out.println(personTrend.findPerson(personName));
    }

}
