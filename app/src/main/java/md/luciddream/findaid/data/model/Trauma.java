package md.luciddream.findaid.data.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.IntRange;

@Entity
public class Trauma implements NamedEntity{
    @PrimaryKey(autoGenerate = true)
    private Integer t_id;
    @ColumnInfo(name = "name")
    private String name;

    //fixme: Thought @IntRange, still can create Trauma-object with relevance > 10.
    @IntRange(from = 0, to = 10)
    @ColumnInfo(name = "relevance")
    private Integer relevance;

    @ColumnInfo(name = "user_added")
    private Boolean userAdded;

    public Trauma() { }

    @Ignore
    public Trauma(Integer t_id, String name, Integer relevance) {
        this.t_id = t_id;
        this.name = name;
        this.relevance = relevance;
        userAdded = true;
    }

    @Ignore
    public Trauma(Integer t_id, String name, Integer relevance, Boolean userAdded) {
        this.t_id = t_id;
        this.name = name;
        this.relevance = relevance;
        this.userAdded = userAdded;
    }

    public Integer getT_id() {
        return t_id;
    }

    public void setT_id(Integer t_id) {
        this.t_id = t_id;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getRelevance() {
        return relevance;
    }

    public void setRelevance(Integer relevance) {
        this.relevance = relevance;
    }

    public Boolean getUserAdded() {
        return userAdded;
    }

    public void setUserAdded(Boolean userAdded) {
        this.userAdded = userAdded;
    }
}
