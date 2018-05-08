package md.luciddream.findaid.data.helper;

import md.luciddream.findaid.data.model.Location;

import java.util.List;
//todo: write helper classes for join-daos
public interface Helper<E> {
    List<E> findAll();

    List<E> findByIds(int[] id);

    List<E> findByName(String name);

    void insert(E... items);

    void delete(E item);
}
