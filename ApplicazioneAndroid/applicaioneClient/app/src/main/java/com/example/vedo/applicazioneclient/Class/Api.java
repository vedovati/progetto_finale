package com.example.vedo.applicazioneclient.Class;

import java.lang.reflect.Field;

public class Api {
    public static final String API_URL = "http://zoomed.altervista.org/API.php";
    public static final String AI_URL = "10.10.21.29:8081";

    public static int getResId(String resName, Class<?> c) {
        try {
            Field idField = c.getDeclaredField(resName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}
