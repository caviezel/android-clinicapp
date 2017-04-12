package mitrais.com.clinicapp.rest.services;

import mitrais.com.clinicapp.rest.models.AppointmentListModel;
import mitrais.com.clinicapp.rest.models.ComplaintListModel;
import mitrais.com.clinicapp.rest.models.DoctorListModel;
import mitrais.com.clinicapp.rest.models.LoginModel;
import mitrais.com.clinicapp.rest.models.MedicineListModel;
import mitrais.com.clinicapp.rest.models.StatusResponseModel;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by mtmac20 on 3/3/17.
 */

public interface ClinicApiServices {
    @FormUrlEncoded
    @POST("user-login/login")
    Call<LoginModel> doLogin(
            @Field("username") String username,
            @Field("password") String password
    );

    @GET("appointment/find-by-doctor")
    Call<AppointmentListModel> doGetAppointmentListDoctor(
            @Query("id") int id
    );

    @GET("appointment/find-by-patient")
    Call<AppointmentListModel> doGetAppointmentListPatient(
            @Query("id") int id
    );

    @GET("doctor")
    Call<DoctorListModel> doGetDoctors(
    );

    @FormUrlEncoded
    @POST("appointment/create")
    Call<StatusResponseModel> doCreateAppointment(
            @Field("date") String date,
            @Field("patient_id") int patientId,
            @Field("doctor_id") int doctorId
    );

    @GET("medicine")
    Call<MedicineListModel> doGetMedicines();

    @GET("complaint-header/find-by-patient")
    Call<ComplaintListModel> doGetComplaints(
            @Query("id") int id
    );
}
