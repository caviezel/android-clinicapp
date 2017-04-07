package mitrais.com.common.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mtmac20 on 3/31/17.
 */

public class CommonListView<T> implements ICommonListView<T> {
    private ListView listView;
    private ProgressBar progressBar;
    private CommonListAdapter adapter;
    private ArrayList<T> arrayList;
    private Context context;
    private int listItemId;
    private ICommonListListener<T> listListener;

    public CommonListView(View view, Context theContext, int listId, int theListItemId, int progressBarId, final ICommonListListener<T> listener) {
        context = theContext;
        listItemId = theListItemId;
        listListener = listener;
        listView = (ListView) view.findViewById(listId);
        progressBar = (ProgressBar) view.findViewById(progressBarId);
        arrayList = new ArrayList<T>();
        adapter = new CommonListAdapter<T>(context, listItemId, arrayList, this);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        listener.onItemSelected(arrayList.get(position));
                    }
                }
        );
    }

    public View buildListItemView(View view, T model) {
        if (null == view) {
            LayoutInflater vi = LayoutInflater.from(context);
            view = vi.inflate(listItemId, null);
        }

        listListener.setListViewItem(view, model);

        return view;
    }

    public void beginRetrieveList() {
        listView.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
    }

    public void endRetrieveList() {
        notifyChanged();
        listView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
    }

    public void clearListView() {
        arrayList.clear();
    }
    public void addListItem(T item) {
        arrayList.add(item);
    }
    public void notifyChanged() {
        adapter.notifyDataSetChanged();
    }
}
