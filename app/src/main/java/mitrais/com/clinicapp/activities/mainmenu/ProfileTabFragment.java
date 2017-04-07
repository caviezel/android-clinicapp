package mitrais.com.clinicapp.activities.mainmenu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import mitrais.com.clinicapp.R;
import mitrais.com.clinicapp.activities.login.LoginActivity;
import mitrais.com.clinicapp.rest.models.CoreModel;
import mitrais.com.clinicapp.rest.models.LoginModel;

public class ProfileTabFragment extends Fragment {
    public ProfileTabFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile_tab, container, false);
        LoginModel loginModel = CoreModel.getInstance().getLoginModel();
        if (null != loginModel) {
            TextView txtName = (TextView) rootView.findViewById(R.id.txt_name);
            txtName.setText(loginModel.Credentials.Name);

            TextView txtRole = (TextView) rootView.findViewById(R.id.txt_role);
            txtRole.setText(loginModel.Credentials.Role);
        }

        Button signOutBtn = (Button) rootView.findViewById(R.id.btn_sign_out);
        signOutBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(getActivity().getApplicationContext(),LoginActivity.class);
                        getActivity().startActivity(i);
                    }
                }
        );

        return rootView;
    }
}
