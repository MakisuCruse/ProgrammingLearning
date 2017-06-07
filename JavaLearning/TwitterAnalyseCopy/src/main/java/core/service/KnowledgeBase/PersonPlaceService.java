package core.service.KnowledgeBase;

import core.DAO.Impl.PersonTrendImpl;
import core.model.Person;
import util.AccessDAO;

import java.util.List;

/**
 * Created by makisucruse on 2017/5/22.
 */
public class PersonPlaceService {
    private static PersonTrendImpl personDao = (PersonTrendImpl) AccessDAO.getPersonDao();

    public static List<Person> getPersonTrend() {
        return personDao.findAllPerson();
    }

    public static String getOneTrend(String personName) {
        return personDao.findPerson(personName);
    }
}
