package mitrais.com.clinicapp.activities.mainmenu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import mitrais.com.clinicapp.R;

/**
 * Created by mtmac20 on 3/24/17.
 */

class DoctorItem {
    String Id;
    String Name;
    public DoctorItem(String id, String name) {
        Id = id;
        Name = name;
    }
}

public class DoctorListAdapter extends ArrayAdapter<DoctorItem> {
    public DoctorListAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    private List<DoctorItem> items;

    public DoctorListAdapter(Context context, int resource, List<DoctorItem> items) {
        super(context, resource, items);
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.generic_person_item, null);
        }

        DoctorItem p = items.get(position);

        if (p != null) {
            TextView tName = (TextView) v.findViewById(R.id.txt_person_name);
            if (null != tName) {
                tName.setText(p.Name);
            }
        }

        return v;
    }
}
