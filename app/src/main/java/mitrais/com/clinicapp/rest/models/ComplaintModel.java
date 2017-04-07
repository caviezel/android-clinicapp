package mitrais.com.clinicapp.rest.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by mtmac20 on 4/6/17.
 */

public class ComplaintModel implements Serializable {
    @SerializedName("id")
    public int Id;
    @SerializedName("patient_id")
    public int PatientId;
    @SerializedName("description")
    public String Description;
}