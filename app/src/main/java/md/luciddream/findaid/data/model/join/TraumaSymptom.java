package md.luciddream.findaid.data.model.join;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.support.annotation.NonNull;
import md.luciddream.findaid.data.model.Symptom;
import md.luciddream.findaid.data.model.Trauma;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "Trauma_Symptom",
        primaryKeys = {
                "sm_id",
                "t_id"
        },
        foreignKeys = {
                @ForeignKey(entity = Symptom.class,
                        parentColumns = "sm_id",
                        childColumns = "sm_id",
                        onUpdate = CASCADE,
                        onDelete = CASCADE),
                @ForeignKey(entity = Trauma.class,
                        parentColumns = "t_id",
                        childColumns = "t_id",
                        onUpdate = CASCADE,
                        onDelete = CASCADE)
        })
public class TraumaSymptom {
    @NonNull
    private Integer sm_id;
    @NonNull
    private Integer t_id;

    public TraumaSymptom() {
    }

    @Ignore
    public TraumaSymptom(@NonNull Integer sm_id, @NonNull Integer t_id) {
        this.sm_id = sm_id;
        this.t_id = t_id;
    }

    @NonNull
    public Integer getSm_id() {
        return sm_id;
    }

    public void setSm_id(@NonNull Integer sm_id) {
        this.sm_id = sm_id;
    }

    @NonNull
    public Integer getT_id() {
        return t_id;
    }

    public void setT_id(@NonNull Integer t_id) {
        this.t_id = t_id;
    }
}
