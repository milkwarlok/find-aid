package md.luciddream.findaid.data.model.join;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.support.annotation.NonNull;
import md.luciddream.findaid.data.model.Season;
import md.luciddream.findaid.data.model.Trauma;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "Trauma_Season",
        primaryKeys = {
                "sn_id",
                "t_id"
        },
        foreignKeys = {
        @ForeignKey(entity = Season.class,
                parentColumns = "sn_id",
                childColumns = "sn_id",
                onUpdate = CASCADE,
                onDelete = CASCADE),
        @ForeignKey(entity = Trauma.class,
                parentColumns = "t_id",
                childColumns = "t_id",
                onUpdate = CASCADE,
                onDelete = CASCADE)
})
public class TraumaSeason {
    @NonNull
    private Integer sn_id;
    @NonNull
    private Integer t_id;

    public TraumaSeason() {
    }

    @Ignore
    public TraumaSeason(@NonNull Integer sn_id, @NonNull Integer t_id) {
        this.sn_id = sn_id;
        this.t_id = t_id;
    }

    @NonNull
    public Integer getSn_id() {
        return sn_id;
    }

    public void setSn_id(@NonNull Integer sn_id) {
        this.sn_id = sn_id;
    }

    @NonNull
    public Integer getT_id() {
        return t_id;
    }

    public void setT_id(@NonNull Integer t_id) {
        this.t_id = t_id;
    }
}
