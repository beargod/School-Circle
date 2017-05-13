package com.example.administrator.school_circle.util;

/**
 * Created by BigGod on 2017-05-11.
 */
public class FinalDataController {
    private static long userId;

    public static Long getUserId() {
        if(userId==0)
            userId=1;
        return userId;
    }

    public static void setUserId(long userId) {
        FinalDataController.userId = userId;
    }
}
