package com.example.a207408_cikguizwan_lab02

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


object Routes {
    const val HOME = "home"
    const val PROFILE = "profile"
    const val MENU = "menu"
    const val IDENTIFY = "identify"
    const val ACTIVITY = "activity"
}

@Composable
fun WildLensNavHost() {
    val navController = rememberNavController()
    val viewModel: WildLensViewModel = viewModel()

    NavHost(navController = navController, startDestination = Routes.HOME) {

        composable(Routes.HOME) {
            WildLensApp(
                viewModel = viewModel,
                onProfileClick = { navController.navigate(Routes.PROFILE) },
                onMenuClick = { navController.navigate(Routes.MENU) },
                onIdentifyClick = { navController.navigate(Routes.IDENTIFY) },
                onActivityClick = { navController.navigate(Routes.ACTIVITY) }
            )
        }

        composable(Routes.PROFILE) {
            ProfileScreen(viewModel = viewModel, onBack = { navController.popBackStack() })
        }

        composable(Routes.MENU) {
            MenuScreen(onBack = { navController.popBackStack() })
        }

        composable(Routes.IDENTIFY) {
            // 修改这里：传入 viewModel
            IdentifyScreen(viewModel = viewModel, onBack = { navController.popBackStack() })
        }

        composable(Routes.ACTIVITY) {
            ActivityScreen(viewModel = viewModel, onBack = { navController.popBackStack() })
        }
    }
}