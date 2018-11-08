package modev.gosport.View;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
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

        /*db.addEvent(new EventInformation("TRAIL DES FANTÔMES","La Roche-en-Ardenne", "De 9de editie van de Trail des Fantômes gaat door op 11 en 12 augustus 2018. Deze intussen “klassieker” onder de trailwedstrijden staat ook in 2018 opnieuw garant voor uitdagende parcoursen doorheen de prachtige natuur van La Roche-en-Ardenne. Tijdens deze editie kan je kiezen uit maar liefst 8 verschillende afstanden gaande van 10 tot 70km! Op zaterdag 11 augustus staan de 70km, 50km, 33km en 14km gepland. De dag erna, op zondag 12 augustus, bieden we de keuze uit 27km, 22km, 16km of 10km.", 2018, 10, 13, 13, 14  ));
        db.addEvent(new EventInformation("SCOTT XTRAILS VAALS","Vaals", "Het weekend van 1 en 2 september 2018  komt de Scott Xtrails terug naar het grensstadje Vaals, gelegen bij het drielandenpunt met België, Nederland en Duitsland. Op zaterdagavond staat de 15km lange PETZL Nighttrail op het programma, op zondag zijn er 4 verschillende keuzes tijdens de Vaalserbergtrail (11/21/30/50km). Enkel voor de deelnemers van de Xtrails, is er zaterdag namiddag al de proloog van 4km.", 2018, 10, 13, 13, 14  ));
        db.addEvent(new EventInformation("TRAIL DES TRAPPISTES","Chimay", "bkababaj", 2018, 10, 24, 13, 14  ));
        db.addEvent(new EventInformation("AFTER WORK TRAIL","De Kempen Lichtaart", "bkababaj", 2018, 10, 24, 13, 14  ));
        db.addEvent(new EventInformation("TRAIL DU BARRAGE","Engreux", "bkababaj", 2018, 10, 20, 13, 14  ));
        db.addEvent(new EventInformation("TRAIL BRUSSEL","Brussel", "bkababaj", 2018, 11, 6, 13, 14  ));
        db.addEvent(new EventInformation("NIGHTTRAIL KOKSIJDE","Koksijde", "bkababaj", 2018, 11, 22, 13, 14  ));

        db.addUserForEvent("-LPpm_k3GqfI6j6fLTwa", db.getEvent(1).getId());
        db.addUserForEvent("-LPpm_k3GqfI6j6fLTwa", db.getEvent(3).getId());*/


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
                        AsyncTaskRunner runner = new AsyncTaskRunner();
                        runner.execute(username.getText().toString(), password.getText().toString());
                    }
                });
    }
    final DBHandler db = new DBHandler(this);
    private class AsyncTaskRunner extends AsyncTask<String, String, String> {

        private String resp;
        ProgressDialog progressDialog;

        @Override
        protected void onPostExecute(String result) {
            // execution of result of Long time consuming operation
            progressDialog.dismiss();
            if (result != null){
                Dialog d = new Dialog("", result, MainActivity.this);
                d.dialogOK();
            }
        }


        @Override
        protected String doInBackground(String... strings) {
            try {
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

                            db.addUser(id);
                            Intent intent = new Intent(MainActivity.this, ListEventsActivity.class);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                resp = e.getMessage();
            }
            return resp;
        }

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(MainActivity.this,
                    "logging in",
                    "Wait a moment");
        }
    }
}


