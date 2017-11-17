package eric.flashlight;

import android.app.Activity;
import android.app.Service;
import android.os.Looper;
import android.os.MessageQueue;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import eric.flashlight.utils.PreferenceUtil;

public class MainActivity extends Activity implements IFlashLightListener{

    FlashlightController mController;
    private Button mButton;
    private Vibrator mVibrator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mButton = (Button) findViewById(R.id.flash_light_switch);
        mVibrator = (Vibrator)getApplicationContext().getSystemService(Service.VIBRATOR_SERVICE);
        Looper.myQueue().addIdleHandler(new MessageQueue.IdleHandler() {
            @Override
            public boolean queueIdle() {
                initFlashLight();
                if(PreferenceUtil.isTurnOnDefault()){
                    mController.setFlashLight();
                }
                return false;
            }
        });


    }

    public void open(View view){
        FlashFrequency frequency = FlashFrequency.ALWAYS_BRIGHT;
        switch (view.getId()){
            case R.id.button_0:
                frequency = FlashFrequency.ALWAYS_BRIGHT;
                break;
            case R.id.button_1:
                frequency = FlashFrequency.FREQUENCY_1;
                break;
            case R.id.button_2:
                frequency = FlashFrequency.FREQUENCY_2;
                break;
            case R.id.button_3:
                frequency = FlashFrequency.FREQUENCY_3;
                break;
            case R.id.button_sos:
                frequency = FlashFrequency.FREQUENCY_SOS;
                break;
        }
        if(mController != null) {
            mController.changeFlashFrequency(frequency);
        }
    }

    public void onClickButton(View view){

    }

    public void onClickFlash(View view){
        initFlashLight();
        mVibrator.vibrate(40);
        mController.setFlashLight();
    }

    private void initFlashLight(){
        if(mController == null){
            mController = new FlashlightController(MainActivity.this.getApplicationContext(), MainActivity.this, 5000);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mController != null){
            mController.destroy();
            mController = null;
        }
    }

    @Override
    public void flashLightStateChange(final boolean enable) {
     //   mTextView.setText(enable ? "turn on" : "turn off");

        runOnUiThread(new Runnable() {
         @Override
         public void run() {
            mButton.setBackgroundColor(enable ? ContextCompat.getColor(MainActivity.this.getApplicationContext(), android.R.color.holo_blue_bright) : 0);

         }
     });
    }

    @Override
    public void flashLightException(final int exceptionCode) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(FlashApplication.getAppContext(), exceptionCode, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
