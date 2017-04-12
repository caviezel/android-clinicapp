package mitrais.com.common.ui.popup;

import android.view.View;

/**
 * Created by mtmac20 on 4/7/17.
 */

public final class PopupButtonData {
    String Text;
    int ColorId;
    View.OnClickListener OnClick;
    public PopupButtonData(String text, int colorId, View.OnClickListener onClick) {
        Text = text;
        ColorId = colorId;
        OnClick = onClick;
    }
}