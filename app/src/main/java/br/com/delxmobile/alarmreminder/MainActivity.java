package br.com.delxmobile.alarmreminder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TimePickerDialog mDialogHourMinute;
    ListView list;
    ArrayAdapter<String> arrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView start = (TextView) findViewById(R.id.start);
        final TextView end = (TextView) findViewById(R.id.end);
        final TextView interval = (TextView) findViewById(R.id.interval);
        list = (ListView) findViewById(R.id.times);
        Button button = (Button) findViewById(R.id.submit);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPickerDialog(start);
            }
        });

        end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPickerDialog(end);
            }
        });

        interval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPickerDialog(interval);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar startTime = getCalendarBySring(start.getText().toString());
                Calendar endTime = getCalendarBySring(end.getText().toString());

                String intervalTime = interval.getText().toString();

                int hours = Integer.parseInt(intervalTime.split(":")[0]);
                int minutes = Integer.parseInt(intervalTime.split(":")[1]);

                List<Calendar> allAlarms = CalendarUtil.getAllAlarms(startTime, endTime, hours, minutes);

                arrayAdapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, getStringCalendar(allAlarms));
                list.setAdapter(arrayAdapter);
            }
        });
    }

    private void showPickerDialog(final TextView text){

        mDialogHourMinute = new TimePickerDialog.Builder()
                .setType(Type.HOURS_MINS)
                .setHourText(" h")
                .setMinuteText(" min")
                .setCancelStringId(getString(R.string.cancel))
                .setSureStringId(getString(R.string.ok))
                .setTitleStringId("")
                .setThemeColor(getResources().getColor(R.color.colorAccent))
                .setCallBack(new OnDateSetListener() {
                    @Override
                    public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
                        Date date =  new Date(millseconds);
                        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
                        text.setText(format.format(date));


                    }
                })
                .build();
        mDialogHourMinute.show(getSupportFragmentManager(), "hour_minute");

    }

    private Calendar getCalendarBySring(String date){
        Calendar c = Calendar.getInstance();
        int hours = Integer.parseInt(date.split(":")[0]);
        int minutes = Integer.parseInt(date.split(":")[1]);

        c.set(Calendar.HOUR_OF_DAY, hours);
        c.set(Calendar.MINUTE, minutes);

        return c;
    }

    private ArrayList<String> getStringCalendar(List<Calendar> times){

        ArrayList<String> alarm = new ArrayList<String>();

        SimpleDateFormat format = new SimpleDateFormat("HH:mm dd/MM/yyyy ");

        for(Calendar time : times){
            alarm.add(format.format(new Date(time.getTimeInMillis())));
        }

        return alarm;

    }

}
