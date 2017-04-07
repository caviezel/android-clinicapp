package mitrais.com.common.ui;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import mitrais.com.clinicapp.R;

/**
 * Created by mtmac20 on 4/7/17.
 */

public class BasicInfoBuilder {
    public BasicInfoBuilder(View view, final ICommonInfoFragment fragment, int iconId, String title, String description) {
        ImageView imgView = (ImageView) view.findViewById(R.id.cominfo_img_icon);
        imgView.setImageResource(iconId);

        TextView tTitle = (TextView) view.findViewById(R.id.txt_title);
        tTitle.setText(title);

        TextView tDesc = (TextView) view.findViewById(R.id.txt_description);
        tDesc.setText(description);
    }
}