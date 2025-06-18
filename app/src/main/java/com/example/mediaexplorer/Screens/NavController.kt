package com.example.mediaexplorer.Screens

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.mediaexplorer.ui.ui.Generos.FirstScreen
import com.example.mediaexplorer.ui.ui.Pelicula.FourScreen
import com.example.mediaexplorer.ui.ui.Generos.HomeScreen
import com.example.mediaexplorer.ui.ui.iniciosecion.LoginScreen
import com.example.mediaexplorer.ui.ui.Pelicula.SecondScreen
import com.example.mediaexplorer.ui.ui.Pelicula.TerceraScreen
import com.example.mediaexplorer.ui.ui.iniciosecion.AuthManager
import kotlinx.serialization.Serializable

@Serializable
object Home

@Serializable
data class SecondPage(val id: Int)

@Serializable
data class TerceraPage(val id: Int)

@Serializable
data class FourPage(val id: Int)

@Serializable
object CategoriesList

@Serializable
object Login

@Serializable
object AuthManager

@Serializable
object Registrer

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Home.toString()) {
        composable(AuthManager.toString()) {
            AuthManager(navController = navController)
        }

        composable(Login.toString()) {
            LoginScreen(navController = navController)
        }

        composable(Home.toString()) {
            HomeScreen(navController = navController)
        }

        composable(CategoriesList.toString()) {
            FirstScreen(navController = navController)
        }

        composable<SecondPage> { backStackEntry ->
            val args = backStackEntry.toRoute<SecondPage>()
            SecondScreen(navController,args.id)

        }
        composable<TerceraPage> { bacStackEntry ->
            val args = bacStackEntry.toRoute<TerceraPage>()
            TerceraScreen(navController, args.id)
        }

        composable<FourPage> { bacStackEntry ->
            val args = bacStackEntry.toRoute<FourPage>()
            FourScreen(navController, args.id)
        }
    }
}

