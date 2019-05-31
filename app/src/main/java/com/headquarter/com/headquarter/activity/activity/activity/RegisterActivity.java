package com.headquarter.com.headquarter.activity.activity.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.headquarter.R;

public class RegisterActivity extends AppCompatActivity {

    CheckBox checkbox;
    EditText numero;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        checkbox = findViewById(R.id.checkBox);
        numero = findViewById(R.id.editNumero);

        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(checkbox.isChecked()){
                    numero.setVisibility(View.VISIBLE);
                }
                else{
                    numero.setVisibility(View.GONE);
                }
            }
        });
    }
}
