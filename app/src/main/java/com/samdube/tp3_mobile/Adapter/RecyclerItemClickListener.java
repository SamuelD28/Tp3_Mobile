package com.samdube.tp3_mobile.Adapter;

import android.content.Context;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class RecyclerItemClickListener extends RecyclerView.SimpleOnItemTouchListener {

    //Interface that needs to be implemented by the parent who uses the recyclerview
    public interface OnRecyclerClickListener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

    private final OnRecyclerClickListener mListener; //This property is used to access the parent implementation of the interface
    private final GestureDetectorCompat mGestureDetector; //This is used to detect different type of events occurring. (Tap, long Tap, Swipe, etc..)

    /**
     * Constructor of the RecyclerItemClickListener
     *
     * @param context      Context of the parent activity
     * @param recyclerView Recycler that will be set as final
     * @param listener     Holder for the parent implementation of the OnRecyclerClickListener methods
     */
    public RecyclerItemClickListener(Context context, final RecyclerView recyclerView, OnRecyclerClickListener listener) {
        mListener = listener;
        mGestureDetector = new GestureDetectorCompat(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                //We access the recyclerview item and find the one corresponding to the touched position
                View childView = recyclerView.findChildViewUnder(e.getX(), e.getY());
                //If a childview was found, return it to the parent implementation method and retrieve the child position within the adapter
                if (childView != null && mListener != null) {
                    mListener.onItemClick(childView, recyclerView.getChildAdapterPosition(childView));
                }
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                //We access the recyclerview item and find the one corresponding to the touched position
                View childView = recyclerView.findChildViewUnder(e.getX(), e.getY());
                //If a childview was found, return it to the parent implementation method and retrieve the child position within the adapter
                if (childView != null && mListener != null) {
                    mListener.onItemLongClick(childView, recyclerView.getChildAdapterPosition(childView));
                }
            }
        });
    }

    /**
     * Method that relays every types of Touch event concerning the recycler view to the mGestureDetecter property
     *
     * @param rv RecyclerView that will triger the events
     * @param e  MotionEvent that will hold the information about the event that happened
     * @return Boolean value corresponding to the event success
     */
    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        if (mGestureDetector != null) {
            return mGestureDetector.onTouchEvent(e);
        } else {
            return false;
        }
    }

}
















