package com.example.arts.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.AlertDialogDefaults.containerColor
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.arts.R
import com.example.arts.ViewModel.AuthViewModel
import com.example.arts.navigation.ROUTE_REGISTER

//import com.example.firstapp.ViewModel.AuthViewModel
//import com.example.firstapp.navigation.ROUTE_DASHBOARD
//import com.example.firstapp.navigation.ROUTE_REGISTER

@Composable
fun LoginScreen(navController: NavHostController){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center

    ) {
        Text(
            text = "LOGIN",
            fontSize = 40.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Default
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text="Already have an account?Login here",
            color= Color.Blue,
            fontSize = 20.sp
        )
        Image(
            painter = painterResource(id = R.drawable.mountain_adventure_logo),
            contentDescription = "pink tulips image",
            modifier = Modifier
                .size(200.dp)
                .clip(CircleShape)
        )
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Enter Email") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = "Email Icon"
                )
            }
        )
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Enter Password") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "Lock Icon"
                )
            }
        )
        Spacer(modifier = Modifier.height(10.dp))
        val context= LocalContext.current
        val myAuth= AuthViewModel(navController, context)
        Button(
            onClick = {
                myAuth.login(email,password)
            },
            modifier = Modifier.fillMaxWidth(),
            colors = buttonColors(containerColor = Color.Blue,
                contentColor = Color.White
            )

        )
        {
            Text("Login", fontSize = 20.sp)
        }
        // Text button
        TextButton(onClick = { navController.navigate(ROUTE_REGISTER) }) {
            Text(text = "Don't have an account? Sign up?", color = Color.Blue)
        }

        // Display the entered email
        Text(
            text = "email entered is $email"
        )
    }
}

@Preview
@Composable
fun LoginScreenPreview(){
    LoginScreen(rememberNavController())
}