package com.gboxapps.laporra.util;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by Javi on 25/03/2018.
 */

public class FontUtils {

    private static final String FONTS_PATH = "fonts/";

    private static final String FUTURA_ND_EXTRA_BOLD_IT = "FuturaCndBoldIt.ttf";

    private static Typeface futuraNdExtraBoldIt;

    public static Typeface getFuturaNdExtraBoldIt(Context context) {
        if (futuraNdExtraBoldIt == null) {
            futuraNdExtraBoldIt = Typeface.createFromAsset(context.getAssets(),
                    FONTS_PATH + FUTURA_ND_EXTRA_BOLD_IT);
        }
        return futuraNdExtraBoldIt;
    }

}
