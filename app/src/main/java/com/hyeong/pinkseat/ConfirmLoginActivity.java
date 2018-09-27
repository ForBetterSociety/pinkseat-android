package com.hyeong.pinkseat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class ConfirmLoginActivity extends AppCompatActivity {

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_login);

        if(AutoLoginPreference.getID(ConfirmLoginActivity.this).length() == 0) {
            // call Login Activity
            intent = new Intent(ConfirmLoginActivity.this, LoginActivity.class);
            startActivity(intent);
            this.finish();
        } else {
            // Call Next Activity
            intent = new Intent(ConfirmLoginActivity.this, Main2Activity.class);
            intent.putExtra("id", AutoLoginPreference.getID(this).toString());
            intent.putExtra("name", AutoLoginPreference.getName(this).toString());
            intent.putExtra("date", AutoLoginPreference.getDate(this).toString());
            intent.putExtra("hospital", AutoLoginPreference.getHospital(this).toString());
            intent.putExtra("idx", AutoLoginPreference.getIdx(this).toString());

            startActivity(intent);
            this.finish();
        }

    }
}
