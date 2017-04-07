package mitrais.com.clinicapp.activities.mainmenu;

import android.support.v4.app.Fragment;

/**
 * Created by mtmac20 on 4/6/17.
 */

public class PatientTabBuilder implements ITabFragmentBuilder {
    public Fragment getTabFragment(int pos) {
        switch (pos) {
            case 0:
                AppointmentTabFragment apptTab = new AppointmentTabFragment();
                return apptTab;
            case 1:
                ComplaintTabFragment complaintTab = new ComplaintTabFragment();
                return complaintTab ;
            case 2:
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
                return "COMPLAINTS";
            case 2:
                return "PROFILE";
        }
        return null;
    }
    public int getTabCount() {
        return 3;
    }
}
