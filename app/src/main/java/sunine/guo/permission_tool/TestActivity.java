package sunine.guo.permission_tool;

import android.app.Activity;

import androidx.annotation.NonNull;

import sunine.guo.lib_permission_tool.OnAllPermissionsGranted;
import sunine.guo.lib_permission_tool.PermissionTool;

public class TestActivity extends Activity implements OnAllPermissionsGranted {

    PermissionTool permissionTool;
    @Override
    protected void onResume() {
        super.onResume();
        permissionTool = new PermissionTool();
        permissionTool.addPermission("")
                .setOnAllPermissionsGranted(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        permissionTool.onRequestPermissionsResult(permissions,grantResults);
    }

    @Override
    public void onAllPermissionsGranted() {
        // TODO: 2023/8/30  
    }
}
