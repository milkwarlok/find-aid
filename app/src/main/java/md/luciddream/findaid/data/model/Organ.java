package md.luciddream.findaid.data.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Organ {
    @PrimaryKey(autoGenerate = true)
    private Integer o_id;
    @ColumnInfo(name = "name")
    private String name;

    public Organ() {
    }

    @Ignore
    public Organ(Integer o_id, String name) {
        this.o_id = o_id;
        this.name = name;
    }

    public Integer getO_id() {
        return o_id;
    }

    public void setO_id(Integer o_id) {
        this.o_id = o_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
