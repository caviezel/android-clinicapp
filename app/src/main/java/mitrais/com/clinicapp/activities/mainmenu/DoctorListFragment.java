package mitrais.com.clinicapp.activities.mainmenu;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

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

public class DoctorListFragment extends DialogFragment {
    private ListView listView;
    private DoctorListAdapter adapter;
    private ArrayList<DoctorItem> arrayList;
    private List<IDoctorListListener> listeners = new ArrayList<IDoctorListListener>();

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
        View rootView = inflater.inflate(R.layout.popup_appointment_doctor, container, false);

        //the appointment list
        listView = (ListView) rootView.findViewById(R.id.list_doctor);
        arrayList = new ArrayList<DoctorItem>();
        adapter = new DoctorListAdapter(getContext(), R.layout.generic_person_item, arrayList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        DoctorItem item = arrayList.get(position);
                        for (int i = 0; i < listeners.size(); ++i) {
                            listeners.get(i).onDoctorItemSelected(item);
                        }
                        dismiss();
                    }
                }
        );

        Button btnClose = (Button) rootView.findViewById(R.id.btn_close);
        btnClose.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dismiss();
                    }
                }
        );

        return rootView;
    }

    public void doGetDoctorList() {
        WebServices services = new WebServices();
        Call<DoctorListModel> call = services.getServices().doGetDoctors();
        call.enqueue(new Callback<DoctorListModel>() {
            @Override
            public void onResponse(Call<DoctorListModel> call, Response<DoctorListModel> response) {
                boolean shouldUpdateList = response.body().Status;
                if (shouldUpdateList) {
                    arrayList.clear();
                    for (int i = 0; i < response.body().Doctors.size(); ++i) {
                        DoctorModel drModel = response.body().Doctors.get(i);
                        arrayList.add(
                                new DoctorItem(drModel.Id, drModel.Person.Name)
                        );
                    }
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<DoctorListModel> call, Throwable t) {

            }
        });
    }

    public void setDoctorListListener(IDoctorListListener listener) {
        listeners.add(listener);
    }
}
