package com.ksoft.emojiconverter;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.Toast;

/**
 * Implementation of App Widget functionality.
 */
public class QuickConvertWidget extends AppWidgetProvider {
    static EmojiConverter emojiConverter = null;
    static final String INTENT_ACTION = "com.ksoft.emojiconverter.widgetdialogfinished";
    static final String INTENT_EXTRA_DIALOG_RESULT = "dialogresult";
    static final String INTENT_EXTRA_TEXT_ENTERED = "textentered";

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.quick_convert_widget);

        //Assign an intent to a button
        Intent dialogIntent = new Intent(context, ConvertDialog.class);
        dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        PendingIntent dialogIntentPendingIntent = PendingIntent.getActivity(context, 0, dialogIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.choose, dialogIntentPendingIntent);

        initEmojiConverter(context);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onReceive (Context context, Intent intent)
    {
        final String ACTION = intent.getAction();
        if (ACTION.equals(INTENT_ACTION))
        {
            String result = intent.getStringExtra(INTENT_EXTRA_DIALOG_RESULT);
            if (result.equals("OK"))
            {
                String text = intent.getStringExtra(INTENT_EXTRA_TEXT_ENTERED);
                RemoteViews myviews = new RemoteViews(context.getPackageName(), R.layout.quick_convert_widget);
                initEmojiConverter(context);
                myviews.setImageViewBitmap(R.id.appwidget_view, emojiConverter.convertEmoji(text, context.getPackageName()));

                //Update the appwidget
                ComponentName myWidget = new ComponentName(context, QuickConvertWidget.class);
                AppWidgetManager manager = AppWidgetManager.getInstance(context);
                manager.updateAppWidget(myWidget, myviews);
            }
            else if (result.equals("CANCEL"))
            {
                //do nothing
            }
            else
                Toast.makeText(context, "An error occurred", Toast.LENGTH_SHORT).show();
        }
        super.onReceive(context, intent);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
        if (emojiConverter == null)
            emojiConverter = new EmojiConverter(context, context.getResources());
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    public static void initEmojiConverter(Context context)
    {
        if (emojiConverter == null)
            emojiConverter = new EmojiConverter(context, context.getResources());
    }
}

