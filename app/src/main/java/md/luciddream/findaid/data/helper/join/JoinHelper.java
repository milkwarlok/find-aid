package md.luciddream.findaid.data.helper.join;

import md.luciddream.findaid.data.dao.Deletable;
import md.luciddream.findaid.data.dao.Insertable;

import java.util.List;

public interface JoinHelper <FirstEntity, SecondEntity, JoinEntity> extends Insertable<JoinEntity>, Deletable<JoinEntity> {
    List<FirstEntity> getFirstBySecondId(int secondId);

    List<FirstEntity> getFirstBySecondName(String secondName);

    List<SecondEntity> getSecondByFirstId(int firstId);

    List<SecondEntity> getSecondByFirstName(String firstName);

    List<JoinEntity> findAll();
}