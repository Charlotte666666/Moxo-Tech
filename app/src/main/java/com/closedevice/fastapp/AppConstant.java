package com.closedevice.fastapp;

import android.os.Environment;

import java.io.File;


public class AppConstant {

    //DATA STORE
    public static final String DATA_PATH = AppContext.getInstance().getCacheDir().getAbsolutePath() + File.separator + "data";
    public static final String SDCARD_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "fastapp";
    public static final String LOG_PATH = SDCARD_PATH + File.separator + "log" + File.separator;
    public static final String NET_DATA_PATH = DATA_PATH + File.separator + "net_cache";
    public static final String UPDATE_FILE_PATH = SDCARD_PATH + File.separator + "update";


    //SP CONFIG
    public static final String KEY_MODE_NO_IMAGE = "no image";
    public static final String KEY_MODE_AUTO_CACHE = "auto cache";


    public static final String API_GAN_URL = "http://gank.io/api/";

    public static final String API_FIR_URL = "http://api.fir.im";
    public static final String TOKEN_FIR = "9e935900ce4683c056f4c59a67f92e7a";
    public static final String KEY_APP_ID = "58023a58959d690fff000aee";

    public static final String API_BK_URL = "http://192.168.191.1:8080/MoxoClass/";
    public static final String UPLOAD_URL = "http://192.168.191.1:8080/MoxoClass/UploadServlet";

    public static final String SERVER_IP = "192.168.191.1";
    public static final int SERVER_PORT = 2018;
}
