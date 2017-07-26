package com.example.wenjunzhong.testnewfeature;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wenjun.zhong on 2016/4/14.
 */
public class TextInputlayoutActivity extends AppCompatActivity implements View.OnClickListener{

    @BindView(R.id.status_bar_view)
    View statusBarView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.user)
    TextInputLayout user;
    @BindView(R.id.password_button)
    Button passwordButton;
    @BindView(R.id.password_edit)
    TextInputEditText passwordEdit;
    @BindView(R.id.password)
    TextInputLayout password;
    @BindView(R.id.custom_edit_layout_img)
    ImageView customEditLayoutImg;
    @BindView(R.id.custom_edit_layout_edit)
    EditText customEditLayoutEdit;
    @BindView(R.id.user_edit)
    EditText userEdit;
    private boolean isShowPassword = false;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.textinput_activity);
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            // toolbar.setPadding(0, getStatusBarHeight(), 0, 0);

            View statusBarView = findViewById(R.id.status_bar_view);
            statusBarView.getLayoutParams().height = getStatusBarHeight();

            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }



        final TextInputLayout password = (TextInputLayout) findViewById(R.id.password);
        // password.setPasswordVisibilityToggleEnabled(true);
        // password.setPasswordVisibilityToggleDrawable(R.drawable.password_visibility_button);
        TextInputEditText editText = (TextInputEditText) findViewById(R.id.password_edit);
        password.setErrorEnabled(true);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 4) {
                    password.setError("Password Error");
                } else {
                    password.setError("");
                    // password.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        ButterKnife.bind(this);
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }


    @OnClick(R.id.password_button)
    @Override
    public void onClick(View v) {

    }
}
