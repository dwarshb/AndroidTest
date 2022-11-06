package com.datechnologies.androidtest.animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.datechnologies.androidtest.MainActivity;
import com.datechnologies.androidtest.R;
import com.google.android.material.button.MaterialButton;
import com.plattysoft.leonids.ParticleSystem;

/**
 * Screen that displays the D & A Technologies logo.
 * The icon can be moved around on the screen as well as animated.
 * */

public class AnimationActivity extends AppCompatActivity implements View.OnDragListener {

    //==============================================================================================
    // Class Properties
    //==============================================================================================
    MaterialButton animationButton;
    ImageView logoView;
    LinearLayout linearLayout;

    MediaPlayer mediaPlayer;
    ValueAnimator changeBackgroundColor,backToOriginalColor;
    //==============================================================================================
    // Static Class Methods
    //==============================================================================================

    public static void start(Context context)
    {
        Intent starter = new Intent(context, AnimationActivity.class);
        context.startActivity(starter);
    }

    //==============================================================================================
    // Lifecycle Methods
    //==============================================================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);

        ActionBar actionBar = getSupportActionBar();

        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        // TODO: Make the UI look like it does in the mock-up. Allow for horizontal screen rotation.
        // TODO: Add a ripple effect when the buttons are clicked

        // TODO: When the fade button is clicked, you must animate the D & A Technologies logo.
        // TODO: It should fade from 100% alpha to 0% alpha, and then from 0% alpha to 100% alpha

        // TODO: The user should be able to touch and drag the D & A Technologies logo around the screen.

        // TODO: Add a bonus to make yourself stick out. Music, color, fireworks, explosions!!!

        animationButton = findViewById(R.id.animation_button);
        linearLayout = findViewById(R.id.linear_layout);
        logoView = findViewById(R.id.logo_imageView);
        logoView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    ClipData data = ClipData.newPlainText("" , "");
                    View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(logoView);
                    v.startDrag(data , shadowBuilder , v , 0);
                    v.setVisibility(View.VISIBLE);
                    return true;
                } else {
                    return false;
                }
            }
        });
        linearLayout.setOnDragListener(this);

        ObjectAnimator fadeOut = ObjectAnimator.ofFloat(logoView, "alpha",  1f, .3f);
        fadeOut.setDuration(3000);
        ObjectAnimator fadeIn = ObjectAnimator.ofFloat(logoView, "alpha", .3f, 1f);
        fadeIn.setDuration(3000);

        AnimatorSet mAnimationSet = new AnimatorSet();
        mAnimationSet.play(fadeIn).after(fadeOut);

        setupBonusTask();
        animationButton.setOnClickListener( v-> {
            mAnimationSet.start();
            startBonusTask();
        });
    }

    private void setupBonusTask() {
        int primaryColor = getColor(R.color.colorPrimary);
        changeBackgroundColor = ValueAnimator.ofObject(new ArgbEvaluator(), Color.WHITE, primaryColor);
        changeBackgroundColor.setDuration(1500); // milliseconds
        changeBackgroundColor.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                linearLayout.setBackgroundColor((int) animator.getAnimatedValue());
            }
        });

        backToOriginalColor = ValueAnimator.ofObject(new ArgbEvaluator(), primaryColor, Color.WHITE);
        backToOriginalColor.setDuration(1500); // milliseconds
        backToOriginalColor.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                linearLayout.setBackgroundColor((int) animator.getAnimatedValue());
            }
        });
        mediaPlayer = MediaPlayer.create(this, R.raw.firework);
    }

    private void startBonusTask() {
        changeBackgroundColor.start();
        mediaPlayer.start();
        ParticleSystem ps = new ParticleSystem(this, 100, R.drawable.ic_firework_16, 1000)
                .setScaleRange(0.7f, 1.3f)
                .setSpeedModuleAndAngleRange(0.07f, 0.16f, 0, 180)
                .setRotationSpeedRange(90, 180)
                .setAcceleration(0.00013f, 90)
                .setFadeOut(200, new AccelerateInterpolator());
        ps.emit(findViewById(R.id.top_left), 100);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ps.stopEmitting();
                backToOriginalColor.start();
            }
        },2000);
    }

    // This is the method that the system calls when it dispatches a drag event to the listener.
    @Override
    public boolean onDrag(View v, DragEvent event) {
        String msg = "onDrag";
        switch (event.getAction()) {
            case DragEvent.ACTION_DRAG_STARTED:
                Log.d(msg , "Action is DragEvent.ACTION_DRAG_STARTED");
                break;

            case DragEvent.ACTION_DRAG_ENTERED:
                Log.d(msg , "Action is DragEvent.ACTION_DRAG_ENTERED");
                break;

            case DragEvent.ACTION_DRAG_EXITED:
                Log.d(msg , "Action is DragEvent.ACTION_DRAG_EXITED");

                break;

            case DragEvent.ACTION_DRAG_LOCATION:
                Log.d(msg , "Action is DragEvent.ACTION_DRAG_LOCATION");
                break;

            case DragEvent.ACTION_DRAG_ENDED:
                Log.d(msg , "Action is DragEvent.ACTION_DRAG_ENDED");
                break;

            case DragEvent.ACTION_DROP:
                View tvState = (View) event.getLocalState();
                ViewGroup tvParent = (ViewGroup) tvState.getParent();
                tvParent.removeView(tvState);
                LinearLayout container = (LinearLayout) v;
                container.addView(tvState);
                tvParent.removeView(tvState);
                tvState.setX(event.getX()-(tvState.getWidth()/2));
                tvState.setY(event.getY());
                ((LinearLayout) v).addView(tvState);
                v.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
