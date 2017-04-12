package mitrais.com.common.ui.listview;

/**
 * Created by mtmac20 on 4/11/17.
 */

public class OnLoadViewData {
    public int ViewId;
    public boolean ShouldShowOnLoad;
    public OnLoadViewData(int id, boolean showOnLoad) {
        ViewId = id;
        ShouldShowOnLoad = showOnLoad;
    }
}
