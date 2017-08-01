package com.example.wenjunzhong.testnewfeature.utils;

import android.graphics.Bitmap;
import android.support.annotation.Nullable;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;

/**
 * need "compile 'com.google.zxing:core:3.2.1'" Created by wenjun.zhong on 2017/8/1.
 */

public final class QRCodeUtil {
    private QRCodeUtil() {}

    @Nullable
    public static Bitmap createQRCode(String contents, int width, int height) {
        try {
            BitMatrix matrix = new MultiFormatWriter().encode(contents, BarcodeFormat.QR_CODE, width, height);
            int matrixWidth = matrix.getWidth();
            int matrixHeight = matrix.getHeight();
            // 二维矩阵转为一维像素数组,也就是一直横着排了
            int[] pixels = new int[matrixWidth * matrixHeight];
            for (int y = 0; y < matrixHeight; y++) {
                for (int x = 0; x < matrixWidth; x++) {
                    if (matrix.get(x, y)) {
                        pixels[y * matrixWidth + x] = 0xff000000;
                    }
                }
            }
            Bitmap bitmap = Bitmap.createBitmap(matrixWidth, matrixHeight, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, matrixWidth, 0, 0, matrixWidth, matrixHeight);
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
