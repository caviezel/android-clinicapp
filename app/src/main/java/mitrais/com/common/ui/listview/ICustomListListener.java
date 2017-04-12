package mitrais.com.common.ui.listview;

import android.view.View;

/**
 * Created by mtmac20 on 4/10/17.
 */

public interface ICustomListListener {
    <T> View buildListItemView(View listItemView, T item);
    <T> void onItemSelected(T item);
}
