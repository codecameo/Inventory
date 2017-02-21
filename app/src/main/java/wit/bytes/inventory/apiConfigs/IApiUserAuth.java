package wit.bytes.inventory.apiConfigs;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import wit.bytes.inventory.models.api_models.LoginResponseModel;

/**
 * Created by Md. Sifat-Ul Haque on 2/21/2017.
 */

public interface IApiUserAuth {

    @FormUrlEncoded
    @POST("auth/login")
    Call<LoginResponseModel> login(@Field("username") String username,
                           @Field("password") String password);
}
