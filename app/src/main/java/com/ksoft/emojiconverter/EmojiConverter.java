package com.ksoft.emojiconverter;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * Created by Kevin on 9/1/2016.
 */
public class EmojiConverter {
    private static final boolean DEBUG = false;
    private ArrayList<MappedEmoji> mappedEmojis = new ArrayList<MappedEmoji>();
    private File directory;
    private Context context;
    private Resources resources;

    public EmojiConverter(Context getContext, Resources getResources)
    {
        context = getContext;
        resources = getResources;
    }

    public void mapEmojis()
    {
        //map all emojis
        mappedEmojis = new ArrayList<MappedEmoji>();
        //map all user set emojis
        ContextWrapper cw = new ContextWrapper(context);
        //read from /data/.../com.ksoft.emojiconverter/app_custom_emojis
        directory = cw.getDir("custom_emojis", Context.MODE_PRIVATE);
        //directory = cw.getFilesDir();
        File[] flist = directory.listFiles();
        for (File file : flist)
            if (file.isFile())
            {
                String name = file.getName();
                if (name.length() > 2 && name.startsWith("u_") && name.endsWith(".png"))
                {
                    String string = "0x" + name.substring(2, name.length() - 4).toUpperCase();
                    mappedEmojis.add(new MappedEmoji(string, false));
                    System.out.println("added " + string + " from custom emojis");
                }
            }

        //map all default emojis in /res/drawable
        for (Field field : R.drawable.class.getFields())
        {
            String name = field.getName();
            if (name.startsWith("u_"))
            {
                //check if the user has already defined a conversion for the specified codepoint, if so, then skip over the default one
                String codepoint = "0x" + name.substring(2).toUpperCase();
                boolean goAhead = true;
                for (MappedEmoji emoji : mappedEmojis)
                    if (emoji.getUnicodeHexString().equals(codepoint) && emoji.getGetFromDrawables() == false)
                        goAhead = false;
                if (goAhead)
                {
                    mappedEmojis.add(new MappedEmoji(codepoint, true));
                    System.out.println("added " + codepoint + " from default emojis");
                }
            }
        }
    }

    private Bitmap loadImageFromStorage(String path, String name)
    {
        FileInputStream inputStream = null;
        try
        {
            File f = new File(path, name);
            inputStream = new FileInputStream(f);
            Bitmap b = BitmapFactory.decodeStream(inputStream);
            return b;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                inputStream.close();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        //default return is the "unknown" drawable
        return BitmapFactory.decodeResource(resources, R.drawable.unknown);
    }

    /**
     * called when the user presses the "Convert" button
     * @param edit edittext to get text from
     * @param packageName name of package
     */
    public Bitmap convertEmoji(EditText edit, String packageName) {
        String textEntered = edit.getText().toString();
        if (textEntered.length() != 0)
        {
            if (DEBUG)
            {
                if (Integer.parseInt(Integer.toHexString(textEntered.codePointAt(0)), 16) == 0x1F600)
                    System.out.println("text equal to 0x1F600");
                else
                    System.out.println("not equal");
            }
            String codepointEntered = "0x" + Integer.toHexString(textEntered.codePointAt(0)).toUpperCase();
            if (DEBUG)
                System.out.println("HEX CODE POINT: " + codepointEntered);
            boolean valid = false;
            MappedEmoji validEmoji = null;
            for (MappedEmoji m : mappedEmojis)
                if (codepointEntered.equals(m.getUnicodeHexString()))
                {
                    valid = true;
                    validEmoji = m;
                    break;
                }
            if (valid && validEmoji != null)
            {
                if (validEmoji.getGetFromDrawables() == true)
                {
                    int resID = resources.getIdentifier(validEmoji.getResName(), "drawable", packageName);
                    return BitmapFactory.decodeResource(resources, resID);
                }
                else
                if (!(directory == null))
                {
                    System.out.println(directory.toString());
                    return loadImageFromStorage(directory.toString(), validEmoji.getResName() + ".png");
                }
                if (DEBUG)
                {
                    System.out.println("the text entered is mapped");
                    System.out.println(validEmoji.getResName());
                }
            }
            else
                return BitmapFactory.decodeResource(resources, R.drawable.unknown);
        }
        return BitmapFactory.decodeResource(resources, R.drawable.unknown);
    }
}
