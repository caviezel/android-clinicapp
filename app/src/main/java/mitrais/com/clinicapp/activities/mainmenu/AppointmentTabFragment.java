package mitrais.com.clinicapp.activities.mainmenu;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

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
import mitrais.com.common.ui.listview.CustomListView;
import mitrais.com.common.ui.listview.ICustomListItemModel;
import mitrais.com.common.ui.listview.ICustomListListener;
import mitrais.com.common.ui.listview.OnLoadViewData;
import mitrais.com.common.ui.popup.IPopupFragment;
import mitrais.com.common.ui.popup.IPopupListener;
import mitrais.com.common.ui.popup.PopupBuilder;
import mitrais.com.common.ui.popup.PopupFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class AppointmentListItem {
    String EditedPersonName;
    String OriginPersonName;
    String Date;
    String Hour;
    String Status;
    public AppointmentListItem(String editedPersonName, String originPersonName, String date, String hour, String status) {
        EditedPersonName = editedPersonName;
        OriginPersonName = originPersonName;
        Date = date;
        Hour = hour;
        Status = status;
    }
}

public class AppointmentTabFragment extends Fragment implements IAppointmentCreateListener, IAppointmentConfirmListener, ICustomListListener, IPopupListener<AppointmentListItem> {
    private CustomListView<AppointmentListItem> listView;

    //region IPopupListener
    public void onBuildCommonInfo(AppointmentListItem item, View view, IPopupFragment fragment) {
        PopupBuilder infoBuilder = new PopupBuilder(
                view
                , fragment
                , R.drawable.borat_icon
                , item.OriginPersonName
                , item.Date + " | " + item.Hour
        );
    }
    public void onCloseCommonInfo() {

    }
    //endregion

    //region ICustomListListener
    public <T> View buildListItemView(View view, T item) {
        AppointmentListItem appointmentListItem = (AppointmentListItem) item;
        if (null != appointmentListItem) {
            TextView tPatient = (TextView) view.findViewById(R.id.txt_person_name);
            if (null != tPatient) {
                tPatient.setText(appointmentListItem.EditedPersonName);
            }
            TextView tDate = (TextView) view.findViewById(R.id.txt_date);
            if (null != tDate) {
                tDate.setText(appointmentListItem.Date);
            }
            TextView tHour = (TextView) view.findViewById(R.id.txt_hour);
            if (null != tHour) {
                tHour.setText(appointmentListItem.Hour);
            }
            TextView tStatus = (TextView) view.findViewById(R.id.txt_status);
            if (null != tStatus) {
                tStatus.setText(appointmentListItem.Status);
            }
        }
        return view;
    }


    public <T> void onItemSelected(T item) {
        AppointmentListItem appointmentListItem = (AppointmentListItem) item;
        PopupFragment infoFragment = new PopupFragment();
        infoFragment.setCommonInfoData(R.layout.popup_common_info, appointmentListItem)
                .setCloseButton("OK")
                .setListener(this);
        infoFragment.show(getChildFragmentManager(), null);
    }
    //endregion

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_appointment_tab, container, false);

        List<OnLoadViewData> onLoadViewDatas = new ArrayList<>();
        onLoadViewDatas.add(new OnLoadViewData(R.id.list_common, false));
        onLoadViewDatas.add(new OnLoadViewData(R.id.loading_list, true));

        listView = new CustomListView(view
                , getContext()
                , R.id.list_common
                , R.layout.complaint_list_item
                , this
                , onLoadViewDatas
        );

        Button createApptBtn = (Button) view.findViewById(R.id.btn_create_appointment);
        createApptBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        openCreateAppointment();
                    }
                }
        );

        CredentialsModel credentials = CoreModel.getInstance().getLoginModel().Credentials;
        createApptBtn.setVisibility(credentials.getRole() == CredentialsModel.eRole.DOCTOR ? View.GONE : View.VISIBLE);

        doGetAppointmentList();

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
        if (null == listView) {
            return;
        }

        WebServices services = new WebServices();
        final CredentialsModel credentials = CoreModel.getInstance().getLoginModel().Credentials;
        final CredentialsModel.eRole role = credentials.getRole();
        Call<AppointmentListModel> call;
        call = role == CredentialsModel.eRole.DOCTOR ? services.getServices().doGetAppointmentListDoctor(credentials.Id) :
                services.getServices().doGetAppointmentListPatient(credentials.Id);

        listView.beginLoadListView();

        call.enqueue(new Callback<AppointmentListModel>() {
                         @Override
                         public void onResponse(Call<AppointmentListModel> call, Response<AppointmentListModel> response) {
                             if (response.body().Status) {
                                 CoreModel.getInstance().setAppointmentListModel(response.body());
                                 List<AppointmentModel> appts = CoreModel.getInstance().getAppointmentListModel().Appointment;

                                 listView.clearListView();

                                 //need to manually reverse the list
                                 for (int i = appts.size() - 1; i >= 0;--i) {
                                     //shows doctor's name if the current role is a patient and vice versa
                                     final AppointmentModel appointmentModel = appts.get(i);
                                     final PersonModel person = role == CredentialsModel.eRole.DOCTOR ? appointmentModel.Patient.Person : appointmentModel.Doctor.Person;

                                     Date originDate = new Date();
                                     try {
                                         originDate = new SimpleDateFormat("yyyy-MM-dd hh:mm").parse(appts.get(i).Date);
                                     } catch (ParseException e) {
                                         e.printStackTrace();
                                     }

                                     final String formattedDate = new SimpleDateFormat("EEE, MMM d, ''yy").format(originDate);
                                     final String formattedHour = new SimpleDateFormat("h:mm a").format(originDate);

                                     final String title = "with " + credentials.getRoleTitle() + person.Name;

                                     listView.addListItem(new ICustomListItemModel<AppointmentListItem>() {
                                         @Override
                                         public AppointmentListItem getListItemModel() {
                                             return new AppointmentListItem(
                                                     title
                                                     , person.Name
                                                     , formattedDate
                                                     , formattedHour
                                                     , appointmentModel.Status
                                             );
                                         }

                                         @Override
                                         public int getListItemLayoutId() {
                                             return R.layout.appointment_list_item;
                                         }
                                     });
                                 }
                             }
                             listView.endLoadListView();
                         }

                         @Override
                         public void onFailure(Call<AppointmentListModel> call, Throwable t) {
                             listView.endLoadListView();
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
