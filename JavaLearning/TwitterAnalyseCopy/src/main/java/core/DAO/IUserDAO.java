package core.DAO;

import core.model.User;

import java.util.List;

/**
 * Created by makisucruse on 2017/5/9.
 */
public interface IUserDAO {
    void addUser(User user);

    List<User> findUsers();

    List<User> findUser(String userId);
}
