package core.DAO;

import core.model.Person;

import java.util.List;

/**
 * Created by makisucruse on 2017/5/22.
 */
public interface IPersonTrend {
    void addPerson(Person person);

    List<Person> findAllPerson();
}
