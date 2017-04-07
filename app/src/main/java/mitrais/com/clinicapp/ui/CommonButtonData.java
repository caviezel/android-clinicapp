package mitrais.com.clinicapp.ui;

import android.view.View;

/**
 * Created by mtmac20 on 4/7/17.
 */

public class CommonButtonData {
    String Text;
    int ColorId;
    View.OnClickListener OnClick;
    public CommonButtonData(String text, int colorId, View.OnClickListener onClick) {
        Text = text;
        ColorId = colorId;
        OnClick = onClick;
    }
}