package core.DAO.Impl;

import core.DAO.IPersonTrend;
import core.model.Person;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by makisucruse on 2017/5/22.
 */
public class PersonTrendImpl extends JdbcDaoSupport implements IPersonTrend {

    @Override
    public void addPerson(Person person) {
        List<Person> persons = findAllPerson();
        for (Person p : persons) {
            if (person.getPersonName().equals(p.getPersonName())) {
                //已经存在这个人物了,连接趋势
                //趋势在库中是否已经存在了
                String[] trends = p.getTrendName().split("  ");
                for (String s : trends) {
                    if (s.equals(person.getTrendName())) return;
                }
                //不存在,更新
                String trendName = p.getTrendName() + "  " + person.getTrendName();
                String sql = "update person set trend_name=\"" + trendName + "\" where person_name=\"" + person.getPersonName() + "\"";
                this.getJdbcTemplate().update(sql);
                return;
            }
        }
        String sql = "insert into person(person_name,trend_name,follower_count) values(?,?,?)";
        this.getJdbcTemplate().update(sql, person.getPersonName(), person.getTrendName(), person.getFollowerCount());
    }

    @Override
    public List<Person> findAllPerson() {
        String sql = "select * from person";
        return this.getJdbcTemplate().query(sql, new PersonRowMapper());
    }

    public String findPerson(String personName) {
        String sql = "select trend_name from person where binary person_name=?";
        return this.getJdbcTemplate().queryForObject(sql, String.class, personName);
    }
}

class PersonRowMapper implements RowMapper<Person> {

    @Override
    public Person mapRow(ResultSet resultSet, int i) throws SQLException {
        Person p = new Person(resultSet.getString("person_name"), resultSet.getString("trend_name"), resultSet.getInt("follower_count"));
        return p;
    }
}
