package com.example.arts.screens.favorites

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.arts.ViewModel.ArtViewModel
import com.example.arts.models.Art
import com.example.arts.navigation.ROUTE_PAINTDETAILS
import com.example.arts.navigation.ROUTE_PROFILE
import com.example.arts.navigation.ROUTE_USERDASH
import com.example.arts.utils.FavoritesManager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(navController: NavHostController) {
    val context = LocalContext.current
    val myArtViewModel = remember { ArtViewModel(navController, context) }
    val allProducts = remember { mutableStateListOf<Art>() }
    var favoriteIds by remember { mutableStateOf(FavoritesManager.getFavoriteIds(context)) }

    LaunchedEffect(Unit) {
        myArtViewModel.allProducts(allProducts)
    }

    val favoriteProducts = allProducts.filter { favoriteIds.contains(it.id) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Favorite Artworks",
                        color = Color(0xFF994A3C),
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = topAppBarColors(
                    containerColor = Color(0xFFEDADE0)
                )
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = Color(0XFFADEDBA)
            ) {
                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate(ROUTE_USERDASH) },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Home,
                            contentDescription = "Home icon"
                        )
                    },
                    label = { Text("Home", color = Color(0xFF994A3C)) }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate(ROUTE_PROFILE) },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Person icon"
                        )
                    },
                    label = { Text("Profile", color = Color(0xFF994A3C)) }
                )
                NavigationBarItem(
                    selected = true,
                    onClick = {},
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = "Favorites icon",
                            tint = Color(0xFFE53935)
                        )
                    },
                    label = { Text("Favorites", color = Color(0xFF994A3C)) }
                )
            }
        }
    ) { innerPadding ->
        if (favoriteProducts.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.FavoriteBorder,
                        contentDescription = "Empty Favorites",
                        tint = Color.Gray,
                        modifier = Modifier.size(80.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "No Favorite Artworks Yet",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF994A3C)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Tap the heart icon on any painting to save your favorite artworks here!",
                        fontSize = 14.sp,
                        color = Color.Gray,
                        textAlign = TextAlign.Center
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(favoriteProducts) { item ->
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                navController.navigate("$ROUTE_PAINTDETAILS/${item.id}")
                            }
                    ) {
                        Box {
                            Image(
                                painter = rememberAsyncImagePainter(item.imageUrl),
                                contentDescription = item.name,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(240.dp)
                                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                            )
                            IconButton(
                                onClick = {
                                    FavoritesManager.toggleFavorite(context, item.id)
                                    favoriteIds = FavoritesManager.getFavoriteIds(context)
                                },
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .padding(8.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Favorite,
                                    contentDescription = "Remove Favorite",
                                    tint = Color(0xFFE53935),
                                    modifier = Modifier.size(28.dp)
                                )
                            }
                        }
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = item.name,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Black
                                )
                                if (item.category.isNotBlank()) {
                                    Card(
                                        shape = RoundedCornerShape(8.dp),
                                        colors = CardDefaults.cardColors(
                                            containerColor = Color(0xFFEDADE0).copy(alpha = 0.5f)
                                        )
                                    ) {
                                        Text(
                                            text = item.category,
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.SemiBold,
                                            color = Color(0xFF832B1C),
                                            modifier = Modifier.padding(
                                                horizontal = 8.dp,
                                                vertical = 4.dp
                                            )
                                        )
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = item.description,
                                fontSize = 14.sp,
                                color = Color.DarkGray
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FavoritesScreenPreview() {
    FavoritesScreen(rememberNavController())
}
