package hr.from.ivantoplak.pokemonapp.logging

import timber.log.Timber

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
