package mitrais.com.clinicapp.rest.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by mtmac20 on 4/3/17.
 */

public class MedicineModel implements Serializable {
    @SerializedName("id")
    public int Id;
    @SerializedName("name")
    public String Name;
    @SerializedName("quantity")
    public int Quantity;
}
