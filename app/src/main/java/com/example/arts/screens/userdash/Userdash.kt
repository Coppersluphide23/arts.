package com.example.arts.screens.userdash

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.arts.ViewModel.ArtViewModel
import com.example.arts.ViewModel.AuthViewModel
import com.example.arts.models.Art
import com.example.arts.models.categoryList
import com.example.arts.navigation.ROUTE_PAINTDETAILS
import com.example.arts.navigation.ROUTE_PROFILE

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserDashboardSCreen(navController: NavHostController) {
    val context = LocalContext.current
    val myArtViewModel = ArtViewModel(navController, context)
    val myauthviewmodel = AuthViewModel(navController, context)
    var username by remember { mutableStateOf("loading..") }
    val products = remember { mutableStateListOf<Art>() }
    var selectedCategory by remember { mutableStateOf("All") }
    var searchText by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        myArtViewModel.allProducts(products)
        myauthviewmodel.getCurrentUsername { username = it }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("User Dashboard") },
                colors = topAppBarColors(
                    containerColor = Color(0xFFEDADE0),
                    titleContentColor = Color.Black
                ),
                actions = {
                    IconButton(onClick = { myauthviewmodel.signout() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                            contentDescription = "Logout"
                        )
                    }
                }
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = Color(0XFFADEDBA)
            ) {
                NavigationBarItem(
                    selected = true,
                    onClick = {},
                    icon = {
                        Icon(
                            Icons.Default.Home,
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
                            Icons.Default.Person,
                            contentDescription = "Person icon"
                        )
                    },
                    label = { Text("Profile", color = Color(0XFF994A3C)) }
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = "Welcome!! $username",
                color = Color(0xFF994A3C),
                style = MaterialTheme.typography.headlineLarge
            )
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = searchText,
                onValueChange = { searchText = it },
                placeholder = { Text("Search paintings...") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search icon"
                    )
                },
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Categories Section (Horizontal LazyRow)
            Text(
                text = "Categories",
                color = Color(0xFF994A3C),
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(8.dp))

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(categoryList) { category ->
                    val isSelected = selectedCategory.equals(category.name, ignoreCase = true)
                    Card(
                        shape = RoundedCornerShape(12.dp),
                        elevation = CardDefaults.cardElevation(if (isSelected) 8.dp else 2.dp),
                        border = if (isSelected) BorderStroke(3.dp, Color(0xFF994A3C)) else null,
                        modifier = Modifier
                            .width(160.dp)
                            .height(120.dp)
                            .clickable { selectedCategory = category.name }
                    ) {
                        Box(modifier = Modifier.fillMaxSize()) {
                            Image(
                                painter = painterResource(id = category.imageRes),
                                contentDescription = category.name,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(RoundedCornerShape(12.dp))
                            )
                            // Dark overlay for text legibility
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(Color.Black.copy(alpha = 0.45f))
                            )
                            Column(
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .padding(4.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = category.name,
                                    color = Color.White,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center
                                )
                                if (isSelected) {
                                    Text(
                                        text = "Selected",
                                        color = Color(0xFFEDADE0),
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Filter products based on search text and selected category
            val filteredProducts = products.filter { product ->
                val matchesSearch = searchText.isBlank() ||
                        product.name.contains(searchText, ignoreCase = true) ||
                        product.description.contains(searchText, ignoreCase = true)

                val matchesCategory = selectedCategory.equals("All", ignoreCase = true) ||
                        product.category.equals(selectedCategory, ignoreCase = true)

                matchesSearch && matchesCategory
            }

            Text(
                text = if (selectedCategory.equals("All", ignoreCase = true)) "Featured Paintings" else "$selectedCategory Paintings",
                color = Color(0xFF994A3C),
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(8.dp))

            if (filteredProducts.isEmpty()) {
                Text(
                    text = "No paintings found in this category.",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(vertical = 16.dp)
                )
            } else {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(filteredProducts) { product ->
                        Card(
                            modifier = Modifier
                                .width(200.dp)
                                .clickable {
                                    navController.navigate("$ROUTE_PAINTDETAILS/${product.id}")
                                },
                            shape = RoundedCornerShape(12.dp),
                            elevation = CardDefaults.cardElevation(8.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(8.dp)
                            ) {
                                Image(
                                    painter = rememberAsyncImagePainter(product.imageUrl),
                                    contentDescription = product.name,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(220.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                        .clickable {
                                            navController.navigate("$ROUTE_PAINTDETAILS/${product.id}")
                                        }
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = product.name,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                if (product.category.isNotBlank()) {
                                    Text(
                                        text = product.category,
                                        fontSize = 12.sp,
                                        color = Color(0xFF994A3C),
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }
                                Text(
                                    text = product.description,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Normal
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UserDashboardPreview() {
    UserDashboardSCreen(rememberNavController())
}
