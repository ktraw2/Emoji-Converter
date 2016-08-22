package com.ksoft.emojiconverter;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    public static final boolean DEBUG = true;
    ArrayList<MappedEmoji> mappedEmojis = new ArrayList<MappedEmoji>();
    File directory = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //map all emojis
        //map all user set emojis
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.add_delete_emoji:
                Intent intent = new Intent(this, AddDeleteActivity.class);
                startActivity(intent);
                break;
        }
        return true;
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
        return BitmapFactory.decodeResource(this.getResources(), R.drawable.unknown);
    }

    /**
     * called when the user presses the "Convert" button
     * @param view the current view
     */
    public void convertEmoji(View view) {
        ImageView img = (ImageView) findViewById(R.id.image);
        EditText edit = (EditText) findViewById(R.id.edit);
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
                    int resID = getResources().getIdentifier(validEmoji.getResName(), "drawable", getPackageName());
                    img.setImageResource(resID);
                }
                else
                    if (!(directory == null))
                    {
                        System.out.println(directory.toString());
                        img.setImageBitmap(loadImageFromStorage(directory.toString(), validEmoji.getResName() + ".png"));
                    }
                if (DEBUG)
                {
                    System.out.println("the text entered is mapped");
                    System.out.println(validEmoji.getResName());
                }
            }
            else
                img.setImageResource(R.drawable.unknown);
        }
        else
        {
            img.setImageResource(R.drawable.unknown);
        }
    }
}
