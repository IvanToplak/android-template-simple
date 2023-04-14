package hr.from.ivantoplak.pokemonapp.logging

import hr.from.ivantoplak.pokemonapp.BuildConfig
import timber.log.Timber

/**
 * Initializing app logging.
 * Enhancing DEBUG tags with more details.
 * Disable analytics/crashlytics when debugging, and per build configuration.
 */
fun initLogging() {
    // val enableCrashlytics = !BuildConfig.DEBUG && BuildConfig.ENABLE_CRASHLYTICS
    // val enableAnalytics = !BuildConfig.DEBUG && BuildConfig.ENABLE_ANALYTICS
    //
    // Firebase.crashlytics.setCrashlyticsCollectionEnabled(enableCrashlytics)
    // Firebase.analytics.setAnalyticsCollectionEnabled(enableAnalytics)

    if (BuildConfig.DEBUG) {
        Timber.plant(
            object : Timber.DebugTree() {
                override fun createStackElementTag(element: StackTraceElement): String {
                    return "(${element.fileName}:${element.lineNumber})#${element.methodName}"
                }
            },
        )
    } else {
        Timber.plant(CrashReportingTree())
    }
}
