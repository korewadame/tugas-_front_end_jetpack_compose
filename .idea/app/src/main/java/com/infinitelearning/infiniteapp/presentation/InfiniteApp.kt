package com.infinitelearning.infiniteapp.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.infinitelearning.infiniteapp.R


// Hero data model
data class Hero(val id: Int, val name: String, val description: String, val imageRes: Int)

val heroes = listOf(
    Hero(1, "Anti-Mage", "Anti-Mage is a melee agility hero known for his ability to burn mana and his fast farming abilities.", R.drawable.antima),
    Hero(2, "Axe", "Axe is a tanky hero who thrives in the frontlines, able to deal damage and initiate fights.", R.drawable.axe),
    Hero(3, "Crystal Maiden", "Crystal Maiden is a ranged support hero with crowd control abilities and strong mana regeneration.", R.drawable.crystama),
    Hero(4, "Invoker", "Invoker is a highly versatile hero who uses a combination of spells for various effects.", R.drawable.invo),
    Hero(5, "Juggernaut", "Juggernaut is a balanced hero with healing abilities, mobility, and strong sustain.", R.drawable.juggernaut),
    Hero(6, "Mirana", "Mirana is a ranged agility hero who specializes in long-range attacks and crowd control abilities.", R.drawable.mirana),
    Hero(7, "Pudge", "Pudge is a strength hero who excels in hooking enemies and disrupting their positioning in fights.", R.drawable.pudge),
    Hero(8, "Drow Ranger", "Drow Ranger is an agility hero who specializes in ranged attacks and reducing the armor of enemies.", R.drawable.drow),
    Hero(9, "Earthshaker", "Earthshaker is a tanky hero with powerful area-of-effect stuns and high initiation potential.", R.drawable.earthshaker),
    Hero(10, "Tinker", "Tinker is a ranged intelligence hero who excels at using powerful spells and gadgets to control the battlefield.", R.drawable.tinker)
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InfiniteApp() {
    val navController = rememberNavController()

    Scaffold(
        topBar = {
            val currentBackStack by navController.currentBackStackEntryAsState()
            val currentRoute = currentBackStack?.destination?.route

            TopAppBar(
                title = {
                    Text(
                        text = when (currentRoute) {
                            "home" -> "Heroes"
                            "about" -> "About"
                            else -> "Hero Details"
                        }
                    )
                },
                navigationIcon = {
                    if (currentRoute?.startsWith("detail") == true) {
                        IconButton(onClick = { navController.navigateUp() }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    }
                }
            )
        },
        bottomBar = { BottomNavigationBar(navController) }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(paddingValues)
        ) {
            composable("home") { HomeScreen(navController) }
            composable("about") { AboutScreen() }
            composable(
                "detail/{heroId}",
                arguments = listOf(navArgument("heroId") { type = NavType.IntType })
            ) { backStackEntry ->
                val heroId = backStackEntry.arguments?.getInt("heroId")
                DetailScreen(heroId)
            }
        }
    }
}

@Composable
fun HomeScreen(navController: NavController) {
    // Using LazyVerticalGrid to display the heroes in a grid layout
    LazyVerticalGrid(
        columns = GridCells.Fixed(2), // 2 columns grid
        contentPadding = PaddingValues(16.dp)
    ) {
        items(heroes.size) { index ->
            val hero = heroes[index]
            // Each hero will be displayed with an image and name in the grid
            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .clickable { navController.navigate("detail/${hero.id}") },
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(
                        painter = painterResource(id = hero.imageRes),
                        contentDescription = hero.name,
                        modifier = Modifier.size(100.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = hero.name)
                }
            }
        }
    }
}

@Composable
fun DetailScreen(heroId: Int?) {
    val hero = heroes.find { it.id == heroId }
    if (hero != null) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = hero.imageRes),
                contentDescription = hero.name,
                modifier = Modifier.size(200.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = hero.name, style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = hero.description)
        }
    }
}

@Composable
fun AboutScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.yeah),
            contentDescription = "Profile Picture",
            modifier = Modifier.size(128.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Name
        Text(
            text = "Iqbal S.A",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // University
        Text(
            text = "Politeknik Negeri Batam",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Email
        Text(
            text = "iqbalshn8@gmail.com",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Major
        Text(
            text = "Teknik Informatika",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )
    }
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    NavigationBar {
        val currentBackStack by navController.currentBackStackEntryAsState()
        val currentRoute = currentBackStack?.destination?.route

        val navigationItems = listOf(
            NavigationItem("Heroes", Icons.Default.Home, "home"),
            NavigationItem("About", Icons.Default.Info, "about")
        )

        navigationItems.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = { Icon(imageVector = item.icon, contentDescription = item.label) },
                label = { Text(text = item.label) }
            )
        }
    }
}



data class NavigationItem(val label: String, val icon: ImageVector, val route: String)

@Preview(showBackground = true)
@Composable
fun InfiniteAppPreview() {
    InfiniteApp()
}
