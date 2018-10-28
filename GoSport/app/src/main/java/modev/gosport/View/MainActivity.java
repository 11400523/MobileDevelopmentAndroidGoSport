package modev.gosport.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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

public class MainActivity extends AppCompatActivity {

    private TextView register;
    private Button btnLogin;
    private EditText username;
    private EditText password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final DBHandler db = new DBHandler(this);
        register = (TextView) findViewById(R.id.txtRegister);
        btnLogin = (Button) findViewById(R.id.loginButton);
        username = (EditText) findViewById(R.id.edittextUsername);
        password = (EditText) findViewById(R.id.edittextPassword);

        /*db.addEvent(new EventInformation("TRAIL DES FANTÔMES","La Roche-en-Ardenne", "De 9de editie van de Trail des Fantômes gaat door op 11 en 12 augustus 2018. Deze intussen “klassieker” onder de trailwedstrijden staat ook in 2018 opnieuw garant voor uitdagende parcoursen doorheen de prachtige natuur van La Roche-en-Ardenne. Tijdens deze editie kan je kiezen uit maar liefst 8 verschillende afstanden gaande van 10 tot 70km! Op zaterdag 11 augustus staan de 70km, 50km, 33km en 14km gepland. De dag erna, op zondag 12 augustus, bieden we de keuze uit 27km, 22km, 16km of 10km.", 2018, 8, 13, 13, 14  ));
        db.addEvent(new EventInformation("SCOTT XTRAILS VAALS","Vaals", "Het weekend van 1 en 2 september 2018  komt de Scott Xtrails terug naar het grensstadje Vaals, gelegen bij het drielandenpunt met België, Nederland en Duitsland. Op zaterdagavond staat de 15km lange PETZL Nighttrail op het programma, op zondag zijn er 4 verschillende keuzes tijdens de Vaalserbergtrail (11/21/30/50km). Enkel voor de deelnemers van de Xtrails, is er zaterdag namiddag al de proloog van 4km.", 2018, 8, 13, 13, 14  ));
        db.addEvent(new EventInformation("TRAIL DES TRAPPISTES","Chimay", "bkababaj", 2018, 8, 24, 13, 14  ));
        /*db.addEvent(new EventInformation("AFTER WORK TRAIL","De Kempen Lichtaart", "bkababaj", 2018, 8, 24, 13, 14  ));
        db.addEvent(new EventInformation("TRAIL DU BARRAGE","Engreux", "bkababaj", 2018, 8, 20, 13, 14  ));
        db.addEvent(new EventInformation("TRAIL BRUSSEL","Brussel", "bkababaj", 2018, 9, 6, 13, 14  ));
        db.addEvent(new EventInformation("NIGHTTRAIL KOKSIJDE","Koksijde", "bkababaj", 2018, 9, 22, 13, 14  ));
        db.addUser(new RegisterUser("Michiel", "Smeets", "michiel_smeets2@hotmail.com", "test"));
        db.addUser(new RegisterUser("Stef", "Smeets", "stef_smeets2@hotmail.com", "test"));
        db.addUser(new RegisterUser("test", "test", "test@hotmail.com", "test"));
        /*db.addUserForEvent(db.getUserRegister(1).getId(), db.getEvent(1).getId());
        db.addUserForEvent(db.getUserRegister(2).getId(), db.getEvent(1).getId());
        db.addUserForEvent(db.getUserRegister(1).getId(), db.getEvent(3).getId());*/


        register.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        Intent intent = new Intent(MainActivity.this, RegisterAccountActivity.class);
                        startActivity(intent);
                    }
                });


        btnLogin.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {


                        DatabaseReference databaseUsers = FirebaseDatabase.getInstance().getReference("user");
                        databaseUsers.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                RegisterUser selectedUser = null;
                                HashedPassword hashedPassword = HashedPassword.create(password.getText().toString());
                                String resultPassword = hashedPassword.toString();
                                String usernameEmail = username.getText().toString();

                                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                    RegisterUser user = postSnapshot.getValue(RegisterUser.class);
                                    if (user.getEmail().toLowerCase().equals(usernameEmail.toLowerCase()) && user.getpassword().equals(resultPassword)){
                                        selectedUser = user;
                                        break;
                                    }
                                }
                                if (selectedUser == null){
                                    Dialog d = new Dialog("", "Your password or username is incorrect", MainActivity.this);
                                    d.dialogOK();
                                }
                                if (selectedUser != null){
                                    String id = selectedUser.getId();
                                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                                    intent.putExtra("user", selectedUser.getId());
                                    startActivity(intent);
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                });
    }
}
