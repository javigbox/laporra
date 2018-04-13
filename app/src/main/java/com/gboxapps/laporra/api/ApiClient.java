package com.gboxapps.laporra.api;

import android.content.Context;

import com.gboxapps.laporra.api.services.UserApiInterface;
import com.gboxapps.laporra.util.Consts;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import retrofit.RestAdapter;
import retrofit.client.OkClient;

/**
 * Factory class to create interfaces to access the GoU web services.
 * UserApiInterface
 * <p>The web services currently available are:</p>
 * <p>
 * <ul>
 * <li>{@link UserApiInterface}</li>
 * </ul>
 */
public class ApiClient {

  /*
    To reduce the extension of this class, it should be created using inheritance of interfaces but
    due to restrictions of the Retrofit library to work with interfaces that extends another interfaces,
    this class defines a method for each web service it exposes to the client classes.
   */

    private static final String LA_PORRA_SERVICE = "la_porra_services";


    private static RestAdapter laPorraRestAdapter;

    private static UserApiInterface userApiInterface;

    private ApiClient() {
    }

    /**
     * Create the REST adapter instance to adapt a Java interface to a REST API.
     *
     * @param context context to access the application resources
     * @return A {@link RestAdapter} adapter instance.
     */
    private static RestAdapter getRestAdapter(Context context, String service) {

        RestAdapter restAdapter = null;

        if (LA_PORRA_SERVICE.equals(service)) {

            if (laPorraRestAdapter == null) {
                laPorraRestAdapter = createRestAdapter(context, service);
            }
            restAdapter = laPorraRestAdapter;
        }
        return restAdapter;
    }

    private static RestAdapter createRestAdapter(Context context, String service) {

        String urlService = null;

        if (LA_PORRA_SERVICE.compareTo(service) == 0) {
            urlService = Consts.WS_BASE_URL;
        }


        OkHttpClient client = new OkHttpClient();
        client.setConnectTimeout(Consts.CONNECTION_TIME_OUT, TimeUnit.SECONDS);
        client.setReadTimeout(Consts.CONNECTION_TIME_OUT, TimeUnit.SECONDS);

        // Create network interceptor to fix EOFException when a empty body response with
        // Content-Encoding gzip is received (NXT-841). We consider a empty body when the server
        // send Content-Length to 0 so the only thing to do it's to replace Content-Encondig gzip
        // to identity.
        client.networkInterceptors().add(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Response originalResponse = chain.proceed(chain.request());
                String contentLengthHeader = originalResponse.header("Content-Length");
                if (contentLengthHeader != null && contentLengthHeader.equals("0")) {
                    return originalResponse.newBuilder()
                            .header("Content-Encoding", "identity")
                            .build();
                }
                return originalResponse;
            }
        });

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setClient(new OkClient(client))
                .setEndpoint(urlService)
                .build();

        return restAdapter;
    }

    /**
     * Return an implementation of the API defined by {@link UserApiInterface}
     *
     * @param context context to access the application resources
     * @return a web service interface implementation of <code>UserApiInterface</code>
     */
    public static UserApiInterface getUserServiceInterface(Context context) {
        if (userApiInterface == null) {
            userApiInterface = getRestAdapter(context, LA_PORRA_SERVICE).create(UserApiInterface.class);
        }
        return userApiInterface;
    }

}
