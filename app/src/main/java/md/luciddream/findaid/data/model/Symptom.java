package md.luciddream.findaid.data.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Symptom implements NamedEntity{
    @PrimaryKey(autoGenerate = true)
    private Integer sm_id;
    @ColumnInfo(name = "name")
    private String name;

    public Symptom() {
    }

    @Ignore
    public Symptom(Integer sm_id, String name) {
        this.sm_id = sm_id;
        this.name = name;
    }

    public Integer getSm_id() {
        return sm_id;
    }

    public void setSm_id(Integer sm_id) {
        this.sm_id = sm_id;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
