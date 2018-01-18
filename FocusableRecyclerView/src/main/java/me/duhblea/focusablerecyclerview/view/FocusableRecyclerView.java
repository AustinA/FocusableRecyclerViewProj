package me.duhblea.focusablerecyclerview.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import me.duhblea.focusablerecyclerview.R;
import me.duhblea.focusablerecyclerview.managers.IItemDetailsProvider;

/**
 * A FrameLayout that contains a RecyclerView and a panel to display more specific details
 * about a selected object.
 *
 * @author Austin Alderton
 */
public class FocusableRecyclerView<ItemType> extends FrameLayout {

    private static final int RECYCLER_VIEW_ID = 310;
    private static final int ITEM_SELECTED_VIEW_ID = 311;

    private Animation slideUp;
    private Animation slideDown;

    private RecyclerView recyclerView;
    private LinearLayout focusPanel;
    private LinearLayout contentFrame;
    private IItemDetailsProvider<ItemType> provider;

    public FocusableRecyclerView(@NonNull Context context) {
        super(context);
        initializeInternalViews(context);
    }

    public FocusableRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initializeInternalViews(context);
    }

    public FocusableRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs,
                                 int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initializeInternalViews(context);
    }

    public void setDetailsProvider(@NonNull IItemDetailsProvider<ItemType> provider)
    {
        this.provider = provider;
    }

    public boolean isAnItemFocused()
    {
        return (focusPanel.getVisibility() == VISIBLE);
    }

    /**
     * Returns the Recycler View.
     */
    public RecyclerView getRecyclerView()
    {
        return recyclerView;
    }

    /**
     * Sets the selected item content
     * @param obj The object to be displayed
     */
    public void focusItem(ItemType obj)
    {
        if (obj != null && provider != null) {
            View view = provider.getView(obj);

            ViewGroup.LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            contentFrame.addView(view, params);
            focusSelectedItem();
        }
    }

    /**
     * Helper to perform cleanup when an item becomes focused.
     */
    private void focusSelectedItem()
    {
        recyclerView.setVisibility(GONE);
        focusPanel.startAnimation(slideUp);
        focusPanel.setVisibility(VISIBLE);

    }

    /**
     * Helper to perform cleanup when an item becomes unfocused.
     */
    private void unfocusSelectedItem()
    {
        contentFrame.removeAllViews();

        focusPanel.startAnimation(slideDown);
        focusPanel.setVisibility(GONE);
        recyclerView.setVisibility(VISIBLE);

        if (provider != null) {
            provider.onDetailsRemoved();
        }
    }

    /*
     * Initialize the RecyclerView and FrameLayout for the selected item focus view,
     * and position them in the containing layout
     *
     * @param ctx Context of the object initializing the view.
     */
    private void initializeInternalViews(Context ctx) {
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (inflater != null) {

            Animation.AnimationListener slideUpListener
                    = new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    if (provider != null) {
                        provider.onDetailsProvided();
                    }
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            };

            Animation.AnimationListener slideDownListener
                    = new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    if (provider != null)
                    {
                        provider.onDetailsRemoved();
                    }
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            };

            slideUp = AnimationUtils.loadAnimation(ctx, R.anim.slide_up);
            slideUp.setAnimationListener(slideUpListener);
            slideDown = AnimationUtils.loadAnimation(ctx, R.anim.slide_down);
            slideDown.setAnimationListener(slideDownListener);


            recyclerView = new RecyclerView(ctx);
            recyclerView.setId(RECYCLER_VIEW_ID);
            recyclerView.setVisibility(VISIBLE);
            ViewGroup.LayoutParams paramsRecyclerView = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);

            focusPanel = (LinearLayout) inflater.inflate(R.layout.focused_item_container, null);

            if (focusPanel != null) {
                focusPanel.setId(ITEM_SELECTED_VIEW_ID);
                focusPanel.setVisibility(GONE);
                ViewGroup.LayoutParams paramsItemSelectedView = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);

                contentFrame = focusPanel.findViewById(R.id.contentFrameOfFocusedItem);

                // Add the Item Selected Layout View
                addView(recyclerView, paramsRecyclerView);
                addView(focusPanel, paramsItemSelectedView);

                ImageButton deFocusButton = focusPanel.findViewById(R.id.backButton);
                deFocusButton.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        unfocusSelectedItem();
                    }
                });
            }
        }
    }
}
