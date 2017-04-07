package mitrais.com.common.ui;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Created by mtmac20 on 4/7/17.
 */

public class CommonListAdapter<T> extends ArrayAdapter<T> {
    private List<T> items;
    private ICommonListView<T> listView;
    public CommonListAdapter(Context context, int resource, List<T> theItems, ICommonListView<T> theListView) {
        super(context, resource, theItems);
        items = theItems;
        listView = theListView;
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent) {
        return listView.buildListItemView(convertView, items.get(pos));
    }
}