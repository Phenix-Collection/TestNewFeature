package com.example.wenjunzhong.testnewfeature.alarm;

/**
 * Created by wenjun.zhong on 2016/11/14.
 */

public class AlarmInfo {
    public long delayTime;
    public String action;

    public AlarmInfo(long delayTime, String action) {
        this.delayTime = delayTime;
        this.action = action;
    }

    @Override
    public String toString() {
        return "AlarmInfo{" + "delayTime=" + delayTime + ", action='" + action + '\'' + '}';
    }
}
