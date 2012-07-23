package com.appsmarttech.utpro;

import android.os.Bundle;

import com.actionbarsherlock.app.SherlockFragmentActivity;

public class Programs_Activity extends SherlockFragmentActivity {

	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //sets an transition animation
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        setContentView(R.layout.programs_activity);
        
    }
}
