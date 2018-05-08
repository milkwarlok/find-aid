package md.luciddream.findaid.data.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Step implements NamedEntity{
    @PrimaryKey(autoGenerate = true)
    private Integer sp_id;
    @ColumnInfo(name = "name")
    private String name;

    public Step() {
    }
    @Ignore
    public Step(Integer sp_id, String name) {
        this.sp_id = sp_id;
        this.name = name;
    }

    public Integer getSp_id() {
        return sp_id;
    }

    public void setSp_id(Integer sp_id) {
        this.sp_id = sp_id;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
