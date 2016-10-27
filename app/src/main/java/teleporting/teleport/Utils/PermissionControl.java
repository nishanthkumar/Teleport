package teleporting.teleport.Utils;

/**
 * Created by Roshini on 22-10-2016.
 */

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;

import java.util.ArrayList;
import java.util.Hashtable;

import teleporting.teleport.Factory.Factory;
//import android.support.v4.os.BuildCompat;



/**
 * Android OS version utilities
 *
 */

public class PermissionControl {

    private static boolean sIsAtLeastM;
    private static boolean sIsAtLeastN;

    static {
        final int v = getApiVersion();
        sIsAtLeastM = v >= android.os.Build.VERSION_CODES.M;
      //  sIsAtLeastN = BuildCompat.isAtLeastN();
    }

    /**
     * @return True if the version of Android that we're running on is at least M
     * (API level 23).
     */
    public static boolean isAtLeastM() {
        return sIsAtLeastM;
    }

    /**
     * @return True if the version of Android that we're running on is at least N
     * (API level 24).
     */
    public static boolean isAtLeastN() {
        return sIsAtLeastN;
    }

    /**
     * @return The Android API version of the OS that we're currently running on.
     */
    public static int getApiVersion() {
        return android.os.Build.VERSION.SDK_INT;
    }

    private static Hashtable<String, Integer> sPermissions = new Hashtable<String, Integer>();


    /**
     * Check if the app has the specified permission. Note that if it
     * returns true, it cannot return false in the same process as the OS kills the process when
     * any permission is revoked.
     *
     * @param permission A permission from {@link android.Manifest.permission}
     */
    @TargetApi(23)
    public static boolean hasPermission(final String permission) {
        if (isAtLeastM()) {
            // It is safe to cache the PERMISSION_GRANTED result as the process gets killed if the
            // user revokes the permission setting. However, PERMISSION_DENIED should not be
            // cached as the process does not get killed if the user enables the permission setting.
            if (!sPermissions.containsKey(permission)
                    || sPermissions.get(permission) == PackageManager.PERMISSION_DENIED) {
                final Context context = Factory.get().getApplicationContext();

                final int permissionState = context.checkSelfPermission(permission);
                sPermissions.put(permission, permissionState);
            }
            return sPermissions.get(permission) == PackageManager.PERMISSION_GRANTED;
        } else {
            return true;
        }
    }

    /**
     * Does the app have all the specified permissions
     */
    public static boolean hasPermissions(final String[] permissions) {
        for (final String permission : permissions) {
            if (!hasPermission(permission)) {
                return false;
            }
        }
        return true;
    }

    public static boolean hasPhonePermission() {
        return hasPermission(Manifest.permission.READ_PHONE_STATE);
    }

    public static boolean hasSmsPermission() {
        return hasPermission(Manifest.permission.READ_SMS);
    }

    public static boolean hasLocationPermission() {
        return hasPermission(Manifest.permission.ACCESS_FINE_LOCATION);
    }


    public static boolean hasStoragePermission() {
        // Note that READ_EXTERNAL_STORAGE and WRITE_EXTERNAL_STORAGE are granted or denied
        // together.
        return hasPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
    }

    public static boolean hasRecordAudioPermission() {
        return hasPermission(Manifest.permission.RECORD_AUDIO);
    }

    /**
     * Returns array with the set of permissions that have not been granted from the given set.
     * The array will be empty if the app has all of the specified permissions. Note that calling
     * permissions for an already granted permission can prompt the user
     * again, and its up to the app to only request permissions that are missing.
     */
    public static String[] getMissingPermissions(final String[] permissions) {
        final ArrayList<String> missingList = new ArrayList<String>();
        for (final String permission : permissions) {
            if (!hasPermission(permission)) {
                missingList.add(permission);
            }
        }

        final String[] missingArray = new String[missingList.size()];
        missingList.toArray(missingArray);
        return missingArray;
    }

    private static String[] sRequiredPermissions = new String[]{
            // Required to read current Location status
            Manifest.permission.ACCESS_COARSE_LOCATION,
            // Required to read current Location status
            Manifest.permission.ACCESS_FINE_LOCATION,
            // Required for knowing the phone number, number of SIMs, etc.
            Manifest.permission.READ_PHONE_STATE,
            // This is not strictly required, but simplifies the contact picker scenarios
            Manifest.permission.READ_CONTACTS,
            // Required to read Camera permission
            Manifest.permission.CAMERA,
    };

    /**
     * Does the app have the minimum set of permissions required to operate.
     */
    public static boolean hasRequiredPermissions() {
        return hasPermissions(sRequiredPermissions);
    }

    public static String[] getMissingRequiredPermissions() {
        return getMissingPermissions(sRequiredPermissions);
    }
}
