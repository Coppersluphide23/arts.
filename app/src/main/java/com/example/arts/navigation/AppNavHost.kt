package com.example.arts.navigation

import com.example.arts.screens.onboarding.OnboardingScreen
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.arts.screens.dashboard.DashboardScreen
import com.example.arts.screens.login.LoginScreen
import com.example.arts.screens.paint.AddPaintScreen
import com.example.arts.screens.paint.PaintListScreen
import com.example.arts.screens.paint.UpdatePaintScreen
import com.example.arts.screens.profile.ProfileScreen
import com.example.arts.screens.profile.ProfileScreenPreview
import com.example.arts.screens.register.RegisterScreen
import com.example.arts.screens.userdash.UserDashboard
import com.example.arts.screens.userdash.UserDashboardPreview

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = ROUTE_ONBOARDING,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable(ROUTE_ONBOARDING) {
            OnboardingScreen(navController)
        }
        composable(ROUTE_LOGIN) {
            RegisterScreen(navController)
        }
        composable(ROUTE_REGISTER) {
            LoginScreen(navController)
        }
        composable(ROUTE_DASHBOARD) {
            DashboardScreen(navController)
        }
        composable(ROUTE_ADDPAINT){
            AddPaintScreen(navController)
        }
        composable(ROUTE_PAINTLIST){
            PaintListScreen(navController)
        }
        composable(ROUTE_UPDATEPAINTS + "/{productId}") {backstackEntry ->
            val artsId = backstackEntry.arguments?.getString("productId")!!
            UpdatePaintScreen(navController, productId = artsId)
        }
        composable(ROUTE_USERDASH){
            UserDashboard(navController)
        }
        composable(ROUTE_PROFILE){
            ProfileScreen(navController)
        }




    }
}
