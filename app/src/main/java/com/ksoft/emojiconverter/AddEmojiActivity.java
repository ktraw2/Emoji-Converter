package com.ksoft.emojiconverter;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;

public class AddEmojiActivity extends AppCompatActivity {
    private static final int PICK_IMAGE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_emoji);
        TextView step1 = (TextView) findViewById(R.id.step1);
        step1.setMovementMethod(LinkMovementMethod.getInstance());
    }

    public void addCustomEmoji(View view)
    {
        EditText editText = (EditText) findViewById(R.id.emojiCode);
        String text = editText.getText().toString();
        if (text.length() > 2 && text.startsWith("U+"))
        {
            ImageView imageView = (ImageView) findViewById(R.id.preview);
            if (imageView.getDrawable() != null)
            {
                System.out.println("IMAGE: " + storeImage(((BitmapDrawable)imageView.getDrawable()).getBitmap(), "u_" + text.substring(2).toLowerCase() + ".png"));
                Toast.makeText(getApplicationContext(), "IMAGE SAVED", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void chooseImageClick(View view)
    {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE)
        {
            Uri imageUri = data.getData();
            ImageView imageView = (ImageView) findViewById(R.id.preview);
            imageView.setImageURI(imageUri);
        }
    }

    public String storeImage(Bitmap image, String name)
    {
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        //save to /data/data/com.ksoft.emojiconverter/app_data/custom_emojis
        File directory = cw.getDir("custom_emojis", Context.MODE_PRIVATE);
        File mypath = new File(directory, name);
        FileOutputStream fos = null;
        try
        {
            fos = new FileOutputStream(mypath);
            image.compress(Bitmap.CompressFormat.PNG, 100, fos);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                fos.close();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath();
    }
}
