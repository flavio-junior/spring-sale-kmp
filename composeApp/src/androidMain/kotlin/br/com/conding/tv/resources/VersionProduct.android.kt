package br.com.conding.tv.resources

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

internal actual fun versionProduct(): String {
    val versionAppInstance: VersionApp = KoinHelperVersionApp.versionApp
    return versionAppInstance.getVersionApp()
}

internal object KoinHelperVersionApp : KoinComponent {
    val versionApp: VersionApp by inject()
}

internal class VersionApp(
    private val context: Context
) {
    fun getVersionApp(): String {
        val packageManager = context.packageManager
        val packageName = context.packageName
        val packageInfo = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            packageManager.getPackageInfo(packageName, PackageManager.PackageInfoFlags.of(0))
        } else {
            packageManager.getPackageInfo(packageName, 0)
        }
        return packageInfo.versionName ?: "N/A"
    }
}
