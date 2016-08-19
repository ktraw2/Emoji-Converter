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
        mappedEmojis.add(new MappedEmoji("0x1F618", true));
        mappedEmojis.add(new MappedEmoji("0x1F617", true));
        mappedEmojis.add(new MappedEmoji("0x1F619", true));
        mappedEmojis.add(new MappedEmoji("0x1F61A", true));
        mappedEmojis.add(new MappedEmoji("0x263A", true));
        mappedEmojis.add(new MappedEmoji("0x1F642", true));
        mappedEmojis.add(new MappedEmoji("0x1F917", true));
        mappedEmojis.add(new MappedEmoji("0x1F914", true));
        mappedEmojis.add(new MappedEmoji("0x1F610", true));
        mappedEmojis.add(new MappedEmoji("0x1F611", true));
        mappedEmojis.add(new MappedEmoji("0x1F636", true));
        mappedEmojis.add(new MappedEmoji("0x1F644", true));
        mappedEmojis.add(new MappedEmoji("0x1F60F", true));
        mappedEmojis.add(new MappedEmoji("0x1F623", true));
        mappedEmojis.add(new MappedEmoji("0x1F625", true));
        mappedEmojis.add(new MappedEmoji("0x1F62E", true));
        mappedEmojis.add(new MappedEmoji("0x1F910", true));
        mappedEmojis.add(new MappedEmoji("0x1F62F", true));
        mappedEmojis.add(new MappedEmoji("0x1F62A", true));
        mappedEmojis.add(new MappedEmoji("0x1F62B", true));
        mappedEmojis.add(new MappedEmoji("0x1F634", true));
        mappedEmojis.add(new MappedEmoji("0x1F60C", true));
        mappedEmojis.add(new MappedEmoji("0x1F913", true));
        mappedEmojis.add(new MappedEmoji("0x1F61B", true));
        mappedEmojis.add(new MappedEmoji("0x1F61C", true));
        mappedEmojis.add(new MappedEmoji("0x1F61D", true));
        mappedEmojis.add(new MappedEmoji("0x1F612", true));
        mappedEmojis.add(new MappedEmoji("0x1F613", true));
        mappedEmojis.add(new MappedEmoji("0x1F614", true));
        mappedEmojis.add(new MappedEmoji("0x1F615", true));
        mappedEmojis.add(new MappedEmoji("0x1F643", true));
        mappedEmojis.add(new MappedEmoji("0x1F911", true));
        mappedEmojis.add(new MappedEmoji("0x1F632", true));
        mappedEmojis.add(new MappedEmoji("0x2639", true));
        mappedEmojis.add(new MappedEmoji("0x1F641", true));
        mappedEmojis.add(new MappedEmoji("0x1F616", true));
        mappedEmojis.add(new MappedEmoji("0x1F61E", true));
        mappedEmojis.add(new MappedEmoji("0x1F61F", true));
        mappedEmojis.add(new MappedEmoji("0x1F624", true));
        mappedEmojis.add(new MappedEmoji("0x1F622", true));
        mappedEmojis.add(new MappedEmoji("0x1F62D", true));
        mappedEmojis.add(new MappedEmoji("0x1F626", true));
        mappedEmojis.add(new MappedEmoji("0x1F627", true));
        mappedEmojis.add(new MappedEmoji("0x1F628", true));
        mappedEmojis.add(new MappedEmoji("0x1F629", true));
        mappedEmojis.add(new MappedEmoji("0x1F630", true));
        mappedEmojis.add(new MappedEmoji("0x1F631", true));
        mappedEmojis.add(new MappedEmoji("0x1F633", true));
        mappedEmojis.add(new MappedEmoji("0x1F635", true));
        mappedEmojis.add(new MappedEmoji("0x1F621", true));
        mappedEmojis.add(new MappedEmoji("0x1F620", true));
        mappedEmojis.add(new MappedEmoji("0x1F607", true));
        mappedEmojis.add(new MappedEmoji("0x1F637", true));
        mappedEmojis.add(new MappedEmoji("0x1F912", true));
        mappedEmojis.add(new MappedEmoji("0x1F915", true));
        mappedEmojis.add(new MappedEmoji("0x1F608", true));
        mappedEmojis.add(new MappedEmoji("0x1F47F", true));
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
