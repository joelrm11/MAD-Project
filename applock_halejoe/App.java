package com.example.applock_halejoe;
import com.example.applock_halejoe.LockManager;
import android.app.Application;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        LockManager.getInstance().enableAppLock(this);
    }

}
