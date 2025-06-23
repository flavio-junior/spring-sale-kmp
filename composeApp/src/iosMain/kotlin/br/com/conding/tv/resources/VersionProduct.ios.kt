package br.com.conding.tv.resources

import platform.Foundation.NSBundle

/**
 * CFBundleShortVersionString - App version
 * CFBundleVersion - Build version
 */
internal actual fun versionProduct(): String {
    return NSBundle.mainBundle.infoDictionary?.get("CFBundleShortVersionString").toString()
}
