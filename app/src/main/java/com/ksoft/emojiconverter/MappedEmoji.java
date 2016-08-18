package com.ksoft.emojiconverter;

/**
 * Created by Kevin on 8/15/2016.
 */
public class MappedEmoji {
    private char unicodeID;
    private String resName;

    public MappedEmoji(char charID, String resource)
    {
        unicodeID = charID;
        resName = resource;
    }

    public char getChar()
    {
        return unicodeID;
    }

    public String getResName()
    {
        return resName;
    }
}
