package wit.bytes.inventory.models.api_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Md. Sifat-Ul Haque on 2/21/2017.
 */

public class BaseResponse implements Serializable{

    @SerializedName("success")
    @Expose
    public boolean success;

    @SerializedName("message")
    @Expose
    public String message;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
