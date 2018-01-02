package com.example.opoku.aware_esms_tutorials;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.aware.Aware;
import com.aware.Aware_Preferences;
import com.aware.ESM;
import com.aware.ui.esms.ESMFactory;
import com.aware.ui.esms.ESM_Freetext;

import org.json.JSONException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Initialising aware
        Intent aware = new Intent(this,Aware.class);
        startService(aware);

        // connect aware to online awareframework.com dashboard

        if(!Aware.isStudy(this  )){
            Aware.joinStudy(this,"The online awareframework.com study url");
        }
        // change defualt aware settings
        Aware.setSetting(this,Aware_Preferences.FREQUENCY_WEBSERVICE,15);

        // initisialising ESM service
        Aware.setSetting(this,Aware_Preferences.STATUS_ESM,true);
        //ESM definitions
        try{
            ESMFactory factory=new ESMFactory();
            ESM_Freetext yourName= new ESM_Freetext();
            yourName.setTitle("Your Name");
            //yourName.ins
            //add ESM definition to ESM service
            ESM.queueESM(this,factory.build());

        }catch (Exception ex){
            ex.printStackTrace();
        }


        /// What happens when user interacts with ESM
        IntentFilter filter=new IntentFilter();
        filter.addAction(ESM.ACTION_AWARE_ESM_ANSWERED); // USER ANSWERS THE ESM
        filter.addAction(ESM.ACTION_AWARE_ESM_DISMISSED);// User dismisses the ESM

        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if(intent.getAction()==ESM.ACTION_AWARE_ESM_ANSWERED){
                    // TAKE ACTION
                }
                if(intent.getAction()==ESM.ACTION_AWARE_ESM_DISMISSED){
                    //
                }
            }
        }, filter);
    }
}
