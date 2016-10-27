package teleporting.teleport.Factory;

import android.content.Context;

/**
 * Created by Roshini on 22-10-2016.
 */

public abstract class Factory {

    private static volatile Factory sInstance;

    public static Factory get() {
        return sInstance;
    }

    protected static void setInstance(final Factory factory) {
        sInstance = factory;
    }

    public abstract Context getApplicationContext();
    public abstract void onRequiredPermissionsAcquired();


}
