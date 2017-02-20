package wit.bytes.inventory.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import wit.bytes.inventory.R;


/**
 * Created by Md. Sifat-Ul Haque on 1/17/2017.
 */
public class PermissionHandler {

    private static final int REQUEST_SETTINGS = 444;

    // permission request code for location
    public static final int REQUEST_BOTH_LOCATION_PERMISSION = 331;
    public static final int REQUEST_COARSE_LOCATION = 332;
    public static final int REQUEST_FINE_LOCATION = 333;

    public static boolean checkPermissions(Context context, String... permissions) {
        for (String permission : permissions) {
            if (!checkPermission(context, permission)) {
                return false;
            }
        }
        return true;
    }

    private static boolean checkPermission(Context context, String permission) {
        return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
    }


    //=== request permission set for location ===//
    public static void requestPermissions(Object o, int permissionId, String message, String... permissions) {
        if (o instanceof Activity) {

            boolean showSnackBar = true;
            for (String permission : permissions) {
                showSnackBar = showSnackBar & ActivityCompat.shouldShowRequestPermissionRationale((AppCompatActivity) o, permission);
            }
            if (showSnackBar) {
                showSettingsSnackbar(message, (Activity) o);
            } else {
                ActivityCompat.requestPermissions((AppCompatActivity) o, permissions, permissionId);
            }
        }
    }

    public static void requestPermissions(Object o, int permissionId, String message, String permission) {
        if (o instanceof Activity) {
            if (ActivityCompat.shouldShowRequestPermissionRationale((AppCompatActivity) o, permission)) {
                showSettingsSnackbar(message, (Activity) o);
            } else {
                ActivityCompat.requestPermissions((AppCompatActivity) o, new String[]{permission}, permissionId);
            }
        }
    }

    public static boolean hasCameraPermission(Context context) {
        return checkPermission(context, Manifest.permission.CAMERA);
    }

    public static boolean hasExternalStorageWritePermission(Context context) {
        return checkPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    public static boolean hasExternalStorageReadPermission(Context context) {
        return checkPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE);
    }

    public static boolean hasRecordAudioPermission(Context context) {
        return checkPermission(context, Manifest.permission.RECORD_AUDIO);
    }

    public static boolean hasContactReadPermission(Context context) {
        return checkPermission(context, Manifest.permission.READ_CONTACTS);
    }

    public static boolean hasContactWritePermission(Context context) {
        return checkPermission(context, Manifest.permission.WRITE_CONTACTS);
    }

    public static boolean hasCoarseLocationPermission(Context context) {
        return checkPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION);
    }

    public static boolean hasFineLocationPermission(Context context) {
        return checkPermission(context, Manifest.permission.ACCESS_FINE_LOCATION);
    }

    private static void showSettingsSnackbar(String message, final Activity activity) {
        if (activity != null) {
            View view = activity.findViewById(android.R.id.content);
            Snackbar.make(view, message, Snackbar.LENGTH_LONG)
                    .setAction(activity.getString(R.string.settings), new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent settingsIntent = new Intent();
                            settingsIntent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            settingsIntent.addCategory(Intent.CATEGORY_DEFAULT);
                            settingsIntent.setData(Uri.parse("package:" + activity.getPackageName()));
                            settingsIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            settingsIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                            settingsIntent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                            activity.startActivityForResult(settingsIntent, REQUEST_SETTINGS);
                        }
                    })
                    .setActionTextColor(ContextCompat.getColor(activity, R.color.colorPrimary))
                    .show();
        }
    }
}


