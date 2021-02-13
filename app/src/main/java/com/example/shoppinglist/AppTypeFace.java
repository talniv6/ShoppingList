package com.example.shoppinglist;

import android.content.Context;
import android.graphics.Typeface;

import androidx.core.content.res.ResourcesCompat;

public class AppTypeFace {

    public static Typeface get(Context context, String user){
        int font = R.font.dana_yad;
        switch (user) {
            case "טל":
                font = R.font.tal_hand;
                break;
            case "שיר":
                font = R.font.shir_hand;
                break;
        }

        return ResourcesCompat.getFont(context, font);
    }

}
