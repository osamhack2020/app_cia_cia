package com.dygames.cia;

import android.content.res.Resources;

public class Util {

    public static String[] categorys;
    public static String userHSID = "";

    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public static int pxToDp(int px) {
        return (int) (px / Resources.getSystem().getDisplayMetrics().density);
    }

}
