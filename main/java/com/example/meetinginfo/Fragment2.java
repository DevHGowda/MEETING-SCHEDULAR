package com.example.meetinginfo;

import static com.example.meetinginfo.R.id.button;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.time.Duration;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.Fragment;

import com.example.meetinginfo.R;

import java.time.temporal.Temporal;
import java.util.Calendar;

import papaya.in.sendmail.SendMail;

public class Fragment2 extends Fragment {
    EditText date;
    CalendarView cal;
    Button btn1;
    Button cancle;
    DataBaseConn dbc;
    TextView t;
   // String med="";
   // String med1="";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment2_layout,container,false);
        date=view.findViewById(R.id.editTextDate);
        cal=view.findViewById(R.id.calendarView);
        btn1=view.findViewById(R.id.btn2);
        cancle=view.findViewById(button);
        dbc=new DataBaseConn(getActivity());
       // t=()

        cal.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String d=dayOfMonth+"/"+(month+1)+"/"+year;
                date.setText(d);
            }
        });

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String d1=date.getText().toString();
                StringBuffer res=new StringBuffer();
                Cursor c=dbc.fetch(d1);
                int count=c.getCount();
                c.moveToFirst();
                if(count>0) {
                    do {
                        res.append(c.getString(c.getColumnIndex("agenda"))+"\t"+"at"+"\t"+c.getString(c.getColumnIndex("time")));
                        res.append("\n");

                        //med = (String.valueOf(c.getString(c.getColumnIndex("agenda"))));
                        //med1 = (String.valueOf(c.getString(c.getColumnIndex("time"))));
                    }while (c.moveToNext());
                        Toast.makeText(getActivity(), res, Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(getActivity(), "No Meeting on This Day....", Toast.LENGTH_LONG).show();

                }

            }
        });

        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String d1=date.getText().toString();
                StringBuffer res=new StringBuffer();
                Cursor c=dbc.fetch(d1);
                int count=c.getCount();
                c.moveToFirst();
                if(count>0) {
                    do {
                        boolean deleted = dbc.deleteValues(d1);  // Pass the date you want to delete (stored in the variable 'd')
                        if (deleted) {
                            // Values associated with the specified date have been deleted successfully
                        } else {
                            // No values were deleted (maybe no values were associated with the specified date)
                        }


                    }while (c.moveToNext());
                    SendMail mail = new SendMail("devhgowda26@gmail.com", "ekumtkemdufjbpfh",
                            "devhgowda26@gmail.com",
                            "Reg. Cancled Meeting",
                            "The Meeting Scheduled is Cancled by the user\nEnjoy Your Day!.");
                    mail.execute();

                    Toast.makeText(getActivity(),  "The Meeting on This Day is Cancled....", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(getActivity(), "No Meeting on This Day....", Toast.LENGTH_LONG).show();

                }
            }
        });

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }
}
