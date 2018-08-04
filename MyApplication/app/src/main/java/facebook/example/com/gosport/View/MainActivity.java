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
    private Button btnLogin;
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

        /*db.addEvent(new EventInformation("TestName1","Hasselt", "bkababaj", 2018, 7, 24, 13, 14  ));
        db.addEvent(new EventInformation("TestName2","Hasselt", "bkababaj", 2018, 7, 24, 13, 14  ));
        db.addEvent(new EventInformation("TestName3","Hasselt", "bkababaj", 2018, 7, 24, 13, 14  ));
        db.addEvent(new EventInformation("TestName4","Hasselt", "bkababaj", 2018, 7, 24, 13, 14  ));
        db.addUser(new RegisterUser("Michiel", "Smeets", "michiel_smeets2@hotmail.com", "test"));
        db.addUser(new RegisterUser("Stef", "Smeets", "stef_smeets2@hotmail.com", "test"));
        db.addUser(new RegisterUser("test", "test", "test@hotmail.com", "test"));
        db.addUserForEvent(db.getUserRegister(1).getId(), db.getEvent(1).getId());
        db.addUserForEvent(db.getUserRegister(2).getId(), db.getEvent(1).getId());
        db.addUserForEvent(db.getUserRegister(1).getId(), db.getEvent(3).getId());*/


        register.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        Intent intent = new Intent(MainActivity.this, RegisterAccount.class);
                        startActivity(intent);
                    }
                });
        //mainPresenter = new MainPresenter(this);


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
                                Intent intent = new Intent(MainActivity.this, Home.class);
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
