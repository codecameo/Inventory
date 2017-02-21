package wit.bytes.inventory.models.api_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Md. Sifat-Ul Haque on 2/21/2017.
 */

public class LoginResponseModel extends BaseResponse implements Serializable{

    @SerializedName("data")
    @Expose
    private LoginData data;

    public LoginData getData() {
        return data;
    }

    public void setData(LoginData data) {
        this.data = data;
    }

    public class LoginData implements Serializable{

        @SerializedName("user_id")
        @Expose
        private int user_id;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("access_token")
        @Expose
        private String access_token;

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAccess_token() {
            return access_token;
        }

        public void setAccess_token(String access_token) {
            this.access_token = access_token;
        }
    }

}
