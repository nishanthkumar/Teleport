package teleporting.teleport;

import android.content.Context;
import android.content.Intent;

import teleporting.teleport.Factory.Factory;

/**
 * Created by Roshini on 22-10-2016.
 */

public class FactoryImpl extends Factory {

    private Context mApplicationContext;
    private TeleportApp mApplication;

    public static Factory register(final Context applicationContext, TeleportApp application) {


        final FactoryImpl factory = new FactoryImpl();
        Factory.setInstance(factory);

        // At this point Factory is published. Services can now get initialized and depend on
        // Factory.get().
        factory.mApplication = application;
        factory.mApplicationContext = applicationContext;

        return factory;
    }

    @Override
    public Context getApplicationContext() {
        return mApplicationContext;
    }

    @Override
    public void onRequiredPermissionsAcquired() {
        //TODO
        Intent intent = new Intent(getApplicationContext(), Teleporter.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getApplicationContext().startActivity(intent);
    }


}
