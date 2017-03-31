package mitrais.com.clinicapp.activities.mainmenu;

import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import mitrais.com.clinicapp.R;

/**
 * Created by mtmac20 on 3/27/17.
 */


class ConfirmationData {
    public String Name;
    public String Doctor;
    public String TheDate;
    public String TheHour;
    public ConfirmationData(String name, String dr, String date, String hour) {
        Name = name;
        Doctor = dr;
        TheDate = date;
        TheHour = hour;
    }
}

interface IAppointmentConfirmListener {
    void onCloseConfirm();
}

public class AppointmentConfirmationFragment extends DialogFragment {
    TextView txtName;
    TextView txtHour;
    TextView txtDoctor;
    TextView txtDate;
    ConfirmationData confirmationData;
    IAppointmentConfirmListener listener;

    @Override
    public void onStart()
    {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null)
        {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.popup_appointment_confirmation, container, false);
        Button btnClose = (Button) rootView.findViewById(R.id.btn_close);
        btnClose.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        closeDialog();
                    }
                }
        );

        Button btnConfirm = (Button) rootView.findViewById(R.id.btn_confirm_appt);
        btnConfirm.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        closeDialog();
                    }
                }
        );

        txtDate = (TextView) rootView.findViewById(R.id.txt_appt_conf_date);
        txtDate.setText(confirmationData.TheDate);
        txtDoctor = (TextView) rootView.findViewById(R.id.txt_appt_conf_dr);
        txtDoctor.setText("Dr. " + confirmationData.Doctor);
        txtHour = (TextView) rootView.findViewById(R.id.txt_appt_conf_hour);
        txtHour.setText(confirmationData.TheHour);
        txtName = (TextView) rootView.findViewById(R.id.txt_appt_conf_name);
        String confName = confirmationData.Name + ", we've got you confirmed for the appointment.";
        txtName.setText(confName);

        return rootView;
    }

    private void closeDialog() {
        dismiss();
        listener.onCloseConfirm();
    }

    public void setConfirmationTexts(ConfirmationData data) {
        confirmationData = data;
    }

    public void setListener(IAppointmentConfirmListener l) {
        listener = l;
    }
}
