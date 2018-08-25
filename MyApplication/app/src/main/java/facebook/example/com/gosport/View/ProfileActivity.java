package facebook.example.com.gosport.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import facebook.example.com.gosport.Algorithm.HashedPassword;
import facebook.example.com.gosport.Class.Dialog;
import facebook.example.com.gosport.Class.RegisterUser;
import facebook.example.com.gosport.R;
import facebook.example.com.gosport.SqlLite.DBHandler;

public class ProfileActivity extends AppCompatActivity {
    int userID;
    private Button btnRegister;
    private EditText txtFirstname;
    private EditText txtLastname;
    private EditText txtEmail;
    private EditText txtOldPassword;
    private EditText txtNewPassword;
    private EditText txtRepeatNewPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_main);

        userID = getIntent().getExtras().getInt("user");
        final DBHandler db = new DBHandler(this);

        btnRegister = (Button) findViewById(R.id.registerButton);
        txtFirstname = (EditText) findViewById(R.id.edittextFirstname);
        txtLastname = (EditText) findViewById(R.id.edittextLastname);
        txtEmail = (EditText) findViewById(R.id.edittextEmail);
        txtOldPassword = (EditText) findViewById(R.id.edittextOldPassword);
        txtNewPassword = (EditText) findViewById(R.id.edittextNewPassword);
        txtRepeatNewPassword = (EditText) findViewById(R.id.edittextRepeatNewPassword);

        final RegisterUser userInfo = db.getUserRegister(userID);
        txtFirstname.setText(userInfo.getFirstName());
        txtLastname.setText(userInfo.getLastName());
        txtEmail.setText(userInfo.getEmail());

        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_calendar:
                                Intent intent = new Intent(ProfileActivity.this, CalendarEventsActivity.class);
                                intent.putExtra("user", userID);
                                startActivity(intent);
                                break;
                            case R.id.action_events:
                                intent = new Intent(ProfileActivity.this, HomeActivity.class);
                                intent.putExtra("user", userID);
                                startActivity(intent);
                                break;
                            case R.id.action_profile:
                                break;
                        }
                        return false;
                    }
                });

        btnRegister.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        String firstName = txtFirstname.getText().toString();
                        String lastName = txtLastname.getText().toString();
                        String email = txtEmail.getText().toString();
                        String oldPassword = txtOldPassword.getText().toString();
                        if (!oldPassword.equals("")){
                            HashedPassword hashedPassword = HashedPassword.create(oldPassword);
                            String resultPassword = hashedPassword.toString();
                            if (userInfo.getpassword().toString().equals(resultPassword)){
                                String newPassword = txtNewPassword.getText().toString();
                                String repeatNewPassword = txtRepeatNewPassword.getText().toString();
                                if (newPassword.equals(repeatNewPassword)){
                                    hashedPassword = HashedPassword.create(newPassword);
                                    resultPassword = hashedPassword.toString();
                                    boolean succes = db.updateUser(userID, firstName, lastName, email,resultPassword);
                                    if (succes){
                                        Dialog d = new Dialog("", "Changes has been saved", ProfileActivity.this);
                                        d.dialogOK();
                                    } else {
                                        Dialog d = new Dialog("", "Something went wrong, try again!", ProfileActivity.this);
                                        d.dialogOK();
                                    }
                                } else{
                                    Dialog d = new Dialog("", "Your repeated password is incorrect", ProfileActivity.this);
                                    d.dialogOK();
                                }
                            } else{
                                Dialog d = new Dialog("", "Your old password is incorrect", ProfileActivity.this);
                                d.dialogOK();
                            }
                        }
                        else{
                            db.updateUser(userID, firstName, lastName, email, userInfo.getpassword().toString());
                            Dialog d = new Dialog("", "Changes has been saved", ProfileActivity.this);
                            d.dialogOK();
                        }
                    }
                });
    }
}
