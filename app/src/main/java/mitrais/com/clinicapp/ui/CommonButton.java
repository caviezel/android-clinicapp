package mitrais.com.clinicapp.ui;

import android.view.View;
import android.widget.Button;

import mitrais.com.clinicapp.R;

/**
 * Created by mtmac20 on 4/7/17.
 */

public class CommonButton {
    public CommonButton(View view, CommonButtonData btnData) {
        Button btn = (Button) view.findViewById(R.id.btn_common);
        if (null != btn) {
            btn.setText(btnData.Text);
            btn.setBackgroundResource(btnData.ColorId);
            btn.setOnClickListener(btnData.OnClick);
        }
    }
}
