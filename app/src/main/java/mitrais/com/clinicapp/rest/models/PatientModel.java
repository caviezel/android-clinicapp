package mitrais.com.clinicapp.rest.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by mtmac20 on 3/21/17.
 */

public class PatientModel implements Serializable {
    @SerializedName("id")
    public String Id;
    @SerializedName("patient_number")
    public String PatientNo;
    @SerializedName("person")
    public PersonModel Person;
}
