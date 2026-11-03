package com.example.arts.screens.dashboard


import android.R.attr.background
import android.R.attr.elevation
import android.R.attr.onClick
import android.R.attr.shape
import android.R.attr.title
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AlertDialogDefaults.containerColor
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CheckboxDefaults.colors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.arts.ViewModel.AuthViewModel
import com.example.arts.navigation.ROUTE_ADDPAINT
import com.example.arts.navigation.ROUTE_PAINTLIST
import com.example.arts.navigation.ROUTE_PROFILE
import com.example.arts.navigation.ROUTE_USERDASH

//import com.example.firstapp.navigation.ROUTE_ADDPRODUCT
//import com.example.firstapp.navigation.ROUTE_INTENTS
//import com.example.firstapp.navigation.ROUTE_PRODUCTLIST
//import com.example.firstapp.navigation.ROUTE_PROFILE
//import com.example.firstapp.navigation.ROUTE_USERDASHBOARD

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(navController: NavHostController) {
    var context = LocalContext.current
    val myAuth = remember { AuthViewModel (navController, context)} // Initialize AuthViewModel
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Dashboard")
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFEDADE0),
                    titleContentColor = Color.Black
                ),
                actions = {
                    IconButton(onClick = {navController.navigate (ROUTE_PROFILE)}) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Profile"
                        )
                    }
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Settings Icon"
                        )
                    }

                    IconButton(onClick = {""})
                    {
                        Icon(
                            imageVector = Icons.Default.ExitToApp,
                            contentDescription = "Signout"
                        )
                    }
                }
            )
        },

        // Bottom bar
        bottomBar = {
            NavigationBar (
                containerColor = Color(0XFFADEDBA)
            ){NavigationBarItem(
                selected = true,
                onClick = {navController.navigate(ROUTE_USERDASH)},
                icon = {
                    Icon(
                        imageVector = Icons.Default.Home,
                        contentDescription = "Home"

                    )
                },
                label = { Text("Home",color= Color(0xFF994A3C))}
            )
                NavigationBarItem(
                    selected = false,
                    onClick = {},
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Settings icon"
                        )
                    },
                    label = {
                        Text("Settings")
                    }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = {  navController.navigate(ROUTE_PROFILE)  },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Profile"
                        )
                    },
                    label = {
                        Text("Profile")
                    }
                )
            }
        },


        // Floating action button
        floatingActionButton = {
            FloatingActionButton(onClick = {navController.navigate(ROUTE_ADDPAINT)}) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add"
                )
            }
        }
    ) { innerpadding ->
        //column
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(innerpadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            var username by remember { mutableStateOf("loading ...") }
            LaunchedEffect(Unit) {
               // myAuth.getCurrentUsername { username = it }
            }
            Text("Welcome $username!!",
                fontSize = 28.sp,
                color = Color.Blue)
            Spacer(modifier = Modifier.height(16.dp))
            //row
            Row {
                DashboardCard(
                    title = "Add painting",
                    background = Color.Gray,
                    onClick = {navController.navigate(ROUTE_ADDPAINT) }
                )
                DashboardCard(
                    title = "Paintings list",
                    background = Color.Yellow,
                    onClick = {navController.navigate(ROUTE_PAINTLIST)  }
                )

            }
            Row {
                DashboardCard(
                    title = "User dashboard",
                    background = Color.Red,
                    onClick = {navController.navigate(ROUTE_USERDASH) }
                )
                DashboardCard(
                    title = "Blank",
                    background = Color.White,
                    onClick = { }
                )
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun DashboardScreenPreview() {
    DashboardScreen(rememberNavController())
}
//Dashboard card
@Composable
fun DashboardCard(
    title: String,
    background:Color,
    onClick: () -> Unit
){
    Card(
        modifier = Modifier
            .height(150.dp)
            .padding(8.dp)
            .width(150.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = background),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = title,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }

    }
}
@Preview(showBackground = true)
@Composable
fun DashboardCardPreview(){
    DashboardCard(
        title = "Opera",
        background = Color.LightGray,
        onClick = {}
    )
}