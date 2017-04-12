package mitrais.com.common.ui.popup;

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

public final class PopupFragment<T> extends DialogFragment implements IPopupFragment {
    IPopupListener listener;
    int layoutId;
    T data;
    List<PopupButtonData> btnDatas = new ArrayList<PopupButtonData>();
    //separate the close btn data to ensure that the close btn will be added to the last in the group
    PopupButtonData btnCloseData;

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
            PopupButton btn = new PopupButton(btnView, btnDatas.get(i));
            btnLayout.addView(btnView);
        }
        if (null != btnCloseData) {
            View btnView = inflater.inflate(R.layout.button_common, container,false);
            PopupButton btn = new PopupButton(btnView, btnCloseData);
            btnLayout.addView(btnView);
        }
        if (null != listener) {
            listener.onBuildCommonInfo(data, rootView, this);
        }
//        rootView.setClipToOutline(true);
        return rootView;
    }

    public void closeCommonInfo() {
        dismiss();
        if (null != listener) {
            listener.onCloseCommonInfo();
        }
    }

    public PopupFragment<T> setCommonInfoData(int theLayoutId, T theData) {
        layoutId = theLayoutId;
        data = theData;
        return this;
    }

    public PopupFragment<T> setListener(IPopupListener l) {
        listener = l;
        return this;
    }

    public PopupFragment<T> setButton(PopupButtonData btnData) {
        btnDatas.add(btnData);
        return this;
    }

    public PopupFragment<T> setCloseButton(String txt) {
        btnCloseData = new PopupButtonData(
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
