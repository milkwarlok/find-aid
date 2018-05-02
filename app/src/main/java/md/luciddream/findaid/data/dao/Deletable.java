package md.luciddream.findaid.data.dao;

import android.arch.persistence.room.Delete;

public interface Deletable<T> {
    @Delete
    void delete(T item);
}
