package com.example.a207408_cikguizwan_lab02

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.a207408_cikguizwan_lab02.ui.theme.WildLensTheme

data class AnimalCategory(
    val name: String,
    val description: String,
    val iconRes: Int,
    val count: Int
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuScreen(onBack: () -> Unit) {
    val categories = listOf(
        AnimalCategory("Birds", "Winged creatures of Malaysia", R.drawable.img_bee_eater, 3),
        AnimalCategory("Mammals", "Land mammals & big cats", R.drawable.img_tiger, 3),
        AnimalCategory("Insects", "Butterflies, moths & more", R.drawable.img_butterfly, 2),
        AnimalCategory("Plants", "Flora & rare flowers", R.drawable.img_rafflesia, 1),
        AnimalCategory("Primates", "Monkeys & apes", R.drawable.img_monkey, 1),
    )

    WildLensTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Browse Categories", style = MaterialTheme.typography.titleLarge) },
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
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    Text(
                        text = "What are you looking for?",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.secondary
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                }
                items(categories) { category ->
                    CategoryCard(category = category)
                }
            }
        }
    }
}

@Composable
fun CategoryCard(category: AnimalCategory) {
    var expanded by remember { mutableStateOf(false) }

    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { expanded = !expanded },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Surface(
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.size(52.dp)
            ) {
                Icon(
                    painter = painterResource(id = category.iconRes),
                    contentDescription = null,
                    modifier = Modifier.padding(10.dp),
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = category.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = category.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.secondary
                )
                if (expanded) {
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "${category.count} species observed nearby 🌿",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

            Surface(
                color = MaterialTheme.colorScheme.secondaryContainer,
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = "${category.count}",
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }
        }
    }
}