package modev.gosport.Class;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

public class Dialog {
    String title;
    String message;
    Context context;

    public Dialog(String title, String message, Context context){
        this.title = title;
        this.message = message;
        this.context = context;
    }

    public void dialogOK(){

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //do things
                    }
                })
                .setTitle(title);

        AlertDialog alert = builder.create();
        alert.show();
    }
}
