package mitrais.com.clinicapp.activities.mainmenu;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import mitrais.com.clinicapp.R;
import mitrais.com.clinicapp.rest.models.ComplaintListModel;
import mitrais.com.clinicapp.rest.models.ComplaintModel;
import mitrais.com.clinicapp.rest.models.CoreModel;
import mitrais.com.clinicapp.rest.models.CredentialsModel;
import mitrais.com.clinicapp.rest.services.WebServices;
import mitrais.com.clinicapp.ui.CommonButtonData;
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

public class ComplaintTabFragment extends Fragment implements ICommonListListener<ComplaintListItem>, ICommonInfoListener<ComplaintListItem> {
    private CommonListView<ComplaintListItem> listView;

    //region ICommonInfoListener
    public void onBuildCommonInfo(ComplaintListItem item, View view, ICommonInfoFragment fragment) {
        BasicInfoBuilder infoBuilder = new BasicInfoBuilder(
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

    //region ICommonListListener
    public void setListViewItem(View view, ComplaintListItem item) {
        if (null != view) {
            TextView tDesc = (TextView) view.findViewById(R.id.txt_compaint_desc);
            if (null != tDesc ) {
                tDesc.setText(item.Description);
            }
        }
    }

    public void onItemSelected(ComplaintListItem item) {
        CommonInfoFragment infoFragment = new CommonInfoFragment();
        infoFragment.setCommonInfoData(R.layout.popup_common_info, item)
                .setListener(this)
                .setCloseButton("CANCEL")
                .setButton(new CommonButtonData(
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
    //endregion

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_complaint_tab, container, false);

        //the appointment list
        listView = new CommonListView(view
                , getContext()
                , R.id.list_common
                , R.layout.complaint_list_item
                , R.id.loading_list
                , this);

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

        listView.beginRetrieveList();

        call.enqueue(new Callback<ComplaintListModel>() {
            @Override
            public void onResponse(Call<ComplaintListModel> call, Response<ComplaintListModel> response) {
                listView.clearListView();
                List<ComplaintModel> models = response.body().Complaints;
                for (int i = 0; i < models.size(); ++i) {
                    listView.addListItem(new ComplaintListItem(models.get(i).Description));
                }
                listView.endRetrieveList();
            }

            @Override
            public void onFailure(Call<ComplaintListModel> call, Throwable t) {
                listView.endRetrieveList();
            }
        });
    }
}
