package com.example.wenjunzhong.testnewfeature;

import com.example.wenjunzhong.testnewfeature.annotation.NotProguard;

/**
 * Created by wenjun.zhong on 2017/7/11.
 */

@NotProguard
public class TestNotProguard {

    @NotProguard
    private int test1;


    @NotProguard
    private void setTest() {}
}
