package com.example.a207408_cikguizwan_lab02

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
// 【改动】导入你新建的主题包
import com.example.a207408_cikguizwan_lab02.ui.theme.WildLensTheme


data class GridItem(
    val name: String,
    val count: Int,
    val imageRes: Int
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
           //新增这个多页面
            WildLensTheme {
                WildLensNavHost()
            }
        }
    }
}

@Composable
fun WildLensApp(
    //页面跳转参数，以及用了viewmodel（ui和数据分离）
    viewModel: WildLensViewModel,
    onProfileClick: () -> Unit,
    onMenuClick: () -> Unit,
    onIdentifyClick: () -> Unit,
    onActivityClick: () -> Unit
) {
    var selectedTab by remember { mutableStateOf(1) }
    var searchInput by remember { mutableStateOf("") }
    var confirmedSearch by remember { mutableStateOf("") }

    Scaffold(
        // 从主题获取背景色
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = {
            WildLensBottomNav(
                selectedTab = selectedTab,
                onTabSelected = { index ->
                   //控制跳转，之前是选中有显示
                    when (index) {
                        0 -> onMenuClick()
                        2 -> onIdentifyClick()
                        3 -> onProfileClick()
                        4 -> onActivityClick()
                        else -> selectedTab = index
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            WildLensTopBar()

            SearchInputRow(
                searchInput = searchInput,
                onInputChange = { searchInput = it },
                onSearchClicked = { confirmedSearch = searchInput.trim() }
            )

            if (confirmedSearch.isNotEmpty()) {
                WelcomeBanner(name = confirmedSearch)
            }


            ObservationBanner(count = 94)

            Box(modifier = Modifier.weight(1f)) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.fillMaxSize(),
                      contentPadding = PaddingValues(12.dp), // 增加间距使卡片更美观
                      verticalArrangement = Arrangement.spacedBy(12.dp),
                      horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(sampleGridItems()) { item -> GridCard(item = item) }
                }

                Box(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 16.dp)
                ) {
                    ViewModeToggle()
                }
            }
        }
    }
}

// Task 2 & 3
@Composable
fun GridCard(item: GridItem) {
    var expanded by remember { mutableStateOf(false) }

    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { expanded = !expanded }
            .animateContentSize(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        // 修正后的 elevation 写法
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 4.dp,
            pressedElevation = 8.dp
        )
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp) // 固定图片高度
            ) {
                Image(
                    painter = painterResource(id = item.imageRes),
                    contentDescription = item.name,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                if (item.count > 1) {
                    Surface(
                        color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.9f),
                        shape = RoundedCornerShape(4.dp),
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(8.dp)
                    ) {
                        Text(
                            text = "${item.count}",
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }
            }

            // 卡片文字部分 这里
            Column(modifier = Modifier.padding(12.dp))  {
                Text(
                    text = item.name,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )

                // 动画逻辑如果展开就展示
                if (expanded) {
                    Spacer(modifier = Modifier.height(8.dp)
                        .offset(y = (-60).dp))
                    Text(
                        text = "This species is vital for SDG 15: Life on Land. Protecting biodiversity ensures a balanced ecosystem. 🌿",
                        // 自动应用 Ubuntu 字体样式
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            }
        }
    }
}

@Composable
fun SearchInputRow(
    searchInput: String,
    onInputChange: (String) -> Unit,
    onSearchClicked: () -> Unit
) {
    val focusManager = LocalFocusManager.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface) // 使用主题背景
                 .padding(horizontal = 12.dp, vertical = 8.dp), // 这里
                verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        OutlinedTextField(
            value = searchInput,
            onValueChange = onInputChange,
            placeholder = { Text("Enter your name…", style = MaterialTheme.typography.bodyMedium) },
            singleLine = true,
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.outline
            ),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = { onSearchClicked(); focusManager.clearFocus() })
        )

        Button(
            onClick = { onSearchClicked(); focusManager.clearFocus() },
            // 【改动】按钮颜色自动跟随主题 Primary 色
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Search", style = MaterialTheme.typography.labelLarge)
        }
    }
}

@Composable
fun WelcomeBanner(name: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f))
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_person),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(20.dp)
        )
           Spacer(modifier = Modifier.width(8.dp))// 这里
        Text(
            text = "Welcome back, $name! 🌿",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
fun WildLensTopBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 这里
        Column (verticalArrangement = Arrangement.spacedBy(4.dp)){
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(painter = painterResource(id = R.drawable.ic_shield), contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("All organisms", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onBackground)
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(painter = painterResource(id = R.drawable.ic_location), contentDescription = null, tint = MaterialTheme.colorScheme.secondary, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("Nearby", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.secondary)
            }
        }

        Surface(
            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.size(44.dp)
        ) {
            Icon(painter = painterResource(id = R.drawable.ic_sliders), contentDescription = "Filter", modifier = Modifier.padding(12.dp))
        }
    }
}

@Composable
fun ObservationBanner(count: Int) {
    Surface(
        color = MaterialTheme.colorScheme.secondaryContainer,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(vertical = 10.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(painter = painterResource(id = R.drawable.ic_binoculars), contentDescription = null, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text("$count Observations", style = MaterialTheme.typography.titleSmall)
        }
    }
}

@Composable
fun ViewModeToggle() {
    var selectedMode by remember { mutableStateOf(1) }
    Surface(
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.9f),
        shape = RoundedCornerShape(24.dp),
        tonalElevation = 4.dp
    ) {
        Row(modifier = Modifier.padding(4.dp)) {
            listOf(R.drawable.ic_map, R.drawable.ic_grid, R.drawable.ic_list).forEachIndexed { index, iconRes ->
                val isSelected = selectedMode == index
                IconButton(
                    onClick = { selectedMode = index },
                    modifier = Modifier
                        .background(if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent, CircleShape)
                ) {
                    Icon(painter = painterResource(id = iconRes), contentDescription = null, tint = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        }
    }
}

@Composable
fun WildLensBottomNav(selectedTab: Int, onTabSelected: (Int) -> Unit) {
    NavigationBar(containerColor = MaterialTheme.colorScheme.surface) {
        val icons = listOf(R.drawable.ic_menu, R.drawable.ic_search, R.drawable.ic_camera, R.drawable.ic_person, R.drawable.ic_bell)
        val labels = listOf("Menu", "Explore", "Identify", "Me", "Activity")

        icons.forEachIndexed { index, iconRes ->
            NavigationBarItem(
                selected = selectedTab == index,
                onClick = { onTabSelected(index) },
                icon = { Icon(painter = painterResource(id = iconRes), contentDescription = labels[index]) },
                label = { Text(labels[index], style = MaterialTheme.typography.labelSmall) }
            )
        }
    }
}

fun sampleGridItems() = listOf(
    GridItem("Butterflies", 3, R.drawable.img_butterfly),
    GridItem("Common Myna", 1, R.drawable.img_myna),
    GridItem("Wasp Moth", 1, R.drawable.img_wasp_moth),
    GridItem("Bee-Eater", 2, R.drawable.img_bee_eater),
    GridItem("Little Heron", 1, R.drawable.img_heron),
    GridItem("Malayan Tiger", 1, R.drawable.img_tiger),
    GridItem("Monkey", 3, R.drawable.img_monkey),
    GridItem("Sun Bear", 1, R.drawable.img_bear),
    GridItem("Rafflesia", 2, R.drawable.img_rafflesia),
    GridItem("Hornbill", 1, R.drawable.img_hornbill),
)