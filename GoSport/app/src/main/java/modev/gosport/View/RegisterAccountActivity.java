package modev.gosport.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import modev.gosport.Class.RegisterUser;
import modev.gosport.R;


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

        btnRegister = (Button) findViewById(R.id.registerButton);
        txtFirstname = (EditText) findViewById(R.id.edittextFirstname);
        txtLastname = (EditText) findViewById(R.id.edittextLastname);
        txtEmail = (EditText) findViewById(R.id.edittextEmail);
        txtPassword = (EditText) findViewById(R.id.edittextPassword);

        btnRegister.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        DatabaseReference databaseArtist;
                        databaseArtist = FirebaseDatabase.getInstance().getReference("user");

                        String id = databaseArtist.push().getKey();

                        RegisterUser user = new RegisterUser(txtFirstname.getText().toString(),txtLastname.getText().toString(), txtEmail.getText().toString(), txtPassword.getText().toString());
                        user.setId(id);
                        databaseArtist.child(id).setValue(user);

                        Intent intent = new Intent(RegisterAccountActivity.this, HomeActivity.class);
                        intent.putExtra("user", id);
                        startActivity(intent);
                    }
                });
    }
}
