package facebook.example.com.gosport.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import facebook.example.com.gosport.Class.RegisterUser;
import facebook.example.com.gosport.R;
import facebook.example.com.gosport.SqlLite.DBHandler;

public class RegisterAccountActivity extends AppCompatActivity {
    private Button btnRegister;
    private EditText txtFirstname;
    private EditText txtLastname;
    private EditText txtEmail;
    private EditText txtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_main);

        String email = getIntent().getExtras().getString("email");

        final DBHandler db = new DBHandler(this);

        btnRegister = (Button) findViewById(R.id.registerButton);
        txtFirstname = (EditText) findViewById(R.id.edittextFirstname);
        txtLastname = (EditText) findViewById(R.id.edittextLastname);
        txtEmail = (EditText) findViewById(R.id.edittextEmail);
        txtPassword = (EditText) findViewById(R.id.edittextPassword);

        txtEmail.setText(email);

        btnRegister.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        RegisterUser user;
                        int id = (int) db.addUser(user = new RegisterUser(txtFirstname.getText().toString(),txtLastname.getText().toString(), txtEmail.getText().toString(), txtPassword.getText().toString()));
                        Intent intent = new Intent(RegisterAccountActivity.this, HomeActivity.class);
                        intent.putExtra("user", id);
                        startActivity(intent);
                    }
                });
    }
}
