package wit.bytes.inventory.apiConfigs;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import wit.bytes.inventory.models.Location;
import wit.bytes.inventory.models.api_models.LocationGetterResponse;
import wit.bytes.inventory.models.api_models.LocationSenderResponse;

/**
 * Created by Md. Sifat-Ul Haque on 2/19/2017.
 */
public interface IApiTracker {

    @FormUrlEncoded
    @POST("locations")
    Call<LocationSenderResponse> sendEmployeeLocation(@Field("user_id") int employeeID,
                                                      @Field("latitude") double lat,
                                                      @Field("longitude") double lng,
                                                      @Field("time") String time,
                                                      @Field("timezone") String timeZone, @Query("access_token") String access_token);


    @GET("locations")
    Call<LocationGetterResponse> getLocations(@Query("access_token") String access_token);
}
