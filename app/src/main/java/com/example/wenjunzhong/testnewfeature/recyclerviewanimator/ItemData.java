package com.example.wenjunzhong.testnewfeature.recyclerviewanimator;

/**
 * Created by wenjun.zhong on 2017/2/24.
 */

public class ItemData {
    public String content;
    public boolean isOpen;

    public ItemData(String content) {
        this.content = content;
        isOpen = false;
    }

    public ItemData(String content, boolean isOpen) {
        this.content = content;
        this.isOpen = isOpen;
    }
}
