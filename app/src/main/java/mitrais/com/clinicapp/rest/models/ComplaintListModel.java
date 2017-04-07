package mitrais.com.clinicapp.rest.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by mtmac20 on 4/6/17.
 */

public class ComplaintListModel implements Serializable {
    @SerializedName("status")
    public boolean Status;
    @SerializedName("data")
    public List<ComplaintModel> Complaints;
}