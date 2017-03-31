package mitrais.com.clinicapp.rest.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by mtmac20 on 3/24/17.
 */

public class DoctorListModel implements Serializable {
    @SerializedName("status")
    public boolean Status;
    @SerializedName("data")
    public List<DoctorModel> Doctors;
}
