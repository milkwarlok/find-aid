package md.luciddream.findaid.data.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Season implements NamedEntity{
    @PrimaryKey(autoGenerate = true)
    private Integer sn_id;
    @ColumnInfo(name = "name")
    private String name;

    public Season() {
    }

    @Ignore
    public Season(Integer sn_id, String name) {
        this.sn_id = sn_id;
        this.name = name;
    }

    public Integer getSn_id() {
        return sn_id;
    }

    public void setSn_id(Integer sn_id) {
        this.sn_id = sn_id;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
