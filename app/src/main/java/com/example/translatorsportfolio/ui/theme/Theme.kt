package com.example.translatorsportfolio.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable


@Composable
fun PortfolioTheme(
    windowSizeClass: WindowsSizeClass,
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit,
) {
    val colors = if (!useDarkTheme) LightColors else DarkColors

    val orientation = when {
        windowSizeClass.width.size > windowSizeClass.height.size -> Orientation.Landscape
        else -> Orientation.Portrait
    }

    val mainSize = when(orientation) {
        Orientation.Portrait -> windowSizeClass.width
        Orientation.Landscape -> windowSizeClass.height
    }

    val dimensions = when(mainSize) {
        is WindowSize.Small -> smallDimensions
        is WindowSize.Compact -> compactDimensions
        is WindowSize.Medium -> mediumDimensions
        else -> largeDimensions
    }

    val typography = when(mainSize) {
        is WindowSize.Small -> typographySmall
        is WindowSize.Compact -> typographyCompact
        is WindowSize.Medium -> typographyMedium
        else -> typographyLarge
    }

    ProvideAppUtils(dimensions = dimensions, orientation = orientation) {
        MaterialTheme(
            colorScheme = colors,
            content = content,
            typography = typography
        )
    }
}

object AppTheme {
    val dimens: Dimensions
    @Composable
    get() = LocalAppDimens.current

    val orient: Orientation
    @Composable
    get() = LocalOrientation.current

}
