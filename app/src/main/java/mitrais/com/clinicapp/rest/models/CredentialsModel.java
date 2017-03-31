package mitrais.com.clinicapp.rest.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by mtmac20 on 3/6/17.
 */

public class CredentialsModel implements Serializable {
    @SerializedName("token")
    public String Token;
    @SerializedName("name")
    public String Name;
    @SerializedName("role")
    public String Role;
    @SerializedName("id")
    public int Id;

    public enum eRole {
        DOCTOR,
        PATIENT
    };

    public eRole getRole() {
        if (Role.equalsIgnoreCase("doctor")) {
            return eRole.DOCTOR;
        }
        return eRole.PATIENT;
    }

    public String getRoleTitle() {
        if (Role.equalsIgnoreCase("doctor")) {
            return "";
        }
        return "Dr.";
    }
}
