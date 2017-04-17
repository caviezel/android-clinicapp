package mitrais.com.clinicapp.activities.mainmenu;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;

import mitrais.com.clinicapp.R;
import mitrais.com.common.ui.tabfragment.ITabFragmentBuilder;

/**
 * Created by mtmac20 on 4/6/17.
 */

public class DoctorTabBuilder implements ITabFragmentBuilder {
    public Fragment getTabFragment(int pos) {
        switch (pos) {
            case 0:
                AppointmentTabFragment apptTab = new AppointmentTabFragment();
                return apptTab;
            case 1:
                ProfileTabFragment profileTab = new ProfileTabFragment();
                return profileTab;
        }
        return null;
    }
    public CharSequence getTabTitle(Context context, int pos) {
        int drawableId = R.drawable.appointment_icon;

        switch (pos) {
            case 1:
                drawableId = R.drawable.group_icon;
                break;
        }

        Drawable image = context.getResources().getDrawable(drawableId);
        image.setBounds(0,0,80, 80);
        SpannableString sb = new SpannableString(" ");
        ImageSpan imageSpan = new ImageSpan(image, ImageSpan.ALIGN_BOTTOM);
        sb.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sb;
    }
    public int getTabCount() {
        return 2;
    }
}
