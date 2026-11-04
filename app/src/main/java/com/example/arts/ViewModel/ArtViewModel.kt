package com.example.arts.ViewModel

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.navigation.NavHostController
import com.example.arts.models.Art
import com.example.arts.navigation.ROUTE_PAINTLIST
//import com.example.arts.models.Product
//import com.example.arts.navigation.ROUTE_PRODUCTLIST
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONObject
import java.io.InputStream
import kotlin.jvm.java

class ArtViewModel (var navController: NavHostController, val context: Context) {
    var cloudinaryUrl = "https://api.cloudinary.com/v1_1/gb3zm0vd/upload"
    var uploadPreset = "products"
    val databasereference = FirebaseDatabase.getInstance().getReference("products")

    //functions
    //crud-create,read,update & delete
    //upload product to firebase realtime database
    fun addProduct(name: String, description: String, imageUri: Uri?, category: String) {
        val ref = databasereference.push()
        val currentUser = FirebaseAuth.getInstance().currentUser
        val userId = currentUser?.uid ?: ""
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val imageUrl = if (imageUri != null) {
                    uploadToCloudinary(context = context, uri = imageUri)
                } else {
                    ""
                }
                //product data to be stored in realtime db
                val productData = mapOf(
                    "id" to ref.key,
                    "name" to name,
                    "description" to description,
                    "category" to category,
                    "userId" to userId,
                    "imageUrl" to imageUrl
                )
                ref.setValue(productData).addOnCompleteListener {
                    if (it.isSuccessful){
                        Toast.makeText(context,"Product added successfully", Toast.LENGTH_LONG).show()
                        //navigate to product list
                        navController.navigate(ROUTE_PAINTLIST)

                    }
                    else{
                        Toast.makeText(context,"${it.exception?.message}", Toast.LENGTH_LONG).show()
                    }
                }
            } catch (e: Exception) {
                CoroutineScope(Dispatchers.Main).launch {
                    Toast.makeText(context, "Upload failed ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    //upload image to cloudinary function
    private fun uploadToCloudinary(context: Context, uri: Uri): String {
        val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
        val fileBytes = inputStream?.use { it.readBytes() }
            ?: throw Exception("Image read failed")
        val requestBody = MultipartBody.Builder().setType(MultipartBody.FORM)
            .addFormDataPart(
                "file",
                "image.jpg",
                RequestBody.create("image/*".toMediaTypeOrNull(), fileBytes)
            )
            .addFormDataPart("upload_preset", uploadPreset)
            .build()
        val request = Request.Builder()
            .url(cloudinaryUrl)
            .post(requestBody)
            .build()
        val response = OkHttpClient().newCall(request).execute()
        if (!response.isSuccessful) throw Exception("Image upload failed")
        val responseBody = response.body?.string() ?: ""
        val secureUrl = JSONObject(responseBody).optString("secure_url").takeIf { it.isNotBlank() }
        return requireNotNull(secureUrl) { "Failed to get image url" }
    }
    //r - Read products from db
    //fetch all products from real time
    fun allPaintings(products: SnapshotStateList<Art>){
        databasereference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                products.clear()
                for (snap in snapshot.children){
                    val retrievedProduct=snap.getValue(Art::class.java)
                    if (retrievedProduct !=null){
                        products.add(retrievedProduct)
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context,error.message,Toast.LENGTH_LONG).show()
            }
        })
    }

    fun allProducts(products: SnapshotStateList<Art>) = allPaintings(products)

    //u-update
    //update existing product in firebase
    fun updatePaintings(productId: String, name: String, description: String, category: String = "", imageUri: Uri?){
        CoroutineScope(Dispatchers.IO).launch {
            try{
                val newImageUrl = imageUri?.let{uploadToCloudinary(context,it)}
                val currentUser= FirebaseAuth.getInstance().currentUser
                val userId=currentUser?.uid?:""
                val updates=mutableMapOf<String,Any>(
                    "id" to productId,
                    "name" to name,
                    "description" to description,
                    "category" to category,
                    "userId" to userId,
                )
                if(!newImageUrl.isNullOrEmpty()){
                    updates["imageUrl"]=newImageUrl
                }
                databasereference.child(productId).updateChildren(updates).await()
                withContext(Dispatchers.Main){
                    Toast.makeText(context,"Product updated successfully",Toast.LENGTH_LONG).show()
                    navController.navigate(ROUTE_PAINTLIST)
                }
            }catch (e:Exception){
                withContext(Dispatchers.Main){
                    Toast.makeText(context,"Update failed:%${e.message}",Toast.LENGTH_LONG).show()
                }

            }
        }

    }

    fun updateProduct(productId: String, name: String, description: String, category: String = "", imageUri: Uri?) =
        updatePaintings(productId = productId, name = name, description = description, category = category, imageUri = imageUri)
    //d-delete
    //deletes an existing product from realtime db
    fun deleteProduct(productId: String){
        databasereference.child(productId).removeValue().addOnSuccessListener {
            Toast.makeText(context,"Product deleted successfully",Toast.LENGTH_LONG).show()
        }.addOnFailureListener{
            Toast.makeText(context,"Failed to delete product",Toast.LENGTH_SHORT).show()
        }

    }

}

