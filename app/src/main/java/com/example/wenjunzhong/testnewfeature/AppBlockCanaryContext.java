package com.example.wenjunzhong.testnewfeature;

import com.github.moduth.blockcanary.BlockCanaryContext;

/**
 * Created by wenjun.zhong on 2017/2/13.
 */

public class AppBlockCanaryContext extends BlockCanaryContext {
    @Override
    public int getConfigBlockThreshold() {
        return 500;
    }

    @Override
    public boolean isNeedDisplay() {
        return true;
    }

    @Override
    public String getLogPath() {
        return "/blockcanary/performance";
    }
}
