package com.yunzhou.common.utils;
/*
 *  @项目名：  TDInformation
 *  @包名：    com.yunzhou.tdinformation.utils
 *  @文件名:   IoUtil
 *  @创建者:   lz
 *  @创建时间:  2018/9/20 15:04
 *  @描述：
 */

import java.io.Closeable;
import java.io.IOException;

public final class IoUtil {
    private IoUtil() {
    }
    public static void close(Closeable closeable){
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
