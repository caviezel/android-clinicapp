package mitrais.com.common.ui.listview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

/**
 * Created by mtmac20 on 4/10/17.
 */

public final class CustomListAdapter<T> extends ArrayAdapter<ICustomListItemModel<T>> {
    private List<ICustomListItemModel<T>> items;
    private ICustomListListener listener;
    private Context context;
    public CustomListAdapter(Context theContext, int resource, List<ICustomListItemModel<T>> theListModels, ICustomListListener theListener, ListView listView) {
        super(theContext, resource, theListModels);
        context = theContext;
        items = theListModels;
        listener = theListener;
        listView.setAdapter(this);
        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        listener.onItemSelected(items.get(position).getListItemModel());
                    }
                }
        );
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent) {
        ICustomListItemModel<T> listItemModel = items.get(pos);
        LayoutInflater vi = LayoutInflater.from(context);
        convertView = vi.inflate(listItemModel.getListItemLayoutId(), null);
        return listener.buildListItemView(convertView, listItemModel.getListItemModel());
    }

    public void resetList() {
        items.clear();
    }

    public void addListItem(ICustomListItemModel<T> itemModel) {
        items.add(itemModel);
    }
}
