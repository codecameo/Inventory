package wit.bytes.inventory.models.api_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import wit.bytes.inventory.models.Location;

/**
 * Created by Md. Sifat-Ul Haque on 2/21/2017.
 */
public class LocationGetterResponse extends BaseResponse {

    @SerializedName("data")
    @Expose
    private ArrayList<Location> data;

    public ArrayList<Location> getData() {
        return data;
    }

    public void setData(ArrayList<Location> data) {
        this.data = data;
    }
}
