package mitrais.com.clinicapp.rest.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by mtmac20 on 3/27/17.
 */

public class StatusResponseModel implements Serializable {
    @SerializedName("status")
    public boolean Status;
}
