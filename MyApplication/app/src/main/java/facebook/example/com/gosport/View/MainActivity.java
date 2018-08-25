package facebook.example.com.gosport.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;

import java.util.List;

import facebook.example.com.gosport.Algorithm.HashedPassword;
import facebook.example.com.gosport.Class.Dialog;
import facebook.example.com.gosport.Class.MainPresenter;
import facebook.example.com.gosport.Class.RegisterUser;
import facebook.example.com.gosport.R;
import facebook.example.com.gosport.SqlLite.DBHandler;

public class MainActivity extends AppCompatActivity{

    private MainPresenter mainPresenter;
    //private LoginButton btnFBLogin;
    //private Button btnLoginFBLoginManager;
    private CallbackManager callbackManager;
    private TextView register;
    private Button btnLogin, btnGoogleLogin;
    private EditText username;
    private EditText password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        final DBHandler db = new DBHandler(this);
        register = (TextView) findViewById(R.id.txtRegister);
        btnLogin = (Button) findViewById(R.id.loginButton);
        username = (EditText) findViewById(R.id.edittextUsername);
        password = (EditText) findViewById(R.id.edittextPassword);
        btnGoogleLogin = (Button) findViewById(R.id.loginGoogleButton);

        /*db.addEvent(new EventInformation("TRAIL DES FANTÔMES","La Roche-en-Ardenne", "De 9de editie van de Trail des Fantômes gaat door op 11 en 12 augustus 2018. Deze intussen “klassieker” onder de trailwedstrijden staat ook in 2018 opnieuw garant voor uitdagende parcoursen doorheen de prachtige natuur van La Roche-en-Ardenne. Tijdens deze editie kan je kiezen uit maar liefst 8 verschillende afstanden gaande van 10 tot 70km! Op zaterdag 11 augustus staan de 70km, 50km, 33km en 14km gepland. De dag erna, op zondag 12 augustus, bieden we de keuze uit 27km, 22km, 16km of 10km.", 2018, 8, 13, 13, 14  ));
        db.addEvent(new EventInformation("SCOTT XTRAILS VAALS","Vaals", "Het weekend van 1 en 2 september 2018  komt de Scott Xtrails terug naar het grensstadje Vaals, gelegen bij het drielandenpunt met België, Nederland en Duitsland. Op zaterdagavond staat de 15km lange PETZL Nighttrail op het programma, op zondag zijn er 4 verschillende keuzes tijdens de Vaalserbergtrail (11/21/30/50km). Enkel voor de deelnemers van de Xtrails, is er zaterdag namiddag al de proloog van 4km.", 2018, 8, 13, 13, 14  ));
        db.addEvent(new EventInformation("TRAIL DES TRAPPISTES","Chimay", "bkababaj", 2018, 8, 24, 13, 14  ));
        db.addEvent(new EventInformation("AFTER WORK TRAIL","De Kempen Lichtaart", "bkababaj", 2018, 8, 24, 13, 14  ));
        db.addEvent(new EventInformation("TRAIL DU BARRAGE","Engreux", "bkababaj", 2018, 8, 20, 13, 14  ));
        db.addEvent(new EventInformation("TRAIL BRUSSEL","Brussel", "bkababaj", 2018, 9, 6, 13, 14  ));
        db.addEvent(new EventInformation("NIGHTTRAIL KOKSIJDE","Koksijde", "bkababaj", 2018, 9, 22, 13, 14  ));
        db.addUser(new RegisterUser("Michiel", "Smeets", "michiel_smeets2@hotmail.com", "test"));
        db.addUser(new RegisterUser("Stef", "Smeets", "stef_smeets2@hotmail.com", "test"));
        db.addUser(new RegisterUser("test", "test", "test@hotmail.com", "test"));
        db.addUserForEvent(db.getUserRegister(1).getId(), db.getEvent(1).getId());
        db.addUserForEvent(db.getUserRegister(2).getId(), db.getEvent(1).getId());
        db.addUserForEvent(db.getUserRegister(1).getId(), db.getEvent(3).getId());*/


        register.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        Intent intent = new Intent(MainActivity.this, RegisterAccountActivity.class);
                        startActivity(intent);
                    }
                });

        btnGoogleLogin.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        Intent intent = new Intent(MainActivity.this, SignInActivity.class);
                        startActivity(intent);
                    }
                });


        btnLogin.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        List<RegisterUser> allUsers = db.getAllUsers();
                        if (allUsers != null){
                            RegisterUser selectedUser = null;
                            boolean loginSucceed = false;
                            for (RegisterUser r : allUsers) {
                                HashedPassword hashedPassword = HashedPassword.create(password.getText().toString());
                                String resultPassword = hashedPassword.toString();
                                String usernameEmail = username.getText().toString();
                                if (r.getEmail().equals(usernameEmail.toLowerCase()) && r.getpassword().equals(resultPassword)){
                                    selectedUser = new RegisterUser(r.getId(), r.getFirstName(), r.getLastName(), r.getEmail(), resultPassword);
                                    loginSucceed = true;
                                    break;
                                }
                            }
                            if (selectedUser == null){
                                Dialog d = new Dialog("", "Your password or username is incorrect", MainActivity.this);
                                d.dialogOK();
                            }
                            if (selectedUser != null){
                                long id = selectedUser.getId();
                                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                                intent.putExtra("user", selectedUser.getId());
                                startActivity(intent);
                            }
                        }
                    }
                });
    }

    /*@Override
    public void initializeFBSdk() {
        //init the callback manager
        callbackManager = CallbackManager.Factory.create();
        btnFBLogin.registerCallback(callbackManager, fbLoginCallback);
        LoginManager.getInstance().registerCallback(callbackManager, fbLoginCallback);
    }

    @Override
    public void initializeView() {
        btnFBLogin = (LoginButton) findViewById(R.id.fbButton);
        btnLoginFBLoginManager = (Button) findViewById(R.id.loginButton);
        btnLoginFBLoginManager.setOnClickListener(this);
    }

    @Override
    public void showFriendsList() {
        //Intent intent = new Intent(this, FriendsListActivity.class);
        //startActivity(intent);
    }

    @Override
    public void showFBLoginResult(AccessToken fbAccessToken) {
    }

    @Override
    public void loginUsingFBManager() {
        //"user_friends" this will return only the common friends using this app
        LoginManager.getInstance().logInWithReadPermissions(MainActivity.this,
                Arrays.asList("public_profile", "user_friends", "email"));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //to pass Results to your facebook callbackManager
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View v) {
        mainPresenter.onLoginUsingFBManagerClicked();
    }


    private final FacebookCallback fbLoginCallback = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            mainPresenter.onFBLoginSuccess(loginResult);
        }

        @Override
        public void onCancel() {

        }

        @Override
        public void onError(FacebookException e) {

        }
    };*/
}
