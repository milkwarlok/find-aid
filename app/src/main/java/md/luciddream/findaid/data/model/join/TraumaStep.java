package md.luciddream.findaid.data.model.join;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.support.annotation.NonNull;
import md.luciddream.findaid.data.model.Step;
import md.luciddream.findaid.data.model.Trauma;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "Trauma_Step",
        primaryKeys = {
                "sp_id",
                "t_id"
        },
        foreignKeys = {
                @ForeignKey(entity = Step.class,
                        parentColumns = "sp_id",
                        childColumns = "sp_id",
                        onUpdate = CASCADE,
                        onDelete = CASCADE),
                @ForeignKey(entity = Trauma.class,
                        parentColumns = "t_id",
                        childColumns = "t_id",
                        onUpdate = CASCADE,
                        onDelete = CASCADE)
        })
public class TraumaStep {
    @NonNull
    private Integer sp_id;
    @NonNull
    private Integer t_id;
    @ColumnInfo(name = "ord_num")
    private Integer ord_num;


    public TraumaStep() {
    }

    @Ignore
    public TraumaStep(@NonNull Integer sp_id, @NonNull Integer t_id) {
        this.sp_id = sp_id;
        this.t_id = t_id;
        this.ord_num = 0;
    }

    @Ignore
    public TraumaStep(@NonNull Integer sp_id, @NonNull Integer t_id, Integer ord_num) {
        this.sp_id = sp_id;
        this.t_id = t_id;
        this.ord_num = ord_num;
    }

    @NonNull
    public Integer getSp_id() {
        return sp_id;
    }

    public void setSp_id(@NonNull Integer sp_id) {
        this.sp_id = sp_id;
    }

    @NonNull
    public Integer getT_id() {
        return t_id;
    }

    public void setT_id(@NonNull Integer t_id) {
        this.t_id = t_id;
    }

    public Integer getOrd_num() {
        return ord_num;
    }

    public void setOrd_num(Integer ord_num) {
        this.ord_num = ord_num;
    }
}
