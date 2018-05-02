package md.luciddream.findaid.data.dao;

import android.arch.persistence.room.Insert;

public interface Insertable<T> {
    @Insert
    void insert(T... items);
}
