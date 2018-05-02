package md.luciddream.findaid.data.model.join;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.support.annotation.NonNull;
import md.luciddream.findaid.data.model.Organ;
import md.luciddream.findaid.data.model.Trauma;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "Trauma_Organ",
        primaryKeys = {
                "o_id",
                "t_od"
        },
        foreignKeys = {
                @ForeignKey(entity = Organ.class,
                parentColumns = "o_id",
                childColumns = "o_id",
                onUpdate = CASCADE,
                onDelete = CASCADE),
                @ForeignKey(entity = Trauma.class,
                parentColumns = "t_id",
                childColumns = "t_id",
                onUpdate = CASCADE,
                onDelete = CASCADE)
        })
public class TraumaOrgan {
    @NonNull
    private Integer o_id;
    @NonNull
    private Integer t_id;

    public TraumaOrgan() {
    }

    @Ignore
    public TraumaOrgan(@NonNull Integer o_id, @NonNull Integer t_id) {
        this.o_id = o_id;
        this.t_id = t_id;
    }

    @NonNull
    public Integer getO_id() {
        return o_id;
    }

    public void setO_id(@NonNull Integer o_id) {
        this.o_id = o_id;
    }

    @NonNull
    public Integer getT_id() {
        return t_id;
    }

    public void setT_id(@NonNull Integer t_id) {
        this.t_id = t_id;
    }
}
