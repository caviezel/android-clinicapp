package mitrais.com.clinicapp.rest.models;

/**
 * Created by mtmac20 on 3/3/17.
 */

public class CoreModel {
    private static CoreModel m_instance;

    public LoginModel m_loginModel;
    public AppointmentListModel m_appointmentListModel;

    public static CoreModel getInstance() {
        if (null == m_instance) {
            m_instance = new CoreModel();
        }
        return  m_instance;
    }

    public  void  setLoginModel(LoginModel loginModel) {
        m_loginModel = loginModel;
    }

    public LoginModel getLoginModel() {
        return m_loginModel;
    }

    public void setAppointmentListModel(AppointmentListModel listModel) {
        m_appointmentListModel = listModel;
    }

    public AppointmentListModel getAppointmentListModel() {
        return m_appointmentListModel;
    }
}
