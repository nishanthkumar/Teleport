package teleporting.teleport;

import android.annotation.TargetApi;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import teleporting.teleport.Factory.Factory;
import teleporting.teleport.Utils.PermissionControl;
import teleporting.teleport.ui.LauncherFragment;

public class Teleporter extends AppCompatActivity implements LauncherFragment.OnLauncherFragmentInteractionListener {

    private static final int REQUIRED_PERMISSIONS_REQUEST_CODE = 1;
    private static final long AUTOMATED_RESULT_THRESHOLD_MILLLIS = 250;
    private static final String PACKAGE_URI_PREFIX = "package:";
    private long mRequestTimeMillis;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!PermissionControl.hasRequiredPermissions()) {
            tryRequestPermission();
            return;
        }
        loadUI();

    }

    private void loadUI() {
        setContentView(R.layout.activity_teleporter);
        Fragment fragment = null;
        fragment = LauncherFragment.getInstance();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.frame_container, fragment).commit();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null) {
            setIntent(intent);
        }
    }

    @TargetApi(23)
    private void tryRequestPermission() {
        final String[] missingPermissions = PermissionControl.getMissingRequiredPermissions();
        if (missingPermissions.length == 0) {
            return;
        }

        mRequestTimeMillis = SystemClock.elapsedRealtime();
        requestPermissions(missingPermissions, REQUIRED_PERMISSIONS_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(
            final int requestCode, final String permissions[], final int[] grantResults) {
        if (requestCode == REQUIRED_PERMISSIONS_REQUEST_CODE) {
            // We do not use grantResults as some of the granted permissions might have been
            // revoked while the permissions dialog box was being shown for the missing permissions.
            if (PermissionControl.hasRequiredPermissions()) {
                Factory.get().onRequiredPermissionsAcquired();
            } else {
                final long currentTimeMillis = SystemClock.elapsedRealtime();
                // If the permission request completes very quickly, it must be because the system
                // automatically denied. This can happen if the user had previously denied it
                // and checked the "Never ask again" check box.
                if ((currentTimeMillis - mRequestTimeMillis) < AUTOMATED_RESULT_THRESHOLD_MILLLIS) {
                    /*mNextView.setVisibility(View.GONE);

                    mSettingsView.setVisibility(View.VISIBLE);*/
                    //findViewById(R.id.enable_permission_procedure).setVisibility(View.VISIBLE);
                }
            }
        }
    }



    @Override
    public void onLauncherFragmentInteraction(Uri uri) {

    }
}
