package com.example.arts.screens.paint

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.arts.R
import com.example.arts.ViewModel.ArtViewModel
import com.example.arts.models.availableCategoryNames

//create addpaint screen preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPaintScreen(navController : NavHostController){
    //add scaffold and top bar
    Scaffold(
        // Add a topbar
        topBar = {
            TopAppBar(title = {Text("Add painting", color = Color(0xFFC0EDAD), fontSize = 20.sp)},

                colors = topAppBarColors(
                    containerColor = Color(0xFFEDADE0),
                    titleContentColor = Color(0xFF994A3C)
                )
            )
        },
    )
    {
            innerpadding->
        //column
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(16.dp)
                .padding(innerpadding)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text= "Add painting",fontSize = 20.sp, color = Color(0xFF832B1C))
            Spacer(modifier = Modifier.height(10.dp))
            var productName by remember { mutableStateOf("") }
            var description by remember { mutableStateOf( "") }
            val categories = availableCategoryNames
            var selectedCategory by remember { mutableStateOf(categories.getOrElse(0) { "" }) }
            var categoryExpanded by remember { mutableStateOf(false) }

            var imageUri by remember {mutableStateOf<Uri?>(null)}
            val imagePickerLauncher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.GetContent(),
            ){ uri : Uri? ->
                imageUri = uri
            }

            //Outlined text field
            OutlinedTextField(
                value = productName,
                onValueChange = {productName=it},
                label = { Text("Painting's Name")},
                modifier = Modifier.fillMaxWidth().padding(8.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = description,
                onValueChange = { description=it },
                label = { Text("Painting's description")},
                modifier = Modifier.fillMaxWidth().padding(8.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))

            // Category Dropdown
            ExposedDropdownMenuBox(
                expanded = categoryExpanded,
                onExpandedChange = { categoryExpanded = !categoryExpanded },
                modifier = Modifier.fillMaxWidth().padding(8.dp)
            ) {
                OutlinedTextField(
                    value = selectedCategory,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Select Category") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = categoryExpanded) },
                    modifier = Modifier.menuAnchor().fillMaxWidth()
                )
                ExposedDropdownMenu(
                    expanded = categoryExpanded,
                    onDismissRequest = { categoryExpanded = false }
                ) {
                    categories.forEach { category ->
                        DropdownMenuItem(
                            text = { Text(category) },
                            onClick = {
                                selectedCategory = category
                                categoryExpanded = false
                            }
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(10.dp))

            //image preview
            Card(
                shape = CircleShape,
                modifier = Modifier
                    .size(100.dp)
                    .clickable{imagePickerLauncher.launch("image/*")}
            ) {
                AsyncImage(
                    model = imageUri ?: R.drawable.mountain_adventure_logo,
                    contentDescription = "Painting's Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(140.dp)
                )
            }
            //image picker
            OutlinedButton(onClick = {imagePickerLauncher.launch("image/*")}){
                Text("Pick an image", fontSize = 10.sp, color= Color(0xFF994A3C))
            }
            val context = LocalContext.current
            val myArtViewmodel = remember { ArtViewModel(navController, context) }
            Button(
                onClick = {
                    myArtViewmodel.addProduct(
                        name = productName,
                        description = description,
                        category = selectedCategory,
                        imageUri = imageUri
                    )
                    //clear outlined TextFields
                    productName = ""
                    description = ""
                    imageUri = null
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                colors = buttonColors(containerColor = Color(0xFF507FE5))
            ) {Text("Add Painting", color = Color(0xFFE5B650), fontSize = 20.sp)}

        }
    }

}
@Preview(showBackground = true)
@Composable
fun AddPaintPreview(){
    AddPaintScreen(rememberNavController())
}
//add scaffold-top bar and bottom nav
//column layout
//text addProduct
//add three outlined textField for name,description,price
//add image picker
//add product button