package com.example.arts.screens.userdash



import android.R.attr.icon
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
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
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.arts.R
import com.example.arts.ViewModel.AuthViewModel
import com.example.arts.ViewModel.ArtViewModel
import com.example.arts.models.Art
//import com.example.firstapp.navigation.ROUTE_PROFILE

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserDashboard(navController: NavHostController){
    var context = LocalContext.current
    var myproductViewModel = ArtViewModel(navController, context)
    var myauthviewmodel = AuthViewModel(navController, context)
    var username by remember { mutableStateOf("loading..") }
    var products = remember { mutableStateListOf<Art>() }
    //fetch products from firebase and logged in user
    LaunchedEffect(Unit) {
        myproductViewModel.allProducts(products)
        myauthviewmodel.getCurrentUsername { username = it }
    }
    //add a scaffold
    Scaffold(
        //add topbar
        topBar = {
            TopAppBar(title = {Text("User Dashboard")},
                colors = topAppBarColors(
                    containerColor = Color(0xFFEDADE0),
                    titleContentColor = Color.Black
                ),
                actions = {
                    IconButton(onClick = { myauthviewmodel.signout() }) {
                        Icon(imageVector = Icons.Default.ExitToApp,
                            contentDescription = "Logout",)
                    }
                }
            )
        },
        //bottom bar
        bottomBar = {
            NavigationBar(
                containerColor = Color(0XFFADEDBA),
            ){
                NavigationBarItem(
                    selected = true,
                    onClick = {},
                    icon = {
                        Icon(Icons.Default.Home,
                            contentDescription = "Homeicon")},
                    label = { Text("Home",color= Color(0xFF994A3C))}
                )
                NavigationBarItem(
                    selected =true,
                    onClick = {navController.navigate(ROUTE_PROFILE)},
                    icon = {Icon(Icons.Default.Person,
                        contentDescription = "Person icon")},
                    label = { Text("Profile",color= Color(0XFF994A3C))},
                )
                NavigationBarItem(
                    selected =true,
                    onClick = {},
                    icon = {Icon(Icons.Default.ShoppingCart,
                        contentDescription = "Cart icon")},
                    label = { Text("Cart",color= Color(0xFF994A3C))}
                )

            }
        }
    ) {
            innerpadding->
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(innerpadding)
                .padding(16.dp)
        ){
            Text(
                text = "Welcome!! ${username}",
                color = Color(0xFF994A3C),
                style = MaterialTheme.typography.headlineLarge
            )
            Spacer( modifier = Modifier.height(16.dp))
            var searchText by remember { mutableStateOf("") }
            OutlinedTextField(
                value = searchText,
                onValueChange = {searchText = it},
                placeholder = { Text("Search products...")},
                leadingIcon = {Icon(imageVector = Icons.Default.Search,
                    contentDescription = "Search icon")},
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(text="featured products",
                color=Color(0xFF),
                style= MaterialTheme.typography.titleLarge)
            Spacer(modifier= Modifier.height(8.dp))
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ){
                items(products){product ->
                    //product card
                    Card(
                        modifier = Modifier
                            .width(200.dp),
                        shape = RoundedCornerShape(12.dp),
                        elevation = CardDefaults.cardElevation(8.dp)
                    ){
                        Column(
                            modifier = Modifier.padding(8.dp)
                        ){
                            Image(
                                painter = rememberAsyncImagePainter(product.imageUrl),
                                contentDescription = product.name,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(150.dp)
                            )
                            //product name
                            Text(text = product.name,
                                fontSize = 18.sp,
                                color = Color(0xFF),
                                fontWeight = FontWeight.Bold)
                            //product description
                            Text(text = product.description,fontSize = 14.sp)

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
    UserDashboard(rememberNavController())
}