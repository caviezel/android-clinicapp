package mitrais.com.clinicapp.rest.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by mtmac20 on 3/3/17.
 */

public class LoginModel implements Serializable {
    @SerializedName("status")
    public boolean Status;
    @SerializedName("data")
    public CredentialsModel Credentials;
}
