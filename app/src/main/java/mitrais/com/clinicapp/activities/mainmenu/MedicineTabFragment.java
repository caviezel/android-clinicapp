package mitrais.com.clinicapp.activities.mainmenu;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import mitrais.com.clinicapp.R;
import mitrais.com.clinicapp.rest.models.MedicineListModel;
import mitrais.com.clinicapp.rest.models.MedicineModel;
import mitrais.com.clinicapp.rest.services.WebServices;
import mitrais.com.common.ui.BasicInfoBuilder;
import mitrais.com.common.ui.CommonInfoFragment;
import mitrais.com.common.ui.CommonListView;
import mitrais.com.common.ui.ICommonInfoFragment;
import mitrais.com.common.ui.ICommonInfoListener;
import mitrais.com.common.ui.ICommonListListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mtmac20 on 3/31/17.
 */

class MedicineListItem {
    String Name;
    String Quantity;
    public MedicineListItem(String name, String quantity) {
        Name = name;
        Quantity = quantity;
    }
}

public class MedicineTabFragment extends Fragment implements ICommonListListener<MedicineListItem>, ICommonInfoListener<MedicineListItem> {
    private CommonListView<MedicineListItem> listView;

    //region ICommonInfoListener
    public void onBuildCommonInfo(MedicineListItem item, View view, ICommonInfoFragment fragment) {
        BasicInfoBuilder infoBuilder = new BasicInfoBuilder(
                view
                , fragment
                , R.drawable.medicine_icon
                , item.Name
                , item.Quantity
        );
    }
    public void onCloseCommonInfo() {

    }
    //endregion

    //region ICommonListListener
    public void setListViewItem(View view, MedicineListItem item) {
        if (null != view) {
            TextView tPatient = (TextView) view.findViewById(R.id.txt_medicine_name);
            if (null != tPatient ) {
                tPatient.setText(item.Name);
            }
        }
    }

    public void onItemSelected(MedicineListItem item) {
        CommonInfoFragment infoFragment = new CommonInfoFragment();
        infoFragment.setCommonInfoData(R.layout.popup_common_info, item)
                .setListener(this)
                .setCloseButton("OK");
        infoFragment.show(getChildFragmentManager(), null);
    }
    //endregion

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_medicine_tab, container, false);

        //the appointment list
        listView = new CommonListView(view
                , getContext()
                , R.id.list_common
                , R.layout.medicine_list_item
                , R.id.loading_list
                , this);

        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (isVisibleToUser) {
            doGetMedicines();
        }
    }

    public void doGetMedicines() {
        WebServices webServices = new WebServices();
        Call<MedicineListModel> call = webServices.getServices().doGetMedicines();

        listView.beginRetrieveList();

        call.enqueue(new Callback<MedicineListModel>() {
            @Override
            public void onResponse(Call<MedicineListModel> call, Response<MedicineListModel> response) {
                listView.clearListView();
                List<MedicineModel> medicineModels = response.body().Medicines;
                for (int i = 0; i < medicineModels.size(); ++i) {
                    listView.addListItem(new MedicineListItem(
                            medicineModels.get(i).Name
                            , Integer.toString(medicineModels.get(i).Quantity)
                    ));
                }
                listView.endRetrieveList();
            }

            @Override
            public void onFailure(Call<MedicineListModel> call, Throwable t) {
                listView.endRetrieveList();
            }

        });
    }
}
