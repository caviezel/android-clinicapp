package mitrais.com.clinicapp.rest.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by mtmac20 on 3/21/17.
 */

public class PersonModel implements Serializable {
    @SerializedName("id")
    public int Id;
    @SerializedName("name")
    public String Name;
    @SerializedName("address")
    public String Address;
    @SerializedName("email")
    public String Email;
    @SerializedName("gender")
    public String Gender;
}
