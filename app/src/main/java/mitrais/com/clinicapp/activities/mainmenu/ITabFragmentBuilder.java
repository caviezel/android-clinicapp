package mitrais.com.clinicapp.activities.mainmenu;

import android.support.v4.app.Fragment;

/**
 * Created by mtmac20 on 4/6/17.
 */

public interface ITabFragmentBuilder {
    Fragment getTabFragment(int pos);
    CharSequence getTabTitle(int pos);
    int getTabCount();
}