package com.example.arts.screens.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(navController: NavHostController) {
    val context = LocalContext.current
    val myAuth = remember { AuthViewModel(navController, context) }
    var username by remember { mutableStateOf("User") }

    LaunchedEffect(Unit) {
        myAuth.getCurrentUsername { username = it }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Admin Dashboard") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFEDADE0),
                    titleContentColor = Color.Black
                ),
                actions = {
                    IconButton(onClick = { navController.navigate(ROUTE_PROFILE) }) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Profile"
                        )
                    }
                    IconButton(onClick = { myAuth.signout() }) {
                        Icon(
                            imageVector = Icons.Default.ExitToApp,
                            contentDescription = "Signout"
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
                    selected = false,
                    onClick = { navController.navigate(ROUTE_USERDASH) },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Home,
                            contentDescription = "Home"
                        )
                    },
                    label = { Text("Home", color = Color(0xFF994A3C)) }
                )
                NavigationBarItem(
                    selected = true,
                    onClick = {},
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Dashboard"
                        )
                    },
                    label = { Text("Admin", color = Color(0xFF994A3C)) }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate(ROUTE_PROFILE) },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Profile"
                        )
                    },
                    label = { Text("Profile", color = Color(0xFF994A3C)) }
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(ROUTE_ADDPAINT) },
                containerColor = Color(0xFFEDADE0)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Painting",
                    tint = Color.Black
                )
            }
        }
    ) { innerpadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(innerpadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Welcome, $username!",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF994A3C),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start
            )
            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                DashboardCard(
                    title = "Add Painting",
                    icon = Icons.Default.Add,
                    background = Color(0xFFEDADE0),
                    textColor = Color.Black,
                    onClick = { navController.navigate(ROUTE_ADDPAINT) }
                )
                DashboardCard(
                    title = "Paintings List",
                    icon = Icons.AutoMirrored.Filled.List,
                    background = Color(0XFFADEDBA),
                    textColor = Color(0xFF994A3C),
                    onClick = { navController.navigate(ROUTE_PAINTLIST) }
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                DashboardCard(
                    title = "User Dashboard",
                    icon = Icons.Default.Palette,
                    background = Color(0xFF507FE5),
                    textColor = Color.White,
                    onClick = { navController.navigate(ROUTE_USERDASH) }
                )
            }
        }
    }
}

@Composable
fun DashboardCard(
    title: String,
    icon: ImageVector,
    background: Color,
    textColor: Color,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .height(140.dp)
            .width(155.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = background),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        shape = RoundedCornerShape(18.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = textColor,
                modifier = Modifier.size(36.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = textColor,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DashboardScreenPreview() {
    DashboardScreen(rememberNavController())
}