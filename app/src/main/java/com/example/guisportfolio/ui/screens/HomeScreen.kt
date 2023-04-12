package com.example.guisportfolio.ui.screens


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
import androidx.compose.ui.unit.dp
import com.example.guisportfolio.R
import com.example.guisportfolio.ui.theme.GuisPortfolioTheme
import com.example.guisportfolio.ui.theme.md_theme_light_primary


enum class ContactType(@StringRes val info: Int) {
    WhatsApp(info = R.string.contactWhasApp),
    LinkedIn(info = R.string.contactLinkedin),
    Email(info = R.string.contactEmail)
}

@Composable
fun HomeScreen(
    onPortfolioButtonClicked: () -> Unit,
    onAboutButtonClicked: () -> Unit,
    modifier: Modifier = Modifier,

) {
    Column(verticalArrangement = Arrangement.SpaceEvenly, modifier = modifier
        .fillMaxSize()
        ) {
        Row(modifier = modifier.fillMaxWidth()) {
            Image(modifier = modifier
                .padding(top = 16.dp, start = 16.dp)
                .size(180.dp)
                .border(BorderStroke(4.dp, md_theme_light_primary), CircleShape)
                .clip(CircleShape),
                painter = painterResource(id = R.drawable.home_screen_photo),
                contentDescription = null,
                contentScale = ContentScale.FillHeight)
            Column(verticalArrangement = Arrangement.SpaceBetween) {
                Text(modifier = modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.18f)
                    .padding(20.dp),
                    text = stringResource(R.string.home_screen_about_me),
                    style = MaterialTheme.typography.headlineMedium,
                    textAlign = TextAlign.Center)
                Text(modifier = modifier
                    .fillMaxWidth()
                    .padding(4.dp),
                    text = stringResource(R.string.title_translator),
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center)
                Text(modifier = modifier.fillMaxWidth(),
                    text = stringResource(R.string.title_dev),
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center)
            }
        }

        Text(modifier = modifier
            .fillMaxWidth()
            .padding(start = 8.dp),
            text = stringResource(R.string.about_me),
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Start)
        Text(modifier = modifier
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp)
            .verticalScroll(rememberScrollState()),
            text = stringResource(R.string.about_me_info),
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Justify)

        ContactInfo()

        Row(modifier = Modifier.fillMaxWidth()) {
            Button(modifier = Modifier
                .fillMaxWidth()
                .weight(2f)
                .padding(4.dp)
                .sizeIn(50.dp),
                onClick = onPortfolioButtonClicked) {
                Text(stringResource(R.string.portfolio_button))
            }
            Button(modifier = Modifier
                .fillMaxWidth()
                .weight(2f)
                .padding(4.dp)
                .sizeIn(50.dp),
                onClick = onAboutButtonClicked) {
                Text(stringResource(R.string.about_app_button))
            }
        }
    }
}

@Composable
fun ContactInfo(modifier: Modifier = Modifier) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = modifier.padding(8.dp),
        elevation = CardDefaults.outlinedCardElevation(4.dp)
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
                    .padding(8.dp)

            ) {
                Text(
                    modifier = modifier.align(Alignment.CenterVertically),
                    text = stringResource(R.string.contact)
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
    Row(modifier = Modifier.padding(4.dp)) {
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
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
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
    GuisPortfolioTheme {
        HomeScreen(onPortfolioButtonClicked = { }, onAboutButtonClicked = { })
    }
}

//@Preview(showSystemUi = true, locale = "pt")
//@Composable
//fun HomeScreenPreviewPT() {
//    GuisPortfolioTheme {
//
//        HomeScreen(onPortfolioButtonClicked = { }, onAboutButtonClicked = { })
//    }
//}