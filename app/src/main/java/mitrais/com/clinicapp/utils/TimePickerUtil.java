package mitrais.com.clinicapp.utils;

import android.annotation.SuppressLint;
import android.widget.NumberPicker;
import android.widget.TimePicker;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mtmac20 on 4/6/17.
 */

public class TimePickerUtil {
    public int Interval;

    public TimePickerUtil(int interval) {
        Interval = interval;
    }

    @SuppressLint("NewApi")
    public void setTimePickerInterval(TimePicker timePicker) {
        try {
            Class<?> classForid = Class.forName("com.android.internal.R$id");

            Field field = classForid.getField("minute");
            NumberPicker minutePicker = (NumberPicker) timePicker
                    .findViewById(field.getInt(null));

            minutePicker.setMinValue(0);
            minutePicker.setMaxValue(3);
            List<String> displayedValues = new ArrayList<String>();
            for (int i = 0; i < 60; i += Interval) {
                displayedValues.add(String.format("%02d", i));
            }
            minutePicker.setDisplayedValues(displayedValues
                    .toArray(new String[0]));
            minutePicker.setWrapSelectorWheel(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
