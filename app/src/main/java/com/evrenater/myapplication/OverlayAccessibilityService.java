package com.evrenater.myapplication;

import android.accessibilityservice.AccessibilityService;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import android.widget.ImageView;

public class OverlayAccessibilityService extends AccessibilityService {

    private WindowManager windowManager;
    private View overlayView, leftBottom, rightTop, rightBottom;
    private WindowManager.LayoutParams overlayParams, lbParams, rtParams, rbParams;

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        initializeOverlayView();
    }

    private void initializeOverlayView() {
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

        // Create an overlay view
        overlayView = new ImageView(this);
        overlayView.setBackground(getResources().getDrawable(R.drawable.left_top));

        // Set up the layout parameters for the overlay view
        overlayParams = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,

                (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) ?
                        WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY :
                        (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) ?
                                WindowManager.LayoutParams.TYPE_SYSTEM_ERROR :
                                WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY,
                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
                        | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
                        | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                        | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                PixelFormat.TRANSLUCENT);

        overlayParams.gravity = Gravity.TOP | Gravity.START;



        // left bottom
        leftBottom = new ImageView(this);
        leftBottom.setBackground(getResources().getDrawable(R.drawable.left_bottom));


        lbParams = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,

                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
                        | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
                        | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                        | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                PixelFormat.TRANSLUCENT);

        lbParams.gravity = Gravity.BOTTOM | Gravity.START;


        // right top
        rightTop = new ImageView(this);
        rightTop.setBackground(getResources().getDrawable(R.drawable.right_top));


        rtParams = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,

                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
                        | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
                        | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                        | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                PixelFormat.TRANSLUCENT);

        rtParams.gravity = Gravity.TOP | Gravity.END;


        // right bottom
        rightBottom = new ImageView(this);
        rightBottom.setBackground(getResources().getDrawable(R.drawable.right_bottom));


        rbParams = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,

                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
                        | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
                        | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                        | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                PixelFormat.TRANSLUCENT);

        rbParams.gravity = Gravity.BOTTOM | Gravity.END;


        // Add the view to the window manager
        windowManager.addView(overlayView, overlayParams);
        windowManager.addView(leftBottom, lbParams);
        windowManager.addView(rightTop, rtParams);
        windowManager.addView(rightBottom, rbParams);
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        // Handle accessibility events here
        // For example, you can draw on the overlay view based on the events received
        Log.d("AccessibilityService", "Received event: " + event);
    }

    @Override
    public void onInterrupt() {
        // Clean up resources when the service is interrupted
        if (windowManager != null && overlayView != null) {
            windowManager.removeView(overlayView);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Ensure proper cleanup when the service is destroyed
        if (windowManager != null && overlayView != null) {
            windowManager.removeView(overlayView);
        }
    }
}
