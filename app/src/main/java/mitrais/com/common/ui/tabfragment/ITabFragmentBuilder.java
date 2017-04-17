package mitrais.com.common.ui.tabfragment;

import android.content.Context;
import android.support.v4.app.Fragment;

/**
 * Created by mtmac20 on 4/6/17.
 */

public interface ITabFragmentBuilder {
    Fragment getTabFragment(int pos);
    CharSequence getTabTitle(Context context, int pos);
    int getTabCount();
}