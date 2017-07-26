package eric.applock;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
*
 * Created by wenjun.zhong on 2017/7/24.
 */

public class AppLockActivity extends AppCompatActivity implements View.OnClickListener{

    private String packageName;
    private Intent intent;
    private boolean isUnLock = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_lock_layout);
        findViewById(R.id.unlock_button).setOnClickListener(this);
        Intent intent1 = getIntent();
        assert intent1 != null;
        packageName = intent1.getStringExtra("packageName");
        intent = intent1.getParcelableExtra("intentStr");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.unlock_button:
                isUnLock = true;
                NeedAppLock.unLockState(packageName);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                this.finish();
                break;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        this.finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(!isUnLock){
          //  killAppByPackageName(packageName);
        }
    }

    private void killAppByPackageName(String packageName){
        PackageManager pm = getApplicationContext().getPackageManager();
        pm.setApplicationEnabledSetting(packageName, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, 0);
        pm.setApplicationEnabledSetting(packageName,PackageManager.COMPONENT_ENABLED_STATE_ENABLED, 0);
    }
}
