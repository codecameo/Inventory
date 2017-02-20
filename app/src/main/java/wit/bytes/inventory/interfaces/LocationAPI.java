package wit.bytes.inventory.interfaces;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;
import wit.bytes.inventory.models.Location;

/**
 * Created by user on 2/19/2017.
 */

public interface LocationAPI {
    @GET("/showcasing/locations.json")
    Call<List<Location>> getLocations();

    @GET("/showcasing/locations.json")
    public void getLocations(Callback<List<Location>> response);


}
