package hr.from.ivantoplak.pokemonapp.ui.error

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import hr.from.ivantoplak.pokemonapp.R
import hr.from.ivantoplak.pokemonapp.ui.common.PokemonTopAppBar
import hr.from.ivantoplak.pokemonapp.ui.theme.PokemonAppTheme
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ErrorScreen(
    modifier: Modifier = Modifier,
    title: String = stringResource(id = R.string.error_screen_content_title),
    body: String = stringResource(id = R.string.error_screen_content_body),
    onClickBack: () -> Unit = {},
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            PokemonTopAppBar(
                title = stringResource(id = R.string.error_screen_title),
                onClickBack = onClickBack,
            )
        },
    ) { innerPadding ->
        ErrorScreenContent(
            modifier = Modifier.padding(innerPadding),
            title = title,
            body = body,
            onClickBack = onClickBack,
        )
    }
}

@Composable
private fun ErrorScreenContent(
    modifier: Modifier = Modifier,
    title: String = "",
    body: String = "",
    onClickBack: () -> Unit = {},
) {
    ConstraintLayout(
        constraintSet = getConstraints(),
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
    ) {
        // error image
        Image(
            painter = painterResource(id = R.drawable.ic_error_outline),
            contentDescription = stringResource(id = R.string.error_image),
            modifier = Modifier
                .layoutId("image_error")
                .size(dimensionResource(id = R.dimen.error_image_size)),
            colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.error),
        )

        // title text
        Text(
            text = title,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .layoutId("text_title")
                .padding(horizontal = 16.dp),
        )

        // body text
        Text(
            text = body,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .layoutId("text_body")
                .padding(horizontal = 16.dp),
        )

        // go back button
        Button(
            onClick = { onClickBack() },
            modifier = Modifier.layoutId("button_go_back"),
        ) {
            Text(
                text = stringResource(id = R.string.go_back).uppercase(Locale.getDefault()),
                style = MaterialTheme.typography.labelLarge,
            )
        }
    }
}

private fun getConstraints(): ConstraintSet {
    return ConstraintSet {
        val imageError = createRefFor("image_error")
        val textTitle = createRefFor("text_title")
        val textBody = createRefFor("text_body")
        val buttonGoBack = createRefFor("button_go_back")

        constrain(imageError) {
            top.linkTo(parent.top, margin = 32.dp)
            start.linkTo(parent.start, margin = 16.dp)
            end.linkTo(parent.end, margin = 16.dp)
        }

        constrain(textTitle) {
            top.linkTo(imageError.bottom, margin = 16.dp)
            start.linkTo(parent.start, margin = 16.dp)
            end.linkTo(parent.end, margin = 16.dp)
        }

        constrain(textBody) {
            top.linkTo(textTitle.bottom, margin = 16.dp)
            start.linkTo(parent.start, margin = 16.dp)
            end.linkTo(parent.end, margin = 16.dp)
        }

        constrain(buttonGoBack) {
            start.linkTo(parent.start, margin = 16.dp)
            end.linkTo(parent.end, margin = 16.dp)
            linkTo(
                top = textBody.bottom,
                bottom = parent.bottom,
                topMargin = 16.dp,
                bottomMargin = 32.dp,
                bias = 0F,
            )
        }
    }
}

@Preview(name = "ErrorScreenPortrait", showBackground = true)
@Preview(name = "ErrorScreenLandscape", showBackground = true, widthDp = 720, heightDp = 360)
@Composable
fun ErrorScreenPreview() {
    PokemonAppTheme {
        GetErrorScreen()
    }
}

@Composable
private fun GetErrorScreen() {
    ErrorScreen(
        title = stringResource(id = R.string.error_screen_content_title),
        body = stringResource(id = R.string.error_screen_content_body),
    )
}
