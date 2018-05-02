package md.luciddream.findaid.data.model.join;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import md.luciddream.findaid.data.model.Location;
import md.luciddream.findaid.data.model.Trauma;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "Trauma_Location",
        primaryKeys = {
                "l_id",
                "t_id"
        },
        foreignKeys = {
                @ForeignKey(entity = Location.class,
                        parentColumns = "l_id",
                        childColumns = "l_id",
                        onUpdate = CASCADE,
                        onDelete = CASCADE),

                @ForeignKey(entity = Trauma.class,
                        parentColumns = "t_id",
                        childColumns = "t_id",
                        onUpdate = CASCADE,
                        onDelete = CASCADE)
        })
public class TraumaLocation {
    @NonNull
    private Integer l_id;
    @NonNull
    private Integer t_id;

    public TraumaLocation() {
    }

    @Ignore
    public TraumaLocation(Integer l_id, Integer t_id) {
        this.l_id = l_id;
        this.t_id = t_id;
    }

    public Integer getL_id() {
        return l_id;
    }

    public void setL_id(Integer l_id) {
        this.l_id = l_id;
    }

    public Integer getT_id() {
        return t_id;
    }

    public void setT_id(Integer t_id) {
        this.t_id = t_id;
    }
}
