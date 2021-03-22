package id.ac.umn.uts_27731;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    EditText username, password;
    Button btnLogin;
    int counter = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = (EditText) findViewById(R.id.et_username);
        password = (EditText) findViewById(R.id.et_password);
        btnLogin = (Button) findViewById(R.id.btn_login);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(username.getText().toString().equals("uasmobile") && password.getText().toString().equals("uasmobilegenap")) {
                    Toast.makeText(getApplicationContext(), "Redirecting...", Toast.LENGTH_SHORT).show();
                    Intent intentList = new Intent(LoginActivity.this, DaftarLagu.class);
                    startActivity(intentList);
                } else {
                    if (counter != 0) {
                        counter--;
                        Toast.makeText(getApplicationContext(), "Wrong Credentials. Remaining Attempt : " + counter, Toast.LENGTH_SHORT).show();
                    } else if (counter == 0){
                        Toast.makeText(getApplicationContext(), "Login Attempt Revoked." + counter, Toast.LENGTH_SHORT).show();
                        btnLogin.setEnabled(false);
                    }
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent mainMenu = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(mainMenu);
        //super.onBackPressed();
    }
}