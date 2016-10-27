package teleporting.teleport.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import teleporting.teleport.Factory.Factory;
import teleporting.teleport.ui.PermissionCheckActivity;

/**
 * Created by Roshini on 22-10-2016.
 */

public class UiUtils {

    /**
     * Check if the activity needs to be redirected to permission check
     *
     * @return true if {@link Activity#finish()} was called because redirection was performed
     */
    public static boolean redirectToPermissionCheckIfNeeded(final Activity activity) {
        if (!PermissionControl.hasRequiredPermissions()) {
            final Context context = Factory.get().getApplicationContext();
            final Intent intent = new Intent(context, PermissionCheckActivity.class);
            context.startActivity(intent);
        } else {
            // No redirect performed
            return false;
        }
        // Redirect performed
        activity.finish();
        return true;
    }
}
