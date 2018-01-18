package me.duhblea.focusablerecyclerview.managers;

import android.view.View;

/**
 * An interface that provides an Android View to render the details of a particular object.
 */
public interface IItemDetailsProvider<ItemType> {
    /**
     * Provides an Android View to display the details contained in an object.
     *
     * @param obj An object whose member variables will be rendered onto a View.
     * @return The Android View for an object.
     */
    View getView(ItemType obj);

    /**
     * Callback to alert that details have been displayed.
     */
    void onDetailsProvided();

    /**
     * Callback to alert that details have been removed.
     */
    void onDetailsRemoved();
}
