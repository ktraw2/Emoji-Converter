package com.ksoft.emojiconverter;

import android.support.v7.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class AddDeleteActivity extends AppCompatActivity {
    private ListView listView;
    private ArrayAdapter<String> listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_delete);

        listView = (ListView) findViewById(R.id.listView);
        String[] listItems = new String[] {"Add a custom emoji", "Delete a custom emoji"};
        ArrayList<String> listItemsList = new ArrayList<String>();
        listItemsList.addAll(Arrays.asList(listItems));
        listAdapter = new ArrayAdapter<String>(this, R.layout.simplerow, listItemsList);
        listView.setAdapter(listAdapter);
        listView.setClickable(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position)
                {
                    case 0:
                        Intent intent0 = new Intent(view.getContext(), AddEmojiActivity.class);
                        startActivity(intent0);
                        break;
                    case 1:
                        Intent intent1 = new Intent(view.getContext(), DeleteEmojiActivity.class);
                        startActivity(intent1);
                        break;
                }
            }
        });
        ActionBar actionBar = this.getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        setTitle("Manage Custom Emojis");
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
}
