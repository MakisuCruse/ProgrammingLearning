package spittr.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import spittr.model.Spitter;

import java.util.List;

/**
 * Created by makisucruse on 16/9/29.
 */
@Repository
public interface SpitterMapper {
    @Select("select * from Spitter where email=#{email}")
    Spitter findSpitterByEmail(String email);

    @Select("select * from Spitter")
    List<Spitter> findAllSpitter();

    @Insert("insert into Spitter (email,username,password) values(#{email},#{username},#{password})")
    Integer createSpitter(Spitter spitter);

    @Delete("delete from Spitter where email=#{email}")
    Integer deleteSpitter(String email);
}
