package mitrais.com.clinicapp.activities.login;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import mitrais.com.clinicapp.R;
import mitrais.com.clinicapp.activities.mainmenu.MenuTabActivity;
import mitrais.com.clinicapp.rest.models.CoreModel;
import mitrais.com.clinicapp.rest.models.LoginModel;
import mitrais.com.clinicapp.rest.services.WebServices;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    EditText tfUsername;
    EditText tfPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initUIComponents();
    }

    private void initUIComponents() {
        tfUsername = (EditText) findViewById(R.id.tf_user_name);
        tfPassword = (EditText) findViewById(R.id.tf_password);
        Button signInBtn = (Button) findViewById(R.id.btn_sign_in);
        signInBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        doLogin();
                    }
                }
        );
    }

    private void doLogin() {
        final ProgressDialog progress = new ProgressDialog(this);
        progress.setTitle("Loading");
        progress.setMessage("Logging in...");
        progress.setCancelable(false);
        progress.show();

        WebServices services = new WebServices();
        Call<LoginModel> call = services.getServices().doLogin(tfUsername.getText().toString(), tfPassword.getText().toString());
        call.enqueue(new Callback<LoginModel>() {
                         @Override
                         public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                             Log.i("login response succcess", response.body().toString());
                             progress.dismiss();

                             if (response.body().Status) {
                                 CoreModel.getInstance().setLoginModel(response.body());

                                 Intent i = new Intent(getApplicationContext(),MenuTabActivity.class);
                                 startActivity(i);
                             }
                         }

                         @Override
                         public void onFailure(Call<LoginModel> call, Throwable t) {
                             Log.i("login response fail", t.getMessage());

                             progress.dismiss();

                             Context context = getApplicationContext();
                             CharSequence text = "Failed to login, try again";
                             int duration = Toast.LENGTH_SHORT;

                             Toast toast = Toast.makeText(context, text, duration);
                             toast.show();
                         }
                     }
        );
    }
}
