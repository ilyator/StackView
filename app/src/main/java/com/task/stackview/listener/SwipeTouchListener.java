package com.task.stackview.listener;

import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.task.stackview.R;
import com.task.stackview.view.CardStackView;


public class SwipeTouchListener implements View.OnTouchListener {
    private static final float CARDS_SWIPE_LENGTH = 200;
    private float originalX = 0;
    private float originalY = 0;
    private float startMoveX = 0;
    private float startMoveY = 0;
    private int startPhotoY = 0;
    private int[] photoLocation = {0, 0};
    private View rootView;
    private ImageView photoView;
    private TextView tvLike, tvDislike;
    private OnCardMovement listener;
    private int orientation;

    public SwipeTouchListener(View rootView, OnCardMovement listener, int orientation) {
        this.rootView = rootView;
        this.listener = listener;
        originalX = rootView.getX();
        originalY = rootView.getY();
        this.orientation = orientation;
        photoView = (ImageView) rootView.findViewById(R.id.iv_photo);
        tvLike = (TextView) rootView.findViewById(R.id.tv_like);
        tvDislike = (TextView) rootView.findViewById(R.id.tv_dislike);
    }

    public void setStartPoints(float x, float y) {
        startMoveX = x;
        startMoveY = y;
    }

    public boolean onTouch(View v, MotionEvent event) {
        boolean processed = true;
        float X = event.getRawX();
        float Y = event.getRawY();
        float deltaX = X - startMoveX;
        float deltaY = Y - startMoveY;
        float distance = orientation == CardStackView.HORIZONTAL ? deltaX : deltaY;
        int action = event.getAction() & MotionEvent.ACTION_MASK;
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                originalX = rootView.getX();
                originalY = rootView.getY();
                startMoveX = X;
                startMoveY = Y;
                photoView.getLocationOnScreen(photoLocation);
                startPhotoY = photoLocation[1];
                break;
            case MotionEvent.ACTION_UP:
                boolean swiped = false;
                if (Math.abs(distance) > CARDS_SWIPE_LENGTH) { // Moved far enough to be an event
                    if (orientation == CardStackView.VERTICAL) {
                        swiped = true;
                        if (deltaY > 0) {
                            handleSwipeDown();
                        } else {
                            handleSwipeUp();
                        }
                    } else { // Horizontal move
                        swiped = true;
                        if (deltaX > 0) {
                            handleSwipeRight();
                        } else {
                            handleSwipeLeft();
                        }
                    }
                }
                if (!swiped) {
                    photoView.setX(originalX);
                    photoView.setY(originalY);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (orientation == CardStackView.VERTICAL) {
                    photoView.setTranslationY(deltaY);
                    photoView.getLocationOnScreen(photoLocation);
                    if (photoLocation[1] > startPhotoY) {
                        ViewGroup.LayoutParams params = tvDislike.getLayoutParams();
                        params.height = (int) deltaY;
                        tvDislike.setLayoutParams(params);
                        tvDislike.invalidate();
                    } else {
                        ViewGroup.LayoutParams params = tvLike.getLayoutParams();
                        params.height = -(int) deltaY;
                        tvLike.setLayoutParams(params);
                        tvLike.invalidate();
                    }
                }
                if (orientation == CardStackView.HORIZONTAL) {
                    photoView.setTranslationX(deltaX);
                }
                break;
            default:
                processed = false;
                break;
        }
        listener.onMovedFromCenter(distance);
        return processed;
    }

    private void handleSwipeLeft() {
        Animation animation = AnimationUtils.loadAnimation(rootView.getContext(), R.anim.slide_left);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                listener.onSwipedLeft();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        rootView.startAnimation(animation);
    }

    private void handleSwipeRight() {
        Animation animation = AnimationUtils.loadAnimation(rootView.getContext(), R.anim.slide_right);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                listener.onSwipedRight();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        rootView.startAnimation(animation);
    }

    private void handleSwipeUp() {
        Animation animation = AnimationUtils.loadAnimation(rootView.getContext(), R.anim.slide_up);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                listener.onSwipedUp();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        rootView.startAnimation(animation);
    }

    private void handleSwipeDown() {
        Animation animation = AnimationUtils.loadAnimation(rootView.getContext(), R.anim.slide_down);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                listener.onSwipedDown();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        rootView.startAnimation(animation);
    }

    public interface OnCardMovement {
        public void onSwipedLeft();

        public void onSwipedRight();

        public void onSwipedUp();

        public void onSwipedDown();

        public void onMovedFromCenter(float distance);
    }

}