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

/**
 * Created by Md. Sifat-Ul Haque on 2/19/2017.
 */
public interface IApiTracker {

    @FormUrlEncoded
    @POST("/employee_location")
    Call<String> sendEmployeeLocation(@Field("employee_id") int employeeID,
                                @Field("latitude") double lat,
                                @Field("longitude") double lng,
                                @Field("time") String time,
                                @Field("time_zone") String timeZone);


    @GET("locations.json")
    Call<List<Location>> getLocations();
}
