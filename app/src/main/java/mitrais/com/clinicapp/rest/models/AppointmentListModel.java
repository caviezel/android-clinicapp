package mitrais.com.clinicapp.rest.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by mtmac20 on 3/20/17.
 */

public class AppointmentListModel implements Serializable {
    @SerializedName("status")
    public boolean Status;
    @SerializedName("data")
    public List<AppointmentModel> Appointment;
}
