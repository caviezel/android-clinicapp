package mitrais.com.common.ui.listview;

import android.content.Context;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mtmac20 on 4/11/17.
 */

public final class CustomListView<T> {
    private ListView listView;
    private CustomListAdapter<T> adapter;
    private List<LoadableView> loadableViews;

    public CustomListView(View view, Context theContext, int listViewId, int defaultListItemId, ICustomListListener theListener, List<OnLoadViewData> onLoadViewDatas) {
        listView = (ListView) view.findViewById(listViewId);

        loadableViews = new ArrayList<>();
        for (int i = 0; i < onLoadViewDatas.size(); ++i) {
            loadableViews.add(new LoadableView(view, onLoadViewDatas.get(i)));
        }

        adapter = new CustomListAdapter<T>(
                theContext
                , defaultListItemId
                , new ArrayList<ICustomListItemModel<T>>()
                , theListener
                , listView
        );
    }

    public void beginLoadListView() {
        for (int i = 0; i < loadableViews.size(); ++i) {
            loadableViews.get(i).setLoading(true);
        }
    }

    public void endLoadListView() {
        for (int i = 0; i < loadableViews.size(); ++i) {
            loadableViews.get(i).setLoading(false);
        }
    }

    public void clearListView() {
        adapter.clear();
    }

    public void addListItem(ICustomListItemModel<T> item) {
        adapter.addListItem(item);
    }

    public void notifyChanged() {
        adapter.notifyDataSetChanged();
    }
}
