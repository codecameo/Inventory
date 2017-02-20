package wit.bytes.inventory.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by user on 2/19/2017.
 */

public class Location implements Serializable{

    @SerializedName("employee_id")
    @Expose
    private String employeeId;
    @SerializedName("employee_name")
    @Expose
    private String employeeName;
    @SerializedName("mobile_number")
    @Expose
    private String mobileNumber;
    @SerializedName("lat")
    @Expose
    private float lat;
    @SerializedName("lng")
    @Expose
    private float lng;
    @SerializedName("time")
    @Expose
    private String time;

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public float getLng() {
        return lng;
    }

    public void setLng(float lng) {
        this.lng = lng;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}
