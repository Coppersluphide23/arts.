package com.example.arts.screens.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.arts.R
import com.example.arts.navigation.ROUTE_LOGIN
import com.example.arts.navigation.ROUTE_ONBOARDING
import kotlinx.coroutines.launch
import kotlin.collections.get

data class OnboardingItem(
    val imageRes: Int,
    val title: String,
    val description: String
)

val OnboardingItems = listOf(
    OnboardingItem(
        imageRes = R.drawable.mountain_adventure_logo,
        title = "Welcome to Arts",
        description = "Discover amazing artwork and creative designs from artists around the world."
    ),
    OnboardingItem(
        imageRes = R.drawable.mountain_adventure_logo,
        title = "Explore & Create",
        description = "Connect with other creators and showcase your artistic portfolio."
    ),
    OnboardingItem(
        imageRes = R.drawable.mountain_adventure_logo,
        title = "Get Started Today",
        description = "Sign up now and join our growing community of art lovers."
    )
)

@Composable
fun OnboardingScreen(navController: NavHostController) {
    val pagerState = rememberPagerState(pageCount = { OnboardingItems.size })
    val scope = rememberCoroutineScope()
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //show skip if not in the last page
        if (pagerState.currentPage!=OnboardingItems.lastIndex){
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.End
            ){
                TextButton(onClick =  {
                    navController.navigate(ROUTE_LOGIN){
                        popUpTo(ROUTE_ONBOARDING){
                            inclusive = true
                        }
                    }
                },modifier = Modifier
                    .height(55.dp)
                    .width(100.dp)){
                    Text("Skip", fontSize = 18.sp, color = Color.Blue, fontWeight = FontWeight.Bold)
                }
            }


        }
        else{
            Spacer(modifier = Modifier.height(40.dp))
        }
        //HORIZONTAL PAGES
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f)
        ) { page ->
            val item = OnboardingItems[page]
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                //image
                Image(
                    painter = painterResource(id = item.imageRes),
                    contentDescription = item.title,
                    modifier = Modifier.size(200.dp)
                )
                Spacer(modifier = Modifier.height(15.dp))
                //title
                Text(
                    text = item.title,
                    fontSize = 26.sp,
                    color = Color.Blue,
                    fontWeight = FontWeight.Bold,
                )
                Spacer(modifier = Modifier.height(15.dp))
                //DESCRIPTION
                Text(
                    text = item.description,
                    fontSize = 18.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Center
                )
            }
        }
        Spacer(modifier = Modifier.height(15.dp))
        //page indicators
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.Center,
        ) {
            repeat(OnboardingItems.size) { index ->
                val isSelected = pagerState.currentPage == index
                Box(
                    modifier = Modifier
                        .padding(4.dp)
                        .size(
                            if (isSelected) 10.dp else 8.dp
                        )
                        .clip(CircleShape)
                        .background(
                            if (isSelected) Color(0xFF4E5F87) else Color.Gray
                        )
                )
            }
        }
        Spacer(modifier = Modifier.height(15.dp))
        //Next /Get started button
        Button(
            onClick = {
                if (pagerState.currentPage < OnboardingItems.size - 1) {
                    scope.launch {
                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                    }
                } else {
                    navController.navigate(ROUTE_LOGIN)
                }
            },
            modifier = Modifier.fillMaxWidth()
        )
        {
            Text(
                text = if (pagerState.currentPage == OnboardingItems.size - 1) {
                    "Get Started"
                } else {
                    "Next"
                }
            )

        }

        Spacer(modifier = Modifier.height(60.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun OnboardingScreenPreview() {
    OnboardingScreen(rememberNavController())
}