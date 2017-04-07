package mitrais.com.clinicapp.rest.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by mtmac20 on 4/3/17.
 */

public class MedicineListModel implements Serializable {
    @SerializedName("data")
    public List<MedicineModel> Medicines;
}
