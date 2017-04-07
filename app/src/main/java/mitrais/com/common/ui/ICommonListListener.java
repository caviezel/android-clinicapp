package mitrais.com.common.ui;

import android.view.View;

/**
 * Created by mtmac20 on 4/7/17.
 */

public interface ICommonListListener<T> {
    void setListViewItem(View view, T item);
    void onItemSelected(T item);
}