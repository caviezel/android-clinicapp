package mitrais.com.common.ui;

import android.view.View;

/**
 * Created by mtmac20 on 4/7/17.
 */

public interface ICommonInfoListener<T> {
    void onBuildCommonInfo(T item, View view, ICommonInfoFragment fragment);
    void onCloseCommonInfo();
}