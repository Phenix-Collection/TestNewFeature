package com.example.wenjunzhong.testnewfeature;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

/**
 * Created by wenjun.zhong on 2016/4/14.
 */
public class TextInputlayoutActivity extends AppCompatActivity{

    private EditText userEdit;
    private boolean isShowPassword = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.textinput_activity);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            // toolbar.setPadding(0, getStatusBarHeight(), 0, 0);

            View statusBarView = findViewById(R.id.status_bar_view);
            statusBarView.getLayoutParams().height = getStatusBarHeight();

            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }
        userEdit = (EditText) findViewById(R.id.user_edit);

//        findViewById(R.id.password_button).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(isShowPassword){
//                    isShowPassword = false;
//                    userEdit.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
//                }else{
//                    isShowPassword = true;
//                    userEdit.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
//                }
//            }
//        });


        final TextInputLayout password = (TextInputLayout )findViewById(R.id.password);
//        password.setPasswordVisibilityToggleEnabled(true);
//        password.setPasswordVisibilityToggleDrawable(R.drawable.password_visibility_button);
        TextInputEditText editText = (TextInputEditText) findViewById(R.id.password_edit);
        password.setErrorEnabled(true);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() > 4){
                    password.setError("Password Error");
                }else{
                    password.setError("");
//                    password.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}
