package mitrais.com.clinicapp.rest.services;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by mtmac20 on 3/3/17.
 */

public class WebServices {
    private Retrofit retrofit;
    private  ClinicApiServices services;

    public WebServices() {
        retrofit = new Retrofit.Builder()
                .baseUrl("http://172.19.11.170:8080/clinic/web/v1/")
//                .baseUrl("http://172.19.11.20:8080/clinic/web/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        services = retrofit.create(ClinicApiServices.class);
    }

    public ClinicApiServices getServices() {
        return services;
    }
}
