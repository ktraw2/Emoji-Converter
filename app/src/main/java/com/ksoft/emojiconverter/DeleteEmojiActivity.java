package com.ksoft.emojiconverter;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

public class DeleteEmojiActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_emoji);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        setTitle("Delete Custom Emojis");

        ListView list = (ListView) findViewById(R.id.deleteListView);
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        final File directory = cw.getDir("custom_emojis", ContextWrapper.MODE_PRIVATE);
        File[] flist = directory.listFiles();
        final ArrayList<String> itemName = new ArrayList<String>();
        final ArrayList<Bitmap> img = new ArrayList<Bitmap>();
        for (int i = 0; i < flist.length; i++)
        {
            String fileName = flist[i].getName().toString();
            if (fileName.length() > 2 && fileName.startsWith("u_") && fileName.endsWith(".png"))
            {
                System.out.println(fileName);
                itemName.add("U+" + fileName.substring(2, fileName.length() - 4).toUpperCase());
                img.add(loadImageFromStorage(directory.toString(), fileName));
            }
        }
        final CustomListAdapter adapter = new CustomListAdapter(this, itemName.toArray(new String[itemName.size()]), img.toArray(new Bitmap[img.size()]));
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                System.out.println("click!");
                //display a dialog to the user confirming emoji deletion
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setMessage("Are you sure you want to delete the custom emoji " + itemName.get(position) + "?");
                builder.setTitle("Confirm Deletion");
                builder.setPositiveButton("Yes, delete it.", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        File file = new File(directory, "u_" +  itemName.get(position).substring(2).toLowerCase() + ".png");
                        System.out.println(file.toString());
                        try
                        {
                            file.delete();
                            Toast.makeText(getApplicationContext(), "Successfully deleted custom emoji", Toast.LENGTH_SHORT).show();
                            itemName.remove(position);
                            img.remove(position);
                            Intent intent = getIntent();
                            finish();
                            startActivity(intent);
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "A deletion error occured", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
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
        return  BitmapFactory.decodeResource(this.getResources(), R.drawable.unknown);
    }
}
