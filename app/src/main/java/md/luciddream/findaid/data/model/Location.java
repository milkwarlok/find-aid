package md.luciddream.findaid.data.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Location {
    @PrimaryKey(autoGenerate = true)
    private Integer l_id;
    @ColumnInfo(name = "name")
    private String name;

    public Location() {
    }

    public Location(Integer l_id, String name) {
        this.l_id = l_id;
        this.name = name;
    }

    public Integer getL_id() {
        return l_id;
    }

    public void setL_id(Integer l_id) {
        this.l_id = l_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
