package com.example.a207408_cikguizwan_lab02

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.a207408_cikguizwan_lab02.ui.theme.WildLensTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    viewModel: WildLensViewModel,
    onBack: () -> Unit
) {

    val profile by viewModel.userProfile.collectAsState()

    var nameInput by remember(profile.name) { mutableStateOf(profile.name) }
    var locationInput by remember(profile.location) { mutableStateOf(profile.location) }

    WildLensTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("My Profile", style = MaterialTheme.typography.titleLarge) },

                    navigationIcon = {
                        IconButton(onClick = onBack) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_back),
                                contentDescription = "Back"
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                )
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                if (profile.name.isNotEmpty()) {
                    Surface(
                        color = MaterialTheme.colorScheme.primaryContainer,
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("Current Profile", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.secondary)
                            Text("👤 ${profile.name}", style = MaterialTheme.typography.titleMedium)
                            Text("📍 ${profile.location}", style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }

                Text("Update Profile", style = MaterialTheme.typography.titleMedium)

                OutlinedTextField(
                    value = nameInput,
                    onValueChange = { nameInput = it },
                    label = { Text("Your Name") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )

                OutlinedTextField(
                    value = locationInput,
                    onValueChange = { locationInput = it },
                    label = { Text("Your Location") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )

                Button(
                    onClick = { viewModel.updateProfile(nameInput, locationInput) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    enabled = nameInput.isNotBlank()
                ) {
                    Text("Save Profile", style = MaterialTheme.typography.labelLarge)
                }

                OutlinedButton(
                    onClick = onBack,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("← Back", style = MaterialTheme.typography.labelLarge)
                }
            }
        }
    }
}