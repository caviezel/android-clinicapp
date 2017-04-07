package mitrais.com.clinicapp.activities.mainmenu;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import mitrais.com.clinicapp.R;
import mitrais.com.clinicapp.rest.models.DoctorListModel;
import mitrais.com.clinicapp.rest.models.DoctorModel;
import mitrais.com.clinicapp.rest.services.WebServices;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mtmac20 on 3/24/17.
 */

interface IDoctorListListener {
    void onDoctorItemSelected(DoctorListItem doctorItem);
}

class DoctorListItem {
    String Id;
    String Name;
    public DoctorListItem(String id, String name) {
        Id = id;
        Name = name;
    }
}

public class DoctorListFragment extends DialogFragment implements ICommonListListener<DoctorListItem> {
    private CommonListView<DoctorListItem> listView;
    private List<IDoctorListListener> listeners = new ArrayList<IDoctorListListener>();

    //region ICommonListListener
    public void setListViewItem(View view, DoctorListItem item) {
        if (null != view) {
            TextView tName = (TextView) view.findViewById(R.id.txt_person_name);
            if (null != tName) {
                tName.setText(item.Name);
            }
        }
    }

    public void onItemSelected(DoctorListItem item) {
        for (int i = 0; i < listeners.size(); ++i) {
            listeners.get(i).onDoctorItemSelected(item);
        }
        dismiss();
    }
    //endregion

    @Override
    public void onStart()
    {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null)
        {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.popup_appointment_doctor, container, false);

        listView = new CommonListView(view
                , getContext()
                , R.id.list_common
                , R.layout.generic_person_item
                , R.id.loading_list
                , this);

        Button btnClose = (Button) view.findViewById(R.id.btn_close);
        btnClose.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dismiss();
                    }
                }
        );

        doGetDoctorList();

        return view;
    }

    public void doGetDoctorList() {
        if (null == listView) {
            return;
        }

        WebServices services = new WebServices();
        Call<DoctorListModel> call = services.getServices().doGetDoctors();
        listView.beginRetrieveList();
        call.enqueue(new Callback<DoctorListModel>() {
            @Override
            public void onResponse(Call<DoctorListModel> call, Response<DoctorListModel> response) {
                boolean shouldUpdateList = response.body().Status;
                if (shouldUpdateList) {
                    listView.clearListView();
                    for (int i = 0; i < response.body().Doctors.size(); ++i) {
                        DoctorModel drModel = response.body().Doctors.get(i);
                        listView.addListItem(
                                new DoctorListItem(drModel.Id, drModel.Person.Name)
                        );
                    }
                }
                listView.endRetrieveList();
            }

            @Override
            public void onFailure(Call<DoctorListModel> call, Throwable t) {
                listView.endRetrieveList();
            }
        });
    }

    public void setDoctorListListener(IDoctorListListener listener) {
        listeners.add(listener);
    }
}
