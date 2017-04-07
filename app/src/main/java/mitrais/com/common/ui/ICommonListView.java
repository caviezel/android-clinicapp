package mitrais.com.common.ui;

import android.view.View;

/**
 * Created by mtmac20 on 4/7/17.
 */

public interface ICommonListView<T> {
    View buildListItemView(View view, T item);
}