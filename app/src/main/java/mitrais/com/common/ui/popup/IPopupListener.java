package mitrais.com.common.ui.popup;

import android.view.View;

/**
 * Created by mtmac20 on 4/7/17.
 */

public interface IPopupListener<T> {
    void onBuildCommonInfo(T item, View view, IPopupFragment fragment);
    void onCloseCommonInfo();
}