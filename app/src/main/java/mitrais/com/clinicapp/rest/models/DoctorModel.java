package mitrais.com.clinicapp.rest.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by mtmac20 on 3/21/17.
 */

public class DoctorModel implements Serializable {
    @SerializedName("id")
    public String Id;
    @SerializedName("reg_number")
    public String RegNo;
    @SerializedName("person")
    public PersonModel Person;
}
