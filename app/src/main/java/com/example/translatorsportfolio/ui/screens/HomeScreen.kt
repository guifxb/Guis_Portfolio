package com.example.translatorsportfolio.ui.screens


import androidx.annotation.StringRes
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.translatorsportfolio.R
import com.example.translatorsportfolio.ui.theme.AppTheme
import com.example.translatorsportfolio.ui.theme.rememberWindowsSizeClass
import com.example.translatorsportfolio.ui.theme.PortfolioTheme
import com.example.translatorsportfolio.ui.theme.md_theme_light_primary


enum class ContactType(@StringRes val info: Int) {
    WhatsApp(info = R.string.contactWhasApp),
    LinkedIn(info = R.string.contactLinkedin),
    Email(info = R.string.contactEmail)
}

@Composable
fun HomeScreen(
    onPortfolioButtonClicked: () -> Unit,
    onAboutButtonClicked: () -> Unit,
    modifier: Modifier = Modifier
    ) {
    Column(
        verticalArrangement = Arrangement.SpaceBetween, modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Row(modifier = modifier.fillMaxWidth()) {
            Image(
                modifier = modifier
                    .padding(top = AppTheme.dimens.medium, start = AppTheme.dimens.medium)
                    .size(AppTheme.dimens.homeScreenPic)
                    .border(
                        BorderStroke(AppTheme.dimens.small, md_theme_light_primary),
                        CircleShape
                    )
                    .clip(CircleShape),
                painter = painterResource(id = R.drawable.home_screen_photo),
                contentDescription = null,
                contentScale = ContentScale.FillHeight
            )
            Column(verticalArrangement = Arrangement.SpaceBetween) {
                Text(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(AppTheme.dimens.medium),
                    text = stringResource(R.string.home_screen_about_me),
                    style = MaterialTheme.typography.headlineLarge,
                    textAlign = TextAlign.Center
                )
                Text(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(AppTheme.dimens.medium),
                    text = stringResource(R.string.title_translator),
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center
                )
                Text(
                    modifier = modifier.fillMaxWidth(),
                    text = stringResource(R.string.title_dev),
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center
                )
            }
        }

        Text(
            modifier = modifier
                .fillMaxWidth()
                .padding(start = AppTheme.dimens.medium),
            text = stringResource(R.string.about_me),
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Start
        )
        Text(
            modifier = modifier
                .fillMaxWidth()
                .padding(start = AppTheme.dimens.medium, end = AppTheme.dimens.medium),
            text = stringResource(R.string.about_me_info),
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Justify
        )

        ContactInfo()

        Row(modifier = Modifier.fillMaxWidth()) {
            Button(
                modifier = Modifier
                    .padding(AppTheme.dimens.medium)
                    .wrapContentWidth()
                    .fillMaxWidth(0.5f)
                    ,
                onClick = onPortfolioButtonClicked
            ) {
                Text(
                    stringResource(R.string.portfolio_button),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Button(
                modifier = Modifier
                    .padding(AppTheme.dimens.medium)
                    .wrapContentWidth()
                    .fillMaxWidth()
                    ,
                onClick = onAboutButtonClicked
            ) {
                Text(
                    text = stringResource(R.string.about_app_button),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Composable
fun ContactInfo(modifier: Modifier = Modifier) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = modifier.padding(AppTheme.dimens.medium),
        elevation = CardDefaults.outlinedCardElevation(AppTheme.dimens.medium)
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
        ) {
            Row(
                modifier
                    .fillMaxWidth()
                    .padding(AppTheme.dimens.medium)

            ) {
                Text(
                    modifier = modifier.align(Alignment.CenterVertically),
                    text = stringResource(R.string.contact),
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.weight(1f))
                ContactIconButton(
                    expanded = expanded,
                    onClick = {
                        expanded = !expanded
                    },
                )
            }
        }
        if (expanded) {
            enumValues<ContactType>().forEach { InfoCard(info = it) }
        }
    }
}

@Composable
fun InfoCard(info: ContactType) {
    Row(modifier = Modifier.padding(AppTheme.dimens.medium)) {
        Icon(
            imageVector = when (info) {
                ContactType.Email -> Icons.Filled.Email
                ContactType.WhatsApp -> Icons.Filled.Phone
                ContactType.LinkedIn -> Icons.Filled.ContactPage
            },
            contentDescription = null
        )
        Spacer(modifier = Modifier.weight(0.1f))

        Text(
            text = stringResource(info.info),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
private fun ContactIconButton(
    expanded: Boolean,
    onClick: () -> Unit
) {
    IconButton(onClick = onClick) {
        Icon(
            imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
            contentDescription = null
        )
    }
}


@Preview(showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    val window = rememberWindowsSizeClass()
    PortfolioTheme(window) {
        HomeScreen(onPortfolioButtonClicked = { }, onAboutButtonClicked = { })
    }
}

