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
 * Created by mtmac20 on 3/21/17.
 */

class AppointmentItem {
    String PersonName;
    String Date;
    String Hour;
    String Status;
    public AppointmentItem(String personName, String date, String hour, String status) {
        PersonName = personName;
        Date = date;
        Hour = hour;
        Status = status;
    }
}

public class AppointmentListAdapter extends ArrayAdapter<AppointmentItem> {
    public AppointmentListAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    private List<AppointmentItem> items;

    public AppointmentListAdapter(Context context, int resource, List<AppointmentItem> items) {
        super(context, resource, items);
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.appointment_list_item, null);
        }

        AppointmentItem p = items.get(position);

        if (p != null) {
            TextView tvPatient = (TextView) v.findViewById(R.id.txt_person_name);
            if (null != tvPatient) {
                tvPatient.setText(p.PersonName);
            }
            TextView tvDate = (TextView) v.findViewById(R.id.txt_date);
            if (null != tvDate) {
                tvDate.setText(p.Date);
            }
            TextView tvHour = (TextView) v.findViewById(R.id.txt_hour);
            if (null != tvHour) {
                tvHour.setText(p.Hour);
            }
            TextView tvStatus = (TextView) v.findViewById(R.id.txt_status);
            if (null != tvStatus) {
                tvStatus.setText(p.Status);
            }
        }

        return v;
    }
}
