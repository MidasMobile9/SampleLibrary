package com.test.samplelibrary.network;

public class NetworkDefineConstant {
    // Host 주소
    public static final String HOST_URL = "http://35.187.156.145";

    //요청 URL path
    public static String SERVER_URL_GET_DATA;
    public static String SERVER_URL_POST_DATA;
    public static String SERVER_URL_UPDATE_DATA;
    public static String SERVER_URL_DELETE_DATA;

    static {
        SERVER_URL_GET_DATA = HOST_URL + "/midastest/get.php";
        SERVER_URL_POST_DATA = HOST_URL + "/midastest/post.php";
        SERVER_URL_UPDATE_DATA = HOST_URL + "/midastest/put.php";
        SERVER_URL_DELETE_DATA = HOST_URL + "/midastest/delete.php";
    }
}
