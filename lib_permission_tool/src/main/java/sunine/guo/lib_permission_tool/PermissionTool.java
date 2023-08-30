package sunine.guo.lib_permission_tool;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import java.util.ArrayList;
import java.util.List;

public class PermissionTool {
    private static final String LT = PermissionTool.class.getSimpleName();
    private static final int REQUEST_CODE = 1234;

    private final List<String> listPermissions = new ArrayList<>();


    public PermissionTool() {
    }

    public PermissionTool addPermission(String permission) {
        listPermissions.add(permission);
        return this;
    }


    private OnAllPermissionsGranted onAllPermissionsGranted;

    private OnOnePermissionGranted onOnePermissionGranted;

    public PermissionTool setOnAllPermissionsGranted(OnAllPermissionsGranted onAllPermissionsGranted) {
        this.onAllPermissionsGranted = onAllPermissionsGranted;
        return this;
    }

    public PermissionTool setOnOnePermissionGranted(OnOnePermissionGranted onOnePermissionGranted) {
        this.onOnePermissionGranted = onOnePermissionGranted;
        return this;
    }

    public void requestPermission(Activity activity) {
        if (listPermissions.size() == 0) {
            Log.w(LT, "[requestPermission] NO permission.");
            return;
        }
        if (hasAllPermissions(activity) && onAllPermissionsGranted != null) {
            onAllPermissionsGranted.onAllPermissionsGranted();
            return;
        }
        String[] s = listPermissions.toArray(new String[]{});
        ActivityCompat.requestPermissions(activity, s, REQUEST_CODE);
    }

    public boolean isPermissionNotGranted(Activity activity, String permission) {
        return ActivityCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED;
    }

    private boolean hasAllPermissions(Activity activity) {
        for (int i = 0; i < listPermissions.size(); i++) {
            if (ActivityCompat.checkSelfPermission(activity, listPermissions.get(i)) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }


    public void onRequestPermissionsResult(String[] permission, int[] grantResults) {
        if (onAllPermissionsGranted == null) {
            return;
        }

        int count = 0;
        for (int i = 0; i < grantResults.length; i++) {
            if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                count++;
            }
            if (onOnePermissionGranted != null) {
                onOnePermissionGranted.onOnePermissionGranted(permission[i], grantResults[i] == PackageManager.PERMISSION_GRANTED);
            }
        }
        if (count == grantResults.length && onAllPermissionsGranted != null) {
            onAllPermissionsGranted.onAllPermissionsGranted();
        }
    }
}
