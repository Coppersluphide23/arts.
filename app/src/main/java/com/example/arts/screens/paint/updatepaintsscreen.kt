package com.example.arts.screens.paint

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CheckboxDefaults.colors
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.arts.ViewModel.ArtViewModel
import com.example.arts.models.Art
import com.example.arts.models.availableCategoryNames

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdatePaintScreen(
    navController: NavHostController,
    productId: String,
    viewModel: ArtViewModel = ArtViewModel(navController, LocalContext.current)
) {
    val categories = availableCategoryNames
    var productName by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var category by remember { mutableStateOf(categories.getOrElse(0) { "" }) }
    var categoryExpanded by remember { mutableStateOf(false) }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var imageUrl by remember { mutableStateOf<String?>(null) }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) imageUri = uri
    }

    // Fetch product details on initial load
    LaunchedEffect(productId) {
        viewModel.databasereference.child(productId).get()
            .addOnSuccessListener { snapshot ->
                val art = snapshot.getValue(Art::class.java)
                art?.let {
                    productName = it.name
                    description = it.description
                    if (it.category.isNotBlank()) {
                        category = it.category
                    }
                    imageUrl = it.imageUrl
                }
            }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Update Paint", color = Color(0xFFC0EDAD), fontSize = 20.sp) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFEDADE0),
                    titleContentColor = Color(0xFF994A3C)
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = "Update Painting's Details",
                fontSize = 20.sp,
                color = Color(0xFF832B1C)
            )

            OutlinedTextField(
                value = productName,
                onValueChange = { productName = it },
                label = { Text("Painting's name") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Product Description") },
                modifier = Modifier.fillMaxWidth()
            )

            // Category Selection Dropdown
            ExposedDropdownMenuBox(
                expanded = categoryExpanded,
                onExpandedChange = { categoryExpanded = !categoryExpanded },
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = category,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Category") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = categoryExpanded) },
                    modifier = Modifier.menuAnchor().fillMaxWidth()
                )
                ExposedDropdownMenu(
                    expanded = categoryExpanded,
                    onDismissRequest = { categoryExpanded = false }
                ) {
                    categories.forEach { cat ->
                        DropdownMenuItem(
                            text = { Text(cat) },
                            onClick = {
                                category = cat
                                categoryExpanded = false
                            }
                        )
                    }
                }
            }

            // Image Preview (New selected URI or fallback to fetched remote URL)
            val imageSource: Any? = imageUri ?: imageUrl
            AsyncImage(
                model = imageSource,
                contentDescription = "Selected Painting's Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )

            Button(
                onClick = { imagePickerLauncher.launch("image/*") },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF507FE5))
            ) {
                Text("Select New Image", fontSize = 14.sp, color = Color.White)
            }

            Button(
                onClick = {
                    viewModel.updatePaintings(
                        productId = productId,
                        name = productName,
                        description = description,
                        category = category,
                        imageUri = imageUri
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFE5B650),
                    contentColor = Color.White
                )
            ) {
                Text("Update Painting", fontSize = 16.sp)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UpdatePaintPreview() {
    UpdatePaintScreen(navController = rememberNavController(), productId = "1")
}
