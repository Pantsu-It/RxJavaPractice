package main;

public class Logger {

    public static final String TAG = "TAG";

    public static void e(String TAG, String log) {
        System.out.println(TAG + " " + log);
    }
}
