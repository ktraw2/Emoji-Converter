package com.ksoft.emojiconverter;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ConvertDialog extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_convert_dialog);
    }

    public void clickOK(View view)
    {
        EditText edit = (EditText) findViewById(R.id.dialog_edit);
        Intent intent = new Intent(IntentStrings.INTENT_ACTION_FINISHED);
        intent.putExtra(IntentStrings.INTENT_EXTRA_DIALOG_RESULT, "OK");
        intent.putExtra(IntentStrings.INTENT_EXTRA_TEXT_ENTERED, edit.getText().toString());
        sendBroadcast(intent);
        finish();
    }

    public void clickCancel(View view)
    {
        Intent intent = new Intent(IntentStrings.INTENT_ACTION_FINISHED);
        intent.putExtra(IntentStrings.INTENT_EXTRA_DIALOG_RESULT, "CANCEL");
        sendBroadcast(intent);
        finish();
    }
}
