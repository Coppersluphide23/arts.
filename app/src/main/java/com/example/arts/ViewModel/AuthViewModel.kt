package com.example.arts.ViewModel

import android.content.Context
import android.widget.Toast
import androidx.navigation.NavHostController
import com.example.arts.models.User
import com.example.arts.navigation.ROUTE_DASHBOARD
//import com.example.arts.navigation.ROUTE_DASHBOARD
import com.example.arts.navigation.ROUTE_LOGIN
import com.example.arts.navigation.ROUTE_REGISTER
//import com.example.arts.navigation.ROUTE_USERDASHBOARD
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.auth.AuthState
import com.google.firebase.database.FirebaseDatabase

class AuthViewModel ( var navController: NavHostController, var context : Context){
    var mAuth = FirebaseAuth.getInstance()
    //register function to create new users
    fun signup(fullName:String,email:String,password:String,confirmPassword:String){
        //validation
        if(email.isBlank() || password.isBlank() || confirmPassword.isBlank()){
            Toast.makeText(context,"Email and Password can't be blank",Toast.LENGTH_LONG).show()
            return
        }else if(password != confirmPassword){
            Toast.makeText(context,"Password and Confirm Password do not match",Toast.LENGTH_LONG).show()
        }else{
            //create user
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener{
                if(it.isSuccessful){
                   val userdata = User( fullName, email, password, mAuth.currentUser!!.uid,"user")
                    //save user data to real-time database
                    val regRef= FirebaseDatabase.getInstance().getReference().child("Users/"+mAuth.currentUser!!.uid)
                    regRef.setValue(userdata).addOnCompleteListener {
                        if(it.isSuccessful){
                            Toast.makeText(context,"Registration Successful",Toast.LENGTH_LONG).show()
                            //navigate to Login
                            navController.navigate(ROUTE_LOGIN)
                        }else{
                            Toast.makeText(context,"${it.exception!!.message})",Toast.LENGTH_LONG).show()
                            navController.navigate(ROUTE_REGISTER)
                        }
                    }
                }else{
                    navController.navigate(ROUTE_REGISTER)
                }

            }

        }

    }
    //login function
    fun login(email : String, password : String){
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener {
            if (it.isSuccessful) {
                val userId = mAuth.currentUser?.uid
                //fetch user role from database
                FirebaseDatabase.getInstance().reference.child("Users").child(userId!!).get()
                    .addOnSuccessListener { snapshot ->
                        var role = snapshot.child("role").value.toString()
                        //role based navigation
                        if (role == "admin") {
                            navController.navigate(ROUTE_DASHBOARD)
                        } else {
                            //navController.navigate(ROUTE_USERDASHBOARD)
                        }
                        Toast.makeText(context, "Login Successful", Toast.LENGTH_LONG).show()
                    }
            }else{
                Toast.makeText(context,it.exception?.message?:"error logging in",Toast.LENGTH_LONG).show()
            }
        }
    }

 //signout function
 fun signout(){
     mAuth.signOut()
     navController.navigate(ROUTE_LOGIN)
     { popUpTo(0) }
 }
    //getting current username
    fun getCurrentUsername(onResult:(String) -> Unit){
        val userId = mAuth.currentUser?.uid
        if (userId==null){
            onResult("user")
            return
        }
        FirebaseDatabase.getInstance().getReference("Users")
            .child(userId)
            .get()
            .addOnSuccessListener {snapshot ->
                val fullName=snapshot.child("fullName").getValue(String::class.java)
                onResult(fullName ?: "user")
            }


    }
    //get current user profile
    fun getUserProfile(onResult: (User?) -> Unit){
        //get current user ID
        val userId=mAuth.currentUser?.uid
        if(userId==null){
            onResult(null)
            return
        }
        //fetch user data from firebase database
        FirebaseDatabase.getInstance().getReference("Users")
            .child(userId)
            .get()
            .addOnSuccessListener { snapshot ->
                if(snapshot.exists()) {
                    val user = snapshot.getValue(User::class.java)
                    //return the user profile
                    onResult(user)
                }else {
                    onResult(null)
                }
            }

            .addOnFailureListener {
                onResult(null)
            }

    }

}
