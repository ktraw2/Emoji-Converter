package com.ksoft.emojiconverter;

import android.graphics.drawable.Drawable;
import android.util.StringBuilderPrinter;

/**
 * Created by Kevin on 8/15/2016.
 */
public class MappedEmoji {
    private String unicodeHexString;
    private String resName;

    public MappedEmoji(String codepoint, String resource)
    {
        unicodeHexString = codepoint;
        resName = resource;
    }

    public MappedEmoji(String codepoint, boolean convert0xtouUnderscore)
    {
        unicodeHexString = codepoint;
        if (convert0xtouUnderscore)
        {
            resName = "u_" + (codepoint).substring(2).toLowerCase();
            System.out.println(resName);
        }
        else
            resName = codepoint + ".png";
    }

    public String getUnicodeHexString()
    {
        return unicodeHexString;
    }

    public String getResName()
    {
        return resName;
    }
}
