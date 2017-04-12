package mitrais.com.common.ui.listview;

import android.view.View;

/**
 * Created by mtmac20 on 4/11/17.
 */

public final class LoadableView {
    View view;
    OnLoadViewData data;
    public LoadableView(View parent, OnLoadViewData theData) {
        data = theData;
        view = parent.findViewById(data.ViewId);
    }
    public void setLoading(boolean isLoading) {
        if (isLoading) {
            view.setVisibility(data.ShouldShowOnLoad ? View.VISIBLE : View.INVISIBLE);
        } else {
            view.setVisibility(data.ShouldShowOnLoad ? View.INVISIBLE : View.VISIBLE);
        }
    }
}
