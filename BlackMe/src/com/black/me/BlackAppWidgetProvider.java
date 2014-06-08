package com.black.me;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;

/**
 * Implementation of App Widget functionality. App Widget Configuration
 * implemented in {@link BlackAppWidgetProviderConfigureActivity
 * BlackAppWidgetProviderConfigureActivity}
 */
public class BlackAppWidgetProvider extends AppWidgetProvider {

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		// There may be multiple widgets active, so update all of them
		final int N = appWidgetIds.length;
		for (int i = 0; i < N; i++) {
			updateAppWidget(context, appWidgetManager, appWidgetIds[i]);
		}
	}

	@Override
	public void onDeleted(Context context, int[] appWidgetIds) {
		// When the user deletes the widget, delete the preference associated
		// with it.
		final int N = appWidgetIds.length;
		for (int i = 0; i < N; i++) {
			BlackAppWidgetProviderConfigureActivity.deleteTitlePref(context,
					appWidgetIds[i]);
		}
	}

	@Override
	public void onEnabled(Context context) {
		// Enter relevant functionality for when the first widget is created
	}

	@Override
	public void onDisabled(Context context) {
		// Enter relevant functionality for when the last widget is disabled
	}

	static void updateAppWidget(Context context,
			AppWidgetManager appWidgetManager, int appWidgetId) {

//		CharSequence widgetText = BlackAppWidgetProviderConfigureActivity
//				.loadTitlePref(context, appWidgetId);
		String widgetText = BlackAppWidgetProviderConfigureActivity.loadTitlePref(context, appWidgetId);
		// Create an Intent to launch ExampleActivity
        Intent intent = new Intent(context, BlackAppWidgetProviderConfigureActivity.class);
        Bundle extras = new Bundle();
        extras.putInt(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        intent.putExtras(extras);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        // You have to send different "requestCode" for different PendingIntents
        PendingIntent pendingIntent = PendingIntent.getActivity(context, appWidgetId, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		
		// Construct the RemoteViews object
		RemoteViews views = new RemoteViews(context.getPackageName(),
				R.layout.black_app_widget_provider);
		
		views.setTextViewText(R.id.appwidget_text, widgetText);
		
		 // attach an on-click listener to the button
        views.setOnClickPendingIntent(R.id.appwidget_text, pendingIntent);
        
        // Tell the AppWidgetManager to perform an update on the current app widget
		appWidgetManager.updateAppWidget(appWidgetId, views);
	}
}
