package wit.bytes.inventory.obserables;

import java.util.Observable;

/**
 * Created by Md. Sifat-Ul Haque on 2/20/2017.
 */

public class LocationUpdateObservable extends Observable {

    private static LocationUpdateObservable instance = new LocationUpdateObservable();

    public static LocationUpdateObservable getInstance() {
        return instance;
    }

    LocationUpdateObservable() {
    }

    public void updateValue(Object data) {
        synchronized (this) {
            setChanged();
            notifyObservers(data);
        }
    }

}
