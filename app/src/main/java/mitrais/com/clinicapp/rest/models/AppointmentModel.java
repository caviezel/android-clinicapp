package mitrais.com.clinicapp.rest.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by mtmac20 on 3/20/17.
 */

public class AppointmentModel implements Serializable {
    @SerializedName("status")
    public String Status;
    @SerializedName("id")
    public int Id;
    @SerializedName("doctor_id")
    public int DoctorId;
    @SerializedName("patient_id")
    public int PatientId;
    @SerializedName("doctor")
    public DoctorModel Doctor;
    @SerializedName("patient")
    public PatientModel Patient;
    @SerializedName("date")
    public String Date;
}
