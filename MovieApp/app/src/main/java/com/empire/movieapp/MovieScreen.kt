package com.empire.movieapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.empire.movieapp.ui.theme.MovieAppTheme

@Composable
fun MovieScreen() {
    val selectedTabIndex by remember {
        mutableIntStateOf(1)
    }
    Scaffold(
        modifier = Modifier.padding(16.dp),
        topBar = {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Icon(imageVector = Icons.Default.Menu, contentDescription = "")
                Text(text = "Movie", fontWeight = FontWeight.Bold)
                Icon(imageVector = Icons.Default.Search, contentDescription = "")
            }
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(top = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                itemsIndexed(tabs) { index, tabText ->
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(if (index == 1) Color(0xFFfe5639) else Color.White)
                    ) {
                        Text(
                            text = tabText,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                            color = if (index == 1) Color.White else Color.Gray
                        )
                    }
                }
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(vertical = 16.dp)
            ) {
                repeat(5) {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        Card(
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .fillMaxWidth(),
                            colors = CardDefaults.cardColors(
                                containerColor = Color.White
                            ),
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 8.dp
                            )

                        ) {
                            Column(
                                modifier = Modifier
                                    .padding(start = 148.dp)
                                    .padding(16.dp),
                                verticalArrangement =
                                Arrangement
                                    .spacedBy(4.dp)
                            ) {
                                Row(modifier = Modifier.fillMaxWidth()) {
                                    Spacer(modifier = Modifier.weight(1f))
                                    Icon(
                                        imageVector = Icons.Default.FavoriteBorder,
                                        contentDescription = "",
                                        tint = Color(0xFFfe5639),
                                        modifier = Modifier.size(14.dp)
                                    )
                                }
                                Text(
                                    text = "Monster Hunt",
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 14.sp
                                )
                                Text(
                                    text = "Comedy/Fantasy - 110min",
                                    modifier = Modifier.alpha(.75f),
                                    fontSize = 12.sp
                                )
                                Row {
                                    Text(
                                        text = "IMDB",
                                        modifier = Modifier.alpha(.75f),
                                        fontSize = 12.sp
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "8.1",
                                        fontWeight = FontWeight.SemiBold,
                                        fontSize = 12.sp
                                    )
                                }
                            }
                        }

                        Image(
                            painter = painterResource(id = R.drawable.movie_poster),
                            contentDescription = "",
                            modifier = Modifier
                                .width(140.dp)
                                .clip(
                                    RoundedCornerShape(4.dp)
                                )
                                .aspectRatio(3 / 4f),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }

        }

    }
}

val tabs = listOf("Science", "Comedy", "Disaster")


@Preview(showSystemUi = true, showBackground = true)
@Composable
fun MSP() {
    MovieAppTheme {
        MovieScreen()
    }
}
