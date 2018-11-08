package modev.gosport.View;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import modev.gosport.Class.Dialog;
import modev.gosport.Class.RegisterUser;
import modev.gosport.R;
import modev.gosport.SqlLite.DBHandler;


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
                        RegisterAccountActivity.AsyncTaskRunner runner = new RegisterAccountActivity.AsyncTaskRunner();
                        runner.execute(txtFirstname.getText().toString(),txtLastname.getText().toString(), txtEmail.getText().toString(), txtPassword.getText().toString());
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
                Dialog d = new Dialog("", result, RegisterAccountActivity.this);
                d.dialogOK();
            }
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                DatabaseReference databaseArtist;
                databaseArtist = FirebaseDatabase.getInstance().getReference("user");

                String id = databaseArtist.push().getKey();

                RegisterUser user = new RegisterUser(strings[0], strings[1], strings[2], strings[3]);
                user.setId(id);
                databaseArtist.child(id).setValue(user);

                db.addUser(id);
                Intent intent = new Intent(RegisterAccountActivity.this, ListEventsActivity.class);
                startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
                resp = e.getMessage();
            }
            return resp;
        }

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(RegisterAccountActivity.this,
                    "logging in",
                    "Wait a moment");
        }
    }
}
