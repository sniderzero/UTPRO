package com.appsmarttech.ut90;

import android.os.Bundle;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.appsmarttech.utpro.R;

public class BandSet_Activity extends SherlockFragmentActivity {

	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //sets an transition animation
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        setContentView(R.layout.bandset_activity);
        
    }
}
