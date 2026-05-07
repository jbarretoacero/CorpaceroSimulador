package co.com.corpacero.simulador.ui.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import co.com.corpacero.simulador.ui.screens.bobina.BobinaScreen
import co.com.corpacero.simulador.ui.screens.corpalosa.CorpalosaScreen
import co.com.corpacero.simulador.ui.screens.corpatecho.CorpatechoScreen
import co.com.corpacero.simulador.ui.screens.cub_arq.CubArqScreen
import co.com.corpacero.simulador.ui.screens.home.HomeScreen
import co.com.corpacero.simulador.ui.screens.lamina.LaminaScreen
import co.com.corpacero.simulador.ui.screens.perlin_c.PerlinCScreen
import co.com.corpacero.simulador.ui.screens.perlin_cajon.PerlinCajonScreen
import co.com.corpacero.simulador.ui.screens.teja_zinc.TejaZincScreen
import co.com.corpacero.simulador.ui.screens.tuberia.TuberiaScreen

@Composable
fun SimuladorNavGraph() {
    val nav = rememberNavController()

    NavHost(
        navController = nav,
        startDestination = Routes.Home.path,
        enterTransition  = { slideInHorizontally(tween(280)) { it / 4 } + fadeIn(tween(280)) },
        exitTransition   = { fadeOut(tween(180)) },
        popEnterTransition = { fadeIn(tween(180)) },
        popExitTransition  = { slideOutHorizontally(tween(280)) { it / 4 } + fadeOut(tween(280)) },
    ) {
        composable(Routes.Home.path)        { HomeScreen(onSelect = { nav.navigate(it.path) }) }
        composable(Routes.PerlinC.path)     { PerlinCScreen(onBack = { nav.popBackStack() }) }
        composable(Routes.PerlinCajon.path) { PerlinCajonScreen(onBack = { nav.popBackStack() }) }
        composable(Routes.Tuberia.path)     { TuberiaScreen(onBack = { nav.popBackStack() }) }
        composable(Routes.Lamina.path)      { LaminaScreen(onBack = { nav.popBackStack() }) }
        composable(Routes.CubArq.path)      { CubArqScreen(onBack = { nav.popBackStack() }) }
        composable(Routes.Corpatecho.path)  { CorpatechoScreen(onBack = { nav.popBackStack() }) }
        composable(Routes.TejaZinc.path)    { TejaZincScreen(onBack = { nav.popBackStack() }) }
        composable(Routes.Corpalosa.path)   { CorpalosaScreen(onBack = { nav.popBackStack() }) }
        composable(Routes.Bobina.path)      { BobinaScreen(onBack = { nav.popBackStack() }) }
    }
}
