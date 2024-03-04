package hr.from.ivantoplak.pokemonapp.common.nav

import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import hr.from.ivantoplak.pokemonapp.R
import hr.from.ivantoplak.pokemonapp.app.nav.AppNavActions
import hr.from.ivantoplak.pokemonapp.app.nav.AppScreen
import hr.from.ivantoplak.pokemonapp.common.ui.error.ErrorScreen

enum class ErrorScreenParameter(val param: String) {
    Title("title"),
    Body("body"),
}

fun NavGraphBuilder.errorScreen(
    navActions: AppNavActions,
) {
    composable(
        route = AppScreen.Error.name +
            "?${ErrorScreenParameter.Title.param}={${ErrorScreenParameter.Title.param}}" +
            "&${ErrorScreenParameter.Body.param}={${ErrorScreenParameter.Body.param}}",
        arguments = listOf(
            navArgument(ErrorScreenParameter.Title.param) { nullable = true },
            navArgument(ErrorScreenParameter.Body.param) { nullable = true },
        ),
    ) { backStackEntry ->
        val title = backStackEntry.arguments?.getString(ErrorScreenParameter.Title.param)
            ?: stringResource(id = R.string.error_screen_content_title)
        val body = backStackEntry.arguments?.getString(ErrorScreenParameter.Body.param)
            ?: stringResource(id = R.string.error_screen_content_body)

        ErrorScreen(
            title = title,
            body = body,
            onClickBack = navActions.navigateUp,
        )
    }
}
