package mitrais.com.clinicapp.activities.mainmenu;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import mitrais.com.clinicapp.R;
import mitrais.com.clinicapp.rest.models.ComplaintListModel;
import mitrais.com.clinicapp.rest.models.ComplaintModel;
import mitrais.com.clinicapp.rest.models.CoreModel;
import mitrais.com.clinicapp.rest.models.CredentialsModel;
import mitrais.com.clinicapp.rest.services.WebServices;
import mitrais.com.common.ui.popup.PopupBuilder;
import mitrais.com.common.ui.popup.PopupButtonData;
import mitrais.com.common.ui.popup.PopupFragment;
import mitrais.com.common.ui.popup.IPopupFragment;
import mitrais.com.common.ui.popup.IPopupListener;
import mitrais.com.common.ui.listview.ICustomListItemModel;
import mitrais.com.common.ui.listview.ICustomListListener;
import mitrais.com.common.ui.listview.CustomListView;
import mitrais.com.common.ui.listview.OnLoadViewData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mtmac20 on 4/6/17.
 */

class ComplaintListItem {
    String Description;
    public ComplaintListItem (String description) {
        Description = description;
    }
}

public class ComplaintTabFragment extends Fragment implements ICustomListListener, IPopupListener<ComplaintListItem> {
    private CustomListView<ComplaintListItem> listView;

    //region IPopupListener
    public void onBuildCommonInfo(ComplaintListItem item, View view, IPopupFragment fragment) {
        PopupBuilder infoBuilder = new PopupBuilder(
                view
                , fragment
                , R.drawable.complaint_icon
                , "COMPLAINT"
                , item.Description
        );
    }

    public void onCloseCommonInfo() {

    }
    //endregion

    //region ICustomListListener
    public <T> View buildListItemView(View view, T item) {
        OnClinicListItemBuilder listItemBuilder = new OnClinicListItemBuilder(view, (ComplaintListItem)item);
        return view;
    }

    public <T> void onItemSelected(T item) {
        ComplaintListItem complaintListItem = (ComplaintListItem) item;
        if (null != complaintListItem) {
            PopupFragment infoFragment = new PopupFragment();
            infoFragment.setCommonInfoData(R.layout.popup_common_info, complaintListItem)
                    .setListener(this)
                    .setCloseButton("CANCEL")
                    .setButton(new PopupButtonData(
                            "DO SOMETHING"
                            , R.color.colorPrimary
                            , new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Context context = getContext();
                            CharSequence text = "Coming Soon";
                            int duration = Toast.LENGTH_SHORT;

                            Toast toast = Toast.makeText(context, text, duration);
                            toast.show();
                        }
                    }));
            infoFragment.show(getChildFragmentManager(), null);
        }
    }
    //endregion

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_complaint_tab, container, false);

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

        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (isVisibleToUser) {
            doGetComplaints();
        }
    }

    public void doGetComplaints() {
        final CredentialsModel credentials = CoreModel.getInstance().getLoginModel().Credentials;
        WebServices webServices = new WebServices();
        Call<ComplaintListModel> call = webServices.getServices().doGetComplaints(credentials.Id);

        listView.beginLoadListView();

        call.enqueue(new Callback<ComplaintListModel>() {
            @Override
            public void onResponse(Call<ComplaintListModel> call, Response<ComplaintListModel> response) {
                listView.clearListView();
                List<ComplaintModel> models = response.body().Complaints;
                for (int i = 0; i < models.size(); ++i) {
                    final ComplaintModel curModel = models.get(i);
                    listView.addListItem(new ICustomListItemModel<ComplaintListItem>() {
                        @Override
                        public ComplaintListItem getListItemModel() {
                            return new ComplaintListItem(curModel.Description);
                        }

                        @Override
                        public int getListItemLayoutId() {
                            return R.layout.complaint_list_item;
                        }
                    });
                }
                listView.endLoadListView();
            }

            @Override
            public void onFailure(Call<ComplaintListModel> call, Throwable t) {
                listView.endLoadListView();
            }
        });
    }
}
