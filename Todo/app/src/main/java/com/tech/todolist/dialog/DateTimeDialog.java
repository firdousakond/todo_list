package com.tech.todolist.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;


import com.tech.todolist.R;
import com.tech.todolist.util.Logger;
import com.tech.todolist.view.todo.AddEditTodoActivity;

import java.util.Calendar;

public class DateTimeDialog extends Dialog {

    private final Activity activity;
    private TimePicker timePicker;
    private DatePicker dateDialogPicker;
    private Button btnOk;
    private TextView txtHeader;
    private boolean isFirst;
    private ImageView ibtnBack;

    public DateTimeDialog(Activity activity) {
        super(activity);
        isFirst = true;
        this.activity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_date_time);
        if (getWindow() != null) {
            getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        iniUI();
    }

    private void iniUI() {

        timePicker = findViewById(R.id.timePicker);
        timePicker.setVisibility(View.GONE);
        dateDialogPicker = findViewById(R.id.datePicker);
        btnOk = findViewById(R.id.btnOkay);

        ibtnBack = findViewById(R.id.ivBack);
        txtHeader = findViewById(R.id.tvHeader);
        txtHeader.setText(activity.getString(R.string.select_date));

        initListener();
    }

    private void initListener() {

        ibtnBack.setOnClickListener(new ImageButtonBackOnClickListener());
        btnOk.setOnClickListener(new ButtonOkayOnClickListener());
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        dateDialogPicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), (datePicker, year, month, dayOfMonth) -> Logger.Companion.debug("onDateChanged"));
        dateDialogPicker.setMinDate(Calendar.getInstance().getTimeInMillis() - 1000);
        timePicker.setOnTimeChangedListener((view, hourOfDay, minute) ->
                Logger.Companion.debug("Time Changed"));
    }

    private class ImageButtonBackOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            if (isFirst) {
                dismiss();
            } else {
                isFirst = true;
                dateDialogPicker.setVisibility(View.VISIBLE);
                timePicker.setVisibility(View.GONE);
                txtHeader.setText(activity.getString(R.string.select_date));
            }
        }
    }

    private class ButtonOkayOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            if (isFirst) {
                isFirst = false;
                dateDialogPicker.setVisibility(View.GONE);
                timePicker.setVisibility(View.VISIBLE);
                timePicker.setCurrentMinute(timePicker.getCurrentMinute());
                timePicker.setCurrentHour(timePicker.getCurrentHour());
                txtHeader.setText(activity.getString(R.string.select_time));
                btnOk.setText(activity.getString(R.string.done));
            } else {
                Calendar calendar = Calendar.getInstance();
                calendar.set(dateDialogPicker.getYear(), dateDialogPicker.getMonth(), dateDialogPicker.getDayOfMonth(), timePicker.getCurrentHour(), timePicker.getCurrentMinute());

                long timeInMillis = calendar.getTimeInMillis();

                if (activity instanceof AddEditTodoActivity) {
                    ((AddEditTodoActivity) activity).setDateTime(timeInMillis);
                }

                dismiss();

            }
        }
    }


}
