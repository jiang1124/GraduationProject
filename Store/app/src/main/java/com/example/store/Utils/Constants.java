package com.example.store.Utils;

import android.os.Environment;

/**
 * Created by 79124 on 2019/5/10.
 */

public abstract class Constants {
    // 相册的RequestCode
    public static final int INTENT_REQUEST_CODE_ALBUM = 0;
    // 照相的RequestCode
    public static final int INTENT_REQUEST_CODE_CAMERA = 1;
    // 加载情况分页参数
    public static final int PARAM_PAGENO = 1;
    public static final int PARAM_PAGESIZE = 10;
    // 是否清理缓存
    public static boolean cleanCache = true;
    //本地缓存目录
    public static final String IMEIBI_PRO_ROOT_PATH;
    static {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            IMEIBI_PRO_ROOT_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Imeibi_Iapp/";
        } else {
            IMEIBI_PRO_ROOT_PATH = Environment.getRootDirectory().getAbsolutePath() + "/Imeibi_Iapp/";
        }
    }
    //图片目录
    public static final String IMAGE_PATH = IMEIBI_PRO_ROOT_PATH + "/images";
    //图片缓存目录
    public static final String CACHE_IMAGE_PATH = IMEIBI_PRO_ROOT_PATH + "/pic";
    //待上传图片缓存目录
    public static final String CACHE_UPLOADING_IMGE_PATH = IMEIBI_PRO_ROOT_PATH + "/uploading_img";
    //数据库根目录
    public static final String SYS_DATABASES_PATH = IMEIBI_PRO_ROOT_PATH + "databases/";
    //APK下载路径
    public static final String APK_DOWNLOAD_PATH = IMEIBI_PRO_ROOT_PATH + "download_apk/";

}
