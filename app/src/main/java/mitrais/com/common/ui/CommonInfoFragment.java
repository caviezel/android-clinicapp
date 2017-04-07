package mitrais.com.common.ui;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import mitrais.com.clinicapp.R;

/**
 * Created by mtmac20 on 4/4/17.
 */

public class CommonInfoFragment<T> extends DialogFragment implements ICommonInfoFragment {
    ICommonInfoListener listener;
    int layoutId;
    T data;
    List<CommonButtonData> btnDatas = new ArrayList<CommonButtonData>();
    //separate the close btn data to ensure that the close btn will be added to the last in the group
    CommonButtonData btnCloseData;

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
        View rootView = inflater.inflate(layoutId, container, false);
        LinearLayout btnLayout = (LinearLayout) rootView.findViewById(R.id.commoninfo_btn_group);
        for (int i = 0; i < btnDatas.size(); ++i) {
            View btnView = inflater.inflate(R.layout.button_common, container,false);
            CommonButton btn = new CommonButton(btnView, btnDatas.get(i));
            btnLayout.addView(btnView);
        }
        if (null != btnCloseData) {
            View btnView = inflater.inflate(R.layout.button_common, container,false);
            CommonButton btn = new CommonButton(btnView, btnCloseData);
            btnLayout.addView(btnView);
        }
        if (null != listener) {
            listener.onBuildCommonInfo(data, rootView, this);
        }
        return rootView;
    }

    public void closeCommonInfo() {
        dismiss();
        if (null != listener) {
            listener.onCloseCommonInfo();
        }
    }

    public CommonInfoFragment<T> setCommonInfoData(int theLayoutId, T theData) {
        layoutId = theLayoutId;
        data = theData;
        return this;
    }

    public CommonInfoFragment<T> setListener(ICommonInfoListener l) {
        listener = l;
        return this;
    }

    public CommonInfoFragment<T> setButton(CommonButtonData btnData) {
        btnDatas.add(btnData);
        return this;
    }

    public CommonInfoFragment<T> setCloseButton(String txt) {
        btnCloseData = new CommonButtonData(
                txt
                , R.color.colorButtonRed
                , new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeCommonInfo();
            }
        });
        return this;
    }
}
