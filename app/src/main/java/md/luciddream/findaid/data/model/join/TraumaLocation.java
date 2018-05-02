package md.luciddream.findaid.data.model.join;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
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
    private int l_id;
    private int t_id;

    public TraumaLocation() {
    }

    public TraumaLocation(int l_id, int t_id) {
        this.l_id = l_id;
        this.t_id = t_id;
    }

    public int getL_id() {
        return l_id;
    }

    public void setL_id(int l_id) {
        this.l_id = l_id;
    }

    public int getT_id() {
        return t_id;
    }

    public void setT_id(int t_id) {
        this.t_id = t_id;
    }
}
