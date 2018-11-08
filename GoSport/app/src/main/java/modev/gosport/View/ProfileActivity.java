package modev.gosport.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import modev.gosport.Algorithm.HashedPassword;
import modev.gosport.Class.Dialog;
import modev.gosport.Class.RegisterUser;
import modev.gosport.R;
import modev.gosport.SqlLite.DBHandler;


public class ProfileActivity extends AppCompatActivity {
    String userID;
    private Button btnRegister;
    private EditText txtFirstname;
    private EditText txtLastname;
    private EditText txtEmail;
    private EditText txtOldPassword;
    private EditText txtNewPassword;
    private EditText txtRepeatNewPassword;
    private RegisterUser selectedUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_main);

        final DBHandler db = new DBHandler(this);
        userID = db.getUserID();

        btnRegister = (Button) findViewById(R.id.registerButton);
        txtFirstname = (EditText) findViewById(R.id.edittextFirstname);
        txtLastname = (EditText) findViewById(R.id.edittextLastname);
        txtEmail = (EditText) findViewById(R.id.edittextEmail);
        txtOldPassword = (EditText) findViewById(R.id.edittextOldPassword);
        txtNewPassword = (EditText) findViewById(R.id.edittextNewPassword);
        txtRepeatNewPassword = (EditText) findViewById(R.id.edittextRepeatNewPassword);

        getUser(userID);
        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_calendar:
                                Intent intent = new Intent(ProfileActivity.this, CalendarEventsActivity.class);
                                startActivity(intent);
                                break;
                            case R.id.action_events:
                                intent = new Intent(ProfileActivity.this, ListEventsActivity.class);
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
                            if (selectedUser.getpassword().toString().equals(resultPassword)){
                                String newPassword = txtNewPassword.getText().toString();
                                String repeatNewPassword = txtRepeatNewPassword.getText().toString();
                                if (newPassword.equals(repeatNewPassword)){
                                    hashedPassword = HashedPassword.create(newPassword);
                                    resultPassword = hashedPassword.toString();
                                    boolean succes = false;
                                    DatabaseReference dR = FirebaseDatabase.getInstance().getReference("user").child(userID);
                                    RegisterUser user = new RegisterUser(userID, firstName, lastName, email, resultPassword);
                                    dR.setValue(user);
                                    succes = true;
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
                            DatabaseReference dR = FirebaseDatabase.getInstance().getReference("user").child(userID);
                            RegisterUser user = new RegisterUser(userID, firstName, lastName, email, selectedUser.getpassword());
                            dR.setValue(user);
                            Dialog d = new Dialog("", "Changes has been saved", ProfileActivity.this);
                            d.dialogOK();
                        }
                    }
                });
    }

    private void getUser(final String userID) {
        DatabaseReference databaseUsers = FirebaseDatabase.getInstance().getReference("user");
        databaseUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    RegisterUser user = postSnapshot.getValue(RegisterUser.class);
                    if (user.getId().equals(userID)){
                        txtFirstname.setText(user.getFirstName());
                        txtLastname.setText(user.getLastName());
                        txtEmail.setText(user.getEmail());
                        selectedUser = user;
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
