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
    public static final boolean DEBUG = false;
    public ArrayList<MappedEmoji> mappedEmojis = new ArrayList<MappedEmoji>();
    File directory = null;
    EmojiConverter emojiConverter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (emojiConverter == null)
            emojiConverter = new EmojiConverter(getApplicationContext(), this.getResources());
        emojiConverter.mapEmojis();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        emojiConverter.mapEmojis();
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

    /**
     * called when the user presses the "Convert" button
     * @param view the current view
     */
    public void convertEmoji(View view) {
        ImageView img = (ImageView) findViewById(R.id.image);
        EditText edit = (EditText) findViewById(R.id.edit);
        img.setImageBitmap(emojiConverter.convertEmoji(edit, getPackageName()));
    }
}
