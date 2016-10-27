package teleporting.teleport;

import android.app.Application;
import android.os.Trace;

/**
 * Created by Roshini on 22-10-2016.
 */

public class TeleportApp extends Application {

    @Override
    public void onCreate() {
        Trace.beginSection("app.onCreate");
        super.onCreate();
        FactoryImpl.register(getApplicationContext(), this);
    }
}
