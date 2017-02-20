package wit.bytes.inventory.interfaces;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by user on 2/19/2017.
 */

public interface LocationAPI {
    @GET("locations.json")
    Call<String> getLocations();
}
