package hr.from.ivantoplak.pokemonapp.common.logging

import hr.from.ivantoplak.pokemonapp.BuildConfig
import timber.log.Timber

/**
 * Initializing app logging.
 * Enhancing DEBUG tags with more details.
 */
fun initLogging() {
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

/**
 * Logging fatal and non-fatal exceptions with Timber and Crashlytics.
 * Non-fatal exceptions are handled exceptions that don't crash the app.
 * They should be logged with Timber.e() method to appear in Crashlytics report.
 */
class CrashReportingTree : Timber.Tree() {
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        // val crashlytics = Firebase.crashlytics
        // crashlytics.log(message)
        //
        // if (priority == ERROR) {
        //     val exception = t ?: Throwable(message)
        //     crashlytics.recordException(exception)
        // }
    }
}
