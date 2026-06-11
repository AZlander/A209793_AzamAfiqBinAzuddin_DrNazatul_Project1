package com.example.a209793_azamafiqbinazuddin_drnazatul_project1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.a209793_azamafiqbinazuddin_drnazatul_project1.screen.AddBoardScreen
import com.example.a209793_azamafiqbinazuddin_drnazatul_project1.screen.BoardDetailScreen
import com.example.a209793_azamafiqbinazuddin_drnazatul_project1.screen.HomeScreen
import com.example.a209793_azamafiqbinazuddin_drnazatul_project1.screen.LibraryScreen
import com.example.a209793_azamafiqbinazuddin_drnazatul_project1.screen.SearchScreen
import com.example.a209793_azamafiqbinazuddin_drnazatul_project1.screen.ProfileScreen
import com.example.a209793_azamafiqbinazuddin_drnazatul_project1.screen.GroveViewModel
import com.example.a209793_azamafiqbinazuddin_drnazatul_project1.ui.theme.GroveTheme

enum class GroveScreen(val title: String, val icon: ImageVector) {
    Home(title = "Home", icon = Icons.Filled.Home),
    Search(title = "Search", icon = Icons.Filled.Search),
    Library(title = "Library", icon = Icons.AutoMirrored.Filled.List),
    Profile(title = "Profile", icon = Icons.Filled.Person)
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GroveTheme {
                GroveApp()
            }
        }
    }
}

@Composable
fun GroveApp(
    navController: NavHostController = rememberNavController(),
    groveViewModel: GroveViewModel = viewModel()
) {
    Scaffold(
        bottomBar = { BottomNavigationBar(navController = navController) }
    ) { innerPadding ->
        NavHost( // NavHost file
            navController = navController,
            startDestination = GroveScreen.Home.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            // existing routes
            composable(route = GroveScreen.Home.name) {
                HomeScreen(
                    viewModel = groveViewModel,
                    navController = navController
                )
            }
            composable(route = GroveScreen.Search.name) { SearchScreen(groveViewModel, navController) }
            composable(route = GroveScreen.Library.name) {
                LibraryScreen(groveViewModel, navController)  //adding a parameter
            }
            composable(route = GroveScreen.Profile.name) { ProfileScreen(groveViewModel) }

            composable(route = "AddBoard") {
                AddBoardScreen(groveViewModel, navController)
            }
            composable(route = "BoardDetail/{boardId}") { backStackEntry ->
                val boardId = backStackEntry.arguments?.getString("boardId")?.toIntOrNull() ?: 0
                BoardDetailScreen(groveViewModel, navController, boardId)
            }
        }
    }
}
@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val items = GroveScreen.entries
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { screen ->
            NavigationBarItem(
                icon = { Icon(screen.icon, contentDescription = screen.title) },
                label = { Text(screen.title) },
                selected = currentRoute == screen.name,
                onClick = {
                    navController.navigate(screen.name) {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GroveMainScreenPreview() {
    GroveTheme {
        GroveApp()
    }
}

@Preview
@Composable
fun GroveDarkThemePreview() {
    GroveTheme(darkTheme = true) {
        GroveApp()
    }
}