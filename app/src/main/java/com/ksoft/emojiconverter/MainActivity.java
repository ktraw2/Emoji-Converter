package com.ksoft.emojiconverter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static final boolean DEBUG = false;

    ArrayList<MappedEmoji> mappedEmojis = new ArrayList<MappedEmoji>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //map all emojis
        mappedEmojis.add(new MappedEmoji("0x1F600", true));
        mappedEmojis.add(new MappedEmoji("0x1F601", true));
        mappedEmojis.add(new MappedEmoji("0x1F602", true));
        mappedEmojis.add(new MappedEmoji("0x1F923", true));
        mappedEmojis.add(new MappedEmoji("0x1F604", true));
        mappedEmojis.add(new MappedEmoji("0x1F605", true));
        mappedEmojis.add(new MappedEmoji("0x1F606", true));
        mappedEmojis.add(new MappedEmoji("0x1F609", true));
        mappedEmojis.add(new MappedEmoji("0x1F60A", true));
        mappedEmojis.add(new MappedEmoji("0x1F60B", true));
        mappedEmojis.add(new MappedEmoji("0x1F60E", true));
        mappedEmojis.add(new MappedEmoji("0x1F60D", true));

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
                }
            if (valid && validEmoji != null)
            {
                int resID = getResources().getIdentifier(validEmoji.getResName(), "drawable", getPackageName());
                img.setImageResource(resID);
                if (DEBUG)
                {
                    System.out.println("the text entered is mapped");
                    System.out.println(validEmoji.getResName());
                    System.out.println("resID: " + resID);
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
