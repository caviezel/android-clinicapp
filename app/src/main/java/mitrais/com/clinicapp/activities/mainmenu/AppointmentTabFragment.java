package mitrais.com.clinicapp.activities.mainmenu;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import mitrais.com.clinicapp.R;
import mitrais.com.clinicapp.rest.models.AppointmentListModel;
import mitrais.com.clinicapp.rest.models.AppointmentModel;
import mitrais.com.clinicapp.rest.models.CoreModel;
import mitrais.com.clinicapp.rest.models.CredentialsModel;
import mitrais.com.clinicapp.rest.models.PersonModel;
import mitrais.com.clinicapp.rest.services.WebServices;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AppointmentTabFragment extends Fragment implements IAppointmentCreateListener, IAppointmentConfirmListener {
    private ListView listView;
    private AppointmentListAdapter adapter;
    private ArrayList<AppointmentItem> arrayList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_appointment_tab, container, false);

        //the appointment list
        listView = (ListView) view.findViewById(R.id.list_appointment);
        arrayList = new ArrayList<AppointmentItem>();
        adapter = new AppointmentListAdapter(getContext(), R.layout.appointment_list_item, arrayList);
        listView.setAdapter(adapter);

        Button signInBtn = (Button) view.findViewById(R.id.btn_create_appointment);
        signInBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        openCreateAppointment();
                    }
                }
        );

        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (isVisibleToUser) {
            doGetAppointmentList();
        }
    }

    private void openCreateAppointment() {
        AppointmentCreateFragment createFragment = new AppointmentCreateFragment();
        createFragment.setListener(this);
        createFragment.show(getChildFragmentManager(), null);
    }

    private void doGetAppointmentList() {
        WebServices services = new WebServices();
        final CredentialsModel credentials = CoreModel.getInstance().getLoginModel().Credentials;
        final CredentialsModel.eRole role = credentials.getRole();
        Call<AppointmentListModel> call;
        call = role == CredentialsModel.eRole.DOCTOR ? services.getServices().doGetAppointmentListDoctor(credentials.Id) :
                services.getServices().doGetAppointmentListPatient(credentials.Id);
        call.enqueue(new Callback<AppointmentListModel>() {
                         @Override
                         public void onResponse(Call<AppointmentListModel> call, Response<AppointmentListModel> response) {
                             if (response.body().Status) {
                                 CoreModel.getInstance().setAppointmentListModel(response.body());
                                 List<AppointmentModel> appts = CoreModel.getInstance().getAppointmentListModel().Appointment;
                                 arrayList.clear();

                                 //need to manually reverse the list
                                 for (int i = appts.size() - 1; i >= 0;--i) {
                                     //shows doctor's name if the current role is a patient and vice versa
                                     String title = "with " + credentials.getRoleTitle();
                                     PersonModel person = role == CredentialsModel.eRole.DOCTOR ? appts.get(i).Patient.Person : appts.get(i).Doctor.Person;

                                     Date originDate = new Date();
                                     try {
                                         originDate = new SimpleDateFormat("yyyy-MM-dd hh:mm").parse(appts.get(i).Date);
                                     } catch (ParseException e) {
                                         e.printStackTrace();
                                     }

                                     String formattedDate = new SimpleDateFormat("EEE, MMM d, ''yy").format(originDate);
                                     String formattedHour = new SimpleDateFormat("h:mm a").format(originDate);

                                     title += person.Name;
                                     arrayList.add(new AppointmentItem(
                                             title
                                             , formattedDate
                                             , formattedHour
                                             , appts.get(i).Status
                                     ));
                                 }
                                 adapter.notifyDataSetChanged();
                             }
                         }

                         @Override
                         public void onFailure(Call<AppointmentListModel> call, Throwable t) {
                         }
                     }
        );
    }

    public void onConfirmData(ConfirmationData data) {
        AppointmentConfirmationFragment conf = new AppointmentConfirmationFragment();
        conf.setConfirmationTexts(data);
        conf.setListener(this);
        conf.show(getChildFragmentManager(), null);
    }

    public void onCloseConfirm() {
        doGetAppointmentList();
    }
}
