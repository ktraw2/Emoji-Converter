package com.ksoft.emojiconverter;

import android.graphics.drawable.Drawable;
import android.util.StringBuilderPrinter;

/**
 * Created by Kevin on 8/15/2016.
 */
public class MappedEmoji {
    private String unicodeHexString;
    private String resName;
    private boolean getFromDrawables;

    public MappedEmoji(String codepoint, boolean isBuiltIn)
    {
        unicodeHexString = codepoint;
        resName = "u_" + (codepoint).substring(2).toLowerCase();
        getFromDrawables = isBuiltIn;

    }

    public String getUnicodeHexString()
    {
        return unicodeHexString;
    }

    public String getResName()
    {
        return resName;
    }

    public boolean getGetFromDrawables () { return getFromDrawables; }
}
