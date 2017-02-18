package wit.bytes.inventory.models;

/**
 * Created by Md. Sifat-Ul Haque on 2/18/2017.
 */

public class Target {

    private String mProductName, mPackageName;
    private float mTarget, mAchieve;

    public String getProductName() {
        return mProductName;
    }

    public void setProductName(String mProductName) {
        this.mProductName = mProductName;
    }

    public String getPackageName() {
        return mPackageName;
    }

    public void setPackageName(String mPackageName) {
        this.mPackageName = mPackageName;
    }

    public float getTarget() {
        return mTarget;
    }

    public void setTarget(float mTarget) {
        this.mTarget = mTarget;
    }

    public float getAchieve() {
        return mAchieve;
    }

    public void setAchieve(float mAchieve) {
        this.mAchieve = mAchieve;
    }
}
