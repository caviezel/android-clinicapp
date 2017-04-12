package mitrais.com.clinicapp.activities.mainmenu;

import android.view.View;
import android.widget.TextView;

import mitrais.com.clinicapp.R;

/**
 * Created by mtmac20 on 4/11/17.
 */

public class OnClinicListItemBuilder {
    public OnClinicListItemBuilder(View view, ComplaintListItem complaintListItem) {
        if (null != view) {
            TextView tDesc = (TextView) view.findViewById(R.id.txt_compaint_desc);
            if (null != tDesc ) {
                tDesc.setText(complaintListItem.Description);
            }
        }
    }

    public OnClinicListItemBuilder(View view, AppointmentListItem item) {
        if (null != view) {
            TextView tPatient = (TextView) view.findViewById(R.id.txt_person_name);
            if (null != tPatient) {
                tPatient.setText(item.EditedPersonName);
            }
            TextView tDate = (TextView) view.findViewById(R.id.txt_date);
            if (null != tDate) {
                tDate.setText(item.Date);
            }
            TextView tHour = (TextView) view.findViewById(R.id.txt_hour);
            if (null != tHour) {
                tHour.setText(item.Hour);
            }
            TextView tStatus = (TextView) view.findViewById(R.id.txt_status);
            if (null != tStatus) {
                tStatus.setText(item.Status);
            }
        }
    }
}
