package com.ksoft.emojiconverter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Kevin on 8/21/2016.
 */
public class CustomListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] itemName;
    private final Bitmap[] img;

    public CustomListAdapter(Activity context, String[] itemName, Bitmap[] img)
    {
        super(context, R.layout.deletelistrow, itemName);

        this.context = context;
        this.itemName = itemName;
        this.img = img;
    }

    public View getView(int position, View view, ViewGroup parent)
    {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.deletelistrow, null, true);

        TextView title = (TextView) rowView.findViewById(R.id.deleteTextView);
        ImageView image = (ImageView) rowView.findViewById(R.id.deleteImageView);

        title.setText(itemName[position]);
        image.setImageBitmap(img[position]);
        return rowView;
    }
}
