package mitrais.com.clinicapp.activities.mainmenu;

import android.support.v4.app.Fragment;

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
    public CharSequence getTabTitle(int pos) {
        switch (pos) {
            case 0:
                return "APPOINTMENT";
            case 1:
                return "PROFILE";
        }
        return null;
    }
    public int getTabCount() {
        return 2;
    }
}
