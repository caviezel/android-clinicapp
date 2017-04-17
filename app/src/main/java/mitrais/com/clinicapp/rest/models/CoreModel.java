package mitrais.com.clinicapp.rest.models;

/**
 * Created by mtmac20 on 3/3/17.
 */

public class CoreModel {
    private static CoreModel instance;

    public LoginModel loginModel;
    public AppointmentListModel appointmentListModel;

    public static CoreModel getInstance() {
        if (null == instance) {
            instance = new CoreModel();
        }
        return instance;
    }

    public  void  setLoginModel(LoginModel loginModel) {
        this.loginModel = loginModel;
    }

    public LoginModel getLoginModel() {
        return loginModel;
    }

    public void setAppointmentListModel(AppointmentListModel listModel) {
        appointmentListModel = listModel;
    }

    public AppointmentListModel getAppointmentListModel() {
        return appointmentListModel;
    }
}
