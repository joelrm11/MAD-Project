package com.example.applock_halejoe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import com.example.applock_halejoe.AppLock;
import com.example.applock_halejoe.AppLockActivity;
import com.example.applock_halejoe.BaseActivity;
import com.example.applock_halejoe.LockManager;
import android.content.Intent;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = "HomePage";
    private Button btOnOff;
    private Button btChange;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btOnOff = (Button) findViewById(R.id.bt_on_off);
        btOnOff.setOnClickListener(this);

        btChange = (Button) findViewById(R.id.bt_change);
        btChange.setText(R.string.change_passcode);
        btChange.setOnClickListener(this);

        updateUI();
    }

    @Override
    public void onClick(View view) {
        if (view.equals(btOnOff)) {
            int type = LockManager.getInstance().getAppLock().isPasscodeSet() ? AppLock.DISABLE_PASSLOCK
                    : AppLock.ENABLE_PASSLOCK;
            Intent intent = new Intent(this, AppCompatActivity.class);
            intent.putExtra(AppLock.TYPE, type);
            startActivityForResult(intent, type);
        } else if (view.equals(btChange)) {
            Intent intent = new Intent(this, AppCompatActivity.class);
            intent.putExtra(AppLock.TYPE, AppLock.CHANGE_PASSWORD);
            intent.putExtra(AppLock.MESSAGE,
                    getString(R.string.enter_old_passcode));
            startActivityForResult(intent, AppLock.CHANGE_PASSWORD);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case AppLock.DISABLE_PASSLOCK:
                break;
            case AppLock.ENABLE_PASSLOCK:
            case AppLock.CHANGE_PASSWORD:
                if (resultCode == RESULT_OK) {
                    Toast.makeText(this, getString(R.string.setup_passcode),
                            Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
        updateUI();
    }

    private void updateUI() {
        if (LockManager.getInstance().getAppLock().isPasscodeSet()) {
            btOnOff.setText(R.string.disable_passcode);
            btChange.setEnabled(true);
        } else {
            btOnOff.setText(R.string.enable_passcode);
            btChange.setEnabled(false);
        }
    }

}
