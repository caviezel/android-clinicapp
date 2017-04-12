package mitrais.com.clinicapp.activities.mainmenu;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import mitrais.com.clinicapp.R;
import mitrais.com.clinicapp.rest.models.MedicineListModel;
import mitrais.com.clinicapp.rest.models.MedicineModel;
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

public class MedicineTabFragment extends Fragment implements ICustomListListener, IPopupListener<MedicineListItem> {
    private CustomListView<MedicineListItem> listView;

    //region IPopupListener
    public void onBuildCommonInfo(MedicineListItem item, View view, IPopupFragment fragment) {
        PopupBuilder infoBuilder = new PopupBuilder(
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

    //region ICustomListListener
    public <T> View buildListItemView(View view, T item) {
        MedicineListItem medicineListItem = (MedicineListItem) item;
        if (null != medicineListItem) {
            TextView tPatient = (TextView) view.findViewById(R.id.txt_medicine_name);
            if (null != tPatient ) {
                tPatient.setText(medicineListItem.Name);
            }
        }
        return view;
    }

    public <T> void onItemSelected(T item) {
        MedicineListItem medicineListItem = (MedicineListItem) item;
        if (null != medicineListItem) {
            PopupFragment infoFragment = new PopupFragment();
            infoFragment.setCommonInfoData(R.layout.popup_common_info, medicineListItem)
                    .setListener(this)
                    .setCloseButton("OK");
            infoFragment.show(getChildFragmentManager(), null);
        }
    }
    //endregion

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_medicine_tab, container, false);

        List<OnLoadViewData> onLoadViewDatas = new ArrayList<>();
        onLoadViewDatas.add(new OnLoadViewData(R.id.list_common, false));
        onLoadViewDatas.add(new OnLoadViewData(R.id.loading_list, true));

        listView = new CustomListView(view
                , getContext()
                , R.id.list_common
                , R.layout.medicine_list_item
                , this
                , onLoadViewDatas
        );

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

        listView.beginLoadListView();

        call.enqueue(new Callback<MedicineListModel>() {
            @Override
            public void onResponse(Call<MedicineListModel> call, Response<MedicineListModel> response) {
                listView.clearListView();
                List<MedicineModel> medicineModels = response.body().Medicines;
                for (int i = 0; i < medicineModels.size(); ++i) {
                    final MedicineModel medicineModel = medicineModels.get(i);

                    listView.addListItem(new ICustomListItemModel<MedicineListItem>() {
                        @Override
                        public MedicineListItem getListItemModel() {
                            return new MedicineListItem(medicineModel.Name
                                    , Integer.toString(medicineModel.Quantity));
                        }

                        @Override
                        public int getListItemLayoutId() {
                            return R.layout.medicine_list_item;
                        }
                    });
                }
                listView.endLoadListView();
            }

            @Override
            public void onFailure(Call<MedicineListModel> call, Throwable t) {
                listView.endLoadListView();
            }

        });
    }
}
