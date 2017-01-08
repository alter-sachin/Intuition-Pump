package io.github.catcharide.sampleapp;

import android.content.Intent;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.support.annotation.StringDef;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;
import java.util.UUID;

import io.github.catcharide.sampleapp.configuration.Constants;

public class MeetActivity extends AppCompatActivity implements TextToSpeech.OnInitListener{

    public static final String[] HATS_ORDER_1 = {"BLUE","WHITE","YELLOW","BLACK","GREEN","RED","BLUE"};

    public static final String[] HATS_ORDER_2 = {"BLUE","WHITE","RED","YELLOW","BLACK","GREEN","BLUE"};

    private TextToSpeech tts;

    private String meetIntro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meet);

        tts = new TextToSpeech(this, this);

        Intent intent = getIntent();
        String meeting_type = intent.getStringExtra(Constants.MEET_TYPE);
        int no_of_people = intent.getIntExtra(Constants.NO_OF_PEOPLE,-1);
        String[] chosenOne ;
        if("New idea".equalsIgnoreCase(meeting_type)){
            //do some stuff and choose the hats order.
            chosenOne = HATS_ORDER_1;
        }else {
            chosenOne = HATS_ORDER_2;
        }
        int meeting_length = no_of_people*5;
        meetIntro = "Hi, Your meeting has been scheduled for " + meeting_length + " minutes and the following hat order has been choosen.";
        for (String hat : chosenOne){
            meetIntro += " " + hat;
        }
        meetIntro += ".";
        TextView textView = (TextView)findViewById(R.id.textView2);
        textView.append(meetIntro);
        if(BuildConfig.DEBUG && no_of_people == -1){
            throw new AssertionError();
        }
    }

    @Override
    protected void onDestroy() {
        // Don't forget to shutdown tts!
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {

            int result = tts.setLanguage(Locale.US);

            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                System.out.println("TTS This Language is not supported");
            }

        } else {
            System.out.println("TTS Initialization Failed!");
        }
    }

    public void speakThis(String text){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            tts.speak(text,TextToSpeech.QUEUE_FLUSH,null, UUID.randomUUID().toString());
        }else {
            tts.speak(text,TextToSpeech.QUEUE_FLUSH,null);
        }
    }

    public void speakUp(View view) {
        speakThis(meetIntro);
    }

    public void goForBlue(View view) {
        String[] questions = {"Why are we here ?",
                "What are we thinking about ?",
                "The definition of the situation",
                "What we want to achieve ?"};

        for(String question : questions){
            speakThis(question);

        }

    }
}
