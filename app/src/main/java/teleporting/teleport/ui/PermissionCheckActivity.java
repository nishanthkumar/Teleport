package teleporting.teleport.ui;

import android.annotation.TargetApi;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import teleporting.teleport.Factory.Factory;
import teleporting.teleport.R;
import teleporting.teleport.Utils.PermissionControl;

public class PermissionCheckActivity extends AppCompatActivity {

    private static final int REQUIRED_PERMISSIONS_REQUEST_CODE = 1;
    private static final long AUTOMATED_RESULT_THRESHOLD_MILLLIS = 250;
    private static final String PACKAGE_URI_PREFIX = "package:";
    private long mRequestTimeMillis;
    private TextView mNextView;
    private TextView mSettingsView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission_check);
        findViewById(R.id.exit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                finish();
            }
        });

        mNextView = (TextView) findViewById(R.id.next);
        mNextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                tryRequestPermission();
            }
        });

        mSettingsView = (TextView) findViewById(R.id.settings);
        mSettingsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                        Uri.parse(PACKAGE_URI_PREFIX + getPackageName()));
                startActivity(intent);
            }
        });
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
                    mNextView.setVisibility(View.GONE);

                    mSettingsView.setVisibility(View.VISIBLE);
                    //findViewById(R.id.enable_permission_procedure).setVisibility(View.VISIBLE);
                }
            }
        }
    }
}
