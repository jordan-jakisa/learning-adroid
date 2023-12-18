package com.empire.haze_bottom_bar

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze
import dev.chrisbanes.haze.hazeChild

@Composable
fun HazeScreen() {
    var selectedTabIndex by remember { mutableIntStateOf(1) }
    val hazeState = remember { HazeState() }

    Scaffold(bottomBar = {
        Box(
            modifier = Modifier
                .padding(vertical = 24.dp, horizontal = 64.dp)
                .fillMaxWidth()
                .height(64.dp)
                .hazeChild(
                    state = hazeState, shape = CircleShape
                ).border(
                    width = Dp.Hairline,
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.White.copy(alpha = .8f),
                            Color.White.copy(alpha = .2f),
                        ),
                    ),
                    shape = CircleShape
                )
        ) {
            BottomBarTabs(tabs, selectedTab = selectedTabIndex, onTabSelected = {
                selectedTabIndex = tabs.indexOf(it)
            })
        }
    }) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .haze(
                    hazeState,
                    backgroundColor = MaterialTheme.colorScheme.background,
                    tint = Color.Black.copy(alpha = .2f),
                    blurRadius = 30.dp
                ),
            contentPadding = padding, verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(15) {
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(4 / 3f)
                        .padding(horizontal = 16.dp)
                        .clip(RoundedCornerShape(16.dp)),
                    painter = painterResource(id = R.drawable.rectangle_13),
                    contentDescription = "",
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}

@Composable
fun BottomBarTabs(
    tabs: List<BottomBarTab>, selectedTab: Int, onTabSelected: (BottomBarTab) -> Unit
) {
    CompositionLocalProvider(
        LocalTextStyle provides LocalTextStyle.current.copy(
            fontSize = 12.sp, fontWeight = FontWeight.Medium
        ), LocalContentColor provides Color.White
    ) {
        Row(modifier = Modifier.fillMaxSize()) {
            for (tab in tabs) {
                val alpha by animateFloatAsState(
                    targetValue = if (selectedTab == tabs.indexOf(tab)) 1f else .35f,
                    label = "alpha"
                )
                val scale by animateFloatAsState(
                    targetValue = if (selectedTab == tabs.indexOf(tab)) 1f else .98f,
                    visibilityThreshold = .000001f,
                    animationSpec = spring(
                        stiffness = Spring.StiffnessLow,
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                    ),
                    label = "scale"
                )
                Column(
                    modifier = Modifier
                        .scale(scale)
                        .alpha(alpha)
                        .fillMaxHeight()
                        .weight(1f)
                        .pointerInput(Unit) {
                            detectTapGestures { onTabSelected(tab) }
                        },
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(imageVector = tab.icon, contentDescription = "tab ${tab.title}")
                    Text(text = tab.title)
                }
            }
        }

    }
}

sealed class BottomBarTab(val title: String, val icon: ImageVector, val color: Color) {
    data object Profile : BottomBarTab(
        title = "Profile", icon = Icons.Rounded.Person, color = Color(0xFFFFA574)
    )

    data object Home : BottomBarTab(
        title = "Home", icon = Icons.Rounded.Home, color = Color(0xFFFA6FFF)
    )

    data object Settings : BottomBarTab(
        title = "Settings", icon = Icons.Rounded.Settings, color = Color(0xFFADFF64)
    )
}

val tabs = listOf(
    BottomBarTab.Profile, BottomBarTab.Home, BottomBarTab.Settings
)

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun HSP() {
    HazeScreen()
}