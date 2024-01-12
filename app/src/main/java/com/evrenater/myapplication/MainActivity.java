package com.evrenater.myapplication;import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final int OVERLAY_PERMISSION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Check if the accessibility service is enabled
        if (!isAccessibilityServiceEnabled(this, OverlayAccessibilityService.class)) {
            // Request the user to enable the accessibility service
            Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
            startActivity(intent);
        } else {
            // Start the accessibility service
            startService(new Intent(this, OverlayAccessibilityService.class));
        }

        // Request overlay drawing permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {
            Intent overlayIntent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
            startActivityForResult(overlayIntent, OVERLAY_PERMISSION_REQUEST_CODE);
        } else {
            // Start your drawing logic here
        }

        Button startDrawingButton = findViewById(R.id.startDrawingButton);
        startDrawingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start your drawing logic here
            }
        });
    }

    // Check if the accessibility service is enabled
    private boolean isAccessibilityServiceEnabled(Context context, Class<?> accessibilityService) {
        String service = context.getPackageName() + "/" + accessibilityService.getName();
        int accessibilityEnabled = 0;
        try {
            accessibilityEnabled = Settings.Secure.getInt(
                    context.getContentResolver(),
                    Settings.Secure.ACCESSIBILITY_ENABLED);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }

        if (accessibilityEnabled == 1) {
            String settingValue = Settings.Secure.getString(
                    context.getContentResolver(),
                    Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
            return settingValue != null && settingValue.toLowerCase().contains(service.toLowerCase());
        }

        return false;
    }
}
