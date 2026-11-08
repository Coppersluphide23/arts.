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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
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

data class OnboardingItem(
    val imageRes: Int,
    val title: String,
    val description: String
)

val OnboardingItems = listOf(
    OnboardingItem(
        imageRes = R.drawable.monetsunset,
        title = "Welcome to Arts",
        description = "Discover amazing artpieces from renowned artists and historic movements."
    ),
    OnboardingItem(
        imageRes = R.drawable.monetlizzatmillz,
        title = "Discover Fine Art",
        description = "Explore a curated gallery of artwork from different parts of the globe."
    ),
    OnboardingItem(
        imageRes = R.drawable.arancio,
        title = "Get Started Today",
        description = "Sign up now and immerse yourself in the world of fine art."
    )
)

@Composable
fun OnboardingScreen(navController: NavHostController) {
    val pagerState = rememberPagerState(pageCount = { OnboardingItems.size })
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Show skip if not on the last page
        if (pagerState.currentPage != OnboardingItems.lastIndex) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(
                    onClick = {
                        navController.navigate(ROUTE_LOGIN) {
                            popUpTo(ROUTE_ONBOARDING) { inclusive = true }
                        }
                    }
                ) {
                    Text(
                        "Skip",
                        fontSize = 16.sp,
                        color = Color(0xFF994A3C),
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        } else {
            Spacer(modifier = Modifier.height(48.dp))
        }

        // Horizontal Pager
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f)
        ) { page ->
            val item = OnboardingItems[page]
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Card(
                    shape = RoundedCornerShape(20.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                    modifier = Modifier.size(240.dp)
                ) {
                    Image(
                        painter = painterResource(id = item.imageRes),
                        contentDescription = item.title,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
                Spacer(modifier = Modifier.height(28.dp))
                Text(
                    text = item.title,
                    fontSize = 26.sp,
                    color = Color(0xFF994A3C),
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = item.description,
                    fontSize = 16.sp,
                    color = Color(0xFF4A4A4A),
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Center,
                    lineHeight = 22.sp
                )
            }
        }

        // Page indicators
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            repeat(OnboardingItems.size) { index ->
                val isSelected = pagerState.currentPage == index
                Box(
                    modifier = Modifier
                        .padding(4.dp)
                        .height(8.dp)
                        .width(if (isSelected) 24.dp else 8.dp)
                        .clip(CircleShape)
                        .background(
                            if (isSelected) Color(0xFFEDADE0) else Color(0xFFE0E0E0)
                        )
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Next / Get Started button
        Button(
            onClick = {
                if (pagerState.currentPage < OnboardingItems.size - 1) {
                    scope.launch {
                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                    }
                } else {
                    navController.navigate(ROUTE_LOGIN) {
                        popUpTo(ROUTE_ONBOARDING) { inclusive = true }
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .height(50.dp),
            shape = RoundedCornerShape(25.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF507FE5),
                contentColor = Color.White
            )
        ) {
            Text(
                text = if (pagerState.currentPage == OnboardingItems.size - 1) "Get Started" else "Next",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(36.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun OnboardingScreenPreview() {
    OnboardingScreen(rememberNavController())
}