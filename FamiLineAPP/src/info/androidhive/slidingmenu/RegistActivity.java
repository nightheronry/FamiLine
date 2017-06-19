package info.androidhive.slidingmenu;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RegistActivity extends Activity {
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.regist);
        getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#d48c06")));
        Button registbtn = (Button)findViewById(R.id.registbtn);
        registbtn.setOnClickListener(
        		new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    	Intent intent = new Intent();
                        intent.setClass(RegistActivity.this, MainActivity.class);
                        startActivity(intent);
                    	finish();
                    }
            });
        
    }
}
	


