package mitrais.com.clinicapp.activities.mainmenu;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TimePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import mitrais.com.clinicapp.R;
import mitrais.com.clinicapp.rest.models.CoreModel;
import mitrais.com.clinicapp.rest.models.StatusResponseModel;
import mitrais.com.clinicapp.rest.services.WebServices;
import mitrais.com.common.utils.TimePickerUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mtmac20 on 3/22/17.
 */

interface IAppointmentCreateListener {
    void onConfirmData(ConfirmationData data);
}

public class AppointmentCreateFragment extends DialogFragment implements CalendarView.OnDateChangeListener, TimePicker.OnTimeChangedListener, IDoctorListListener {
    Button btnDate;
    Button btnTime;
    Button btnSelectDoctor;
    CalendarView calendarView;
    TimePicker timePicker;
    DoctorListFragment doctorListFragment;
    DoctorListItem selectedDoctor;
    IAppointmentCreateListener listener;
    TimePickerUtil timePickerUtil;

    @Override
    public void onStart() {
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.popup_appointment_create, container, false);

        timePickerUtil = new TimePickerUtil(15);
        timePicker = (TimePicker) rootView.findViewById(R.id.timepicker);
        timePicker.setOnTimeChangedListener(this);
        timePicker.setDescendantFocusability(TimePicker.FOCUS_BLOCK_DESCENDANTS);
        timePickerUtil.setTimePickerInterval(timePicker);

        calendarView = (CalendarView) rootView.findViewById(R.id.calendar);
        calendarView.setOnDateChangeListener(this);

        //btn for opening up the calendar view
        btnDate = (Button) rootView.findViewById(R.id.btn_date);
        btnDate.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        timePicker.setVisibility(View.GONE);
                        calendarView.setVisibility(View.VISIBLE);
                    }
                }
        );
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        btnDate.setText(sdf.format(calendarView.getDate()));

        //btn for opening up the timepicker
        btnTime = (Button) rootView.findViewById(R.id.btn_time);
        btnTime.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        calendarView.setVisibility(View.GONE);
                        timePicker.setVisibility(View.VISIBLE);
                    }
                }
        );
        btnTime.setText(timePicker.getCurrentHour()+":45");

        Button btnClose = (Button) rootView.findViewById(R.id.btn_close);
        btnClose.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dismiss();
                    }
                }
        );

        doctorListFragment = new DoctorListFragment();
        doctorListFragment.setDoctorListListener(this);

        btnSelectDoctor = (Button) rootView.findViewById(R.id.btn_select_doctor);
        btnSelectDoctor.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        doctorListFragment.show(getActivity().getFragmentManager(), "doclist");
                        doctorListFragment.doGetDoctorList();
                    }
                }
        );

        Button btnCreateAppt = (Button) rootView.findViewById(R.id.btn_create_appt);
        btnCreateAppt.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        createAppointment();
                    }
                }
        );

        return rootView;
    }

    public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
        // add one because month starts at 0
        month = month + 1;
        String newDate = String.format("%02d",dayOfMonth)+"/"+String.format("%02d",month)+"/"+year;
        btnDate.setText(newDate);
    }

    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
        String newTime = hourOfDay + ":" + String.format("%02d", minute * timePickerUtil.Interval);
        btnTime.setText(newTime);
    }

    public void onDoctorItemSelected(DoctorListItem item) {
        selectedDoctor = item;
        btnSelectDoctor.setText(selectedDoctor.Name);
    }

    private String getCompiledDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");
        Date apptDate = new Date();
        String curDate = btnDate.getText() + " " + btnTime.getText();
        try {
            apptDate = sdf.parse(curDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String formattedDate = new SimpleDateFormat("yyyy-MM-dd hh:mm").format(apptDate);
        return formattedDate;
    }

    public void createAppointment() {
        final ProgressDialog progress = new ProgressDialog(getActivity());
        progress.setTitle("Loading");
        progress.setMessage("Logging in...");
        progress.setCancelable(false);
        progress.show();

        boolean canCreate = true;
        if (canCreate) {
            WebServices ws = new WebServices();
            Call<StatusResponseModel> call = ws.getServices().doCreateAppointment(
                    getCompiledDate()
                    , CoreModel.getInstance().getLoginModel().Credentials.Id
                    , Integer.parseInt(selectedDoctor.Id)
            );

            final ConfirmationData confData = new ConfirmationData(
                    CoreModel.getInstance().getLoginModel().Credentials.Name
                    , selectedDoctor.Name
                    , btnDate.getText().toString()
                    , btnTime.getText().toString()
            );

            call.enqueue(new Callback<StatusResponseModel>() {
                             @Override
                             public void onResponse(Call<StatusResponseModel> call, Response<StatusResponseModel> response) {
                                 progress.dismiss();
                                 if (response.body().Status) {
                                     listener.onConfirmData(confData);
                                     dismiss();
                                 }
                             }

                             @Override
                             public void onFailure(Call<StatusResponseModel> call, Throwable t) {
                                 progress.dismiss();
                             }
                         }
            );
        }
    }

    public void setListener(IAppointmentCreateListener l) {
        listener = l;
    }
}
