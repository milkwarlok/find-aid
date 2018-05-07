package md.luciddream.findaid.data.helper;

import md.luciddream.findaid.data.model.Location;

import java.util.List;

public interface Helper<T> {
    List<T> findAll();

    List<T> findByIds(int[] id);

    List<T> findByName(String name);

    void insert(T... items);

    void delete(T item);
}
