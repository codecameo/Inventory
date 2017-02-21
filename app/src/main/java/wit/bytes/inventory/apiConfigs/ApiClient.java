package wit.bytes.inventory.apiConfigs;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Md. Sifat-Ul Haque on 2/19/2017.
 */

public class ApiClient {

    public static final String BASE_URL = "http://82ca037c.ngrok.io/witbytes_product/public/";
    private static final String API_VERSION = "api/v1/";
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL+API_VERSION)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}