package hr.from.ivantoplak.pokemonapp.app.nav

import java.lang.ref.WeakReference

/**
 * Provides access to the navigation actions in the app.
 * Use DI to inject it to Composable functions instead of passing it as a parameter.
 */
class AppNavActionProvider {
    private var appNavActionsReference = WeakReference<AppNavActions>(null)

    var appNavActions: AppNavActions?
        get() = appNavActionsReference.get()
        set(value) {
            appNavActionsReference = WeakReference(value)
        }
}
