package md.luciddream.findaid.data.dao;

import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;

public interface Insertable<T> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(T... items);
}
