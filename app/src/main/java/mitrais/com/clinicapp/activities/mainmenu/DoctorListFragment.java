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
import mitrais.com.common.ui.listview.CustomListView;
import mitrais.com.common.ui.listview.ICustomListItemModel;
import mitrais.com.common.ui.listview.ICustomListListener;
import mitrais.com.common.ui.listview.OnLoadViewData;
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

public class DoctorListFragment extends DialogFragment implements ICustomListListener {
    private CustomListView<DoctorListItem> listView;
    private List<IDoctorListListener> listeners = new ArrayList<IDoctorListListener>();

    //region ICustomListListener
    public <T> View buildListItemView(View view, T item) {
        DoctorListItem doctorListItem = (DoctorListItem) item;
        if (null != doctorListItem) {
            TextView tName = (TextView) view.findViewById(R.id.txt_person_name);
            if (null != tName) {
                tName.setText(doctorListItem.Name);
            }
        }
        return view;
    }

    public <T> void onItemSelected(T item) {
        DoctorListItem doctorListItem = (DoctorListItem) item;
        if (null != doctorListItem) {
            for (int i = 0; i < listeners.size(); ++i) {
                listeners.get(i).onDoctorItemSelected(doctorListItem);
            }
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

        List<OnLoadViewData> onLoadViewDatas = new ArrayList<>();
        onLoadViewDatas.add(new OnLoadViewData(R.id.list_common, false));
        onLoadViewDatas.add(new OnLoadViewData(R.id.loading_list, true));

        listView = new CustomListView(view
                , getContext()
                , R.id.list_common
                , R.layout.generic_person_item
                , this
                , onLoadViewDatas
        );

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
        listView.beginLoadListView();
        call.enqueue(new Callback<DoctorListModel>() {
            @Override
            public void onResponse(Call<DoctorListModel> call, Response<DoctorListModel> response) {
                boolean shouldUpdateList = response.body().Status;
                if (shouldUpdateList) {
                    listView.clearListView();
                    for (int i = 0; i < response.body().Doctors.size(); ++i) {
                        final DoctorModel drModel = response.body().Doctors.get(i);

                        listView.addListItem(new ICustomListItemModel<DoctorListItem>() {
                            @Override
                            public DoctorListItem getListItemModel() {
                                return new DoctorListItem(drModel.Id, drModel.Person.Name);
                            }

                            @Override
                            public int getListItemLayoutId() {
                                return R.layout.generic_person_item;
                            }
                        });
                    }
                }
                listView.endLoadListView();
            }

            @Override
            public void onFailure(Call<DoctorListModel> call, Throwable t) {
                listView.endLoadListView();
            }
        });
    }

    public void setDoctorListListener(IDoctorListListener listener) {
        listeners.add(listener);
    }
}
