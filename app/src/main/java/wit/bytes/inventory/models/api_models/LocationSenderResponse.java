package wit.bytes.inventory.models.api_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Md. Sifat-Ul Haque on 2/21/2017.
 */

public class LocationSenderResponse extends BaseResponse {

    @SerializedName("data")
    @Expose
    private LocationSenderData data;

    public class LocationSenderData {

        @SerializedName("location_id")
        @Expose
        int location_id;

        public int getLocation_id() {
            return location_id;
        }

        public void setLocation_id(int location_id) {
            this.location_id = location_id;
        }
    }
}
