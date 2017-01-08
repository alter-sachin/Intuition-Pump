package io.github.catcharide.sampleapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.NumberPicker;
import android.widget.Spinner;

import io.github.catcharide.sampleapp.configuration.Constants;

public class QuesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ques);

        Spinner spinner = (Spinner) findViewById(R.id.spinner);

        ArrayAdapter<CharSequence> adapter =
                ArrayAdapter.createFromResource(this, R.array.meet_type, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        if (spinner != null) {
            spinner.setAdapter(adapter);
        }

        NumberPicker numberPicker = (NumberPicker)findViewById(R.id.numberPicker);

        assert numberPicker != null;

        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(7);

    }

    public void startMeeting(View view) {

        Intent intent = new Intent(this, MeetActivity.class);
        final NumberPicker numberPicker = (NumberPicker) findViewById(R.id.numberPicker);
        final Spinner spinner =  (Spinner) findViewById(R.id.spinner);
        String meeting_type = spinner.getSelectedItem().toString();
        int no_of_people = numberPicker.getValue();
        System.out.println("Meeting Type Selected " + meeting_type + " No. of people " + no_of_people);
        intent.putExtra(Constants.MEET_TYPE,meeting_type);
        intent.putExtra(Constants.NO_OF_PEOPLE,no_of_people);
        startActivity(intent);

    }
}
