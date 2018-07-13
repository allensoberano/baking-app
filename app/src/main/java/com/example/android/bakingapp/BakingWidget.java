package com.example.android.bakingapp;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.RemoteViews;

/**
 * Implementation of App Widget functionality.
 */
public class BakingWidget extends AppWidgetProvider {

    private String APP_TITLE = "Baking App";
    private String WIDGET_INGREDIENTS = "ingredients";
    private String WIDGET_RECIPE_NAME = "recipeName";

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId, String ingredients, String recipeName) {

        CharSequence widgetText = ingredients;
        CharSequence name = recipeName;
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_widget);
        views.setTextViewText(R.id.recipe_name, name);
        views.setTextViewText(R.id.appwidget_text, widgetText);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    private String getIngredients(Context context){
        SharedPreferences pref = context.getSharedPreferences(APP_TITLE, 0);
        String ingredientList = pref.getString(WIDGET_INGREDIENTS, null);
        return ingredientList;
    }

    private String getRecipeName(Context context){
        SharedPreferences pref = context.getSharedPreferences(APP_TITLE, 0);
        String recipeName = pref.getString(WIDGET_RECIPE_NAME, null);
        return recipeName;
    }


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId, getIngredients(context), getRecipeName(context));
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
}

