package co.com.corpacero.simulador

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import co.com.corpacero.simulador.ui.navigation.SimuladorNavGraph
import co.com.corpacero.simulador.ui.theme.CorpaceroSimuladorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        // Splash screen API moderna (Android 12+ con compatibilidad hacia atrás)
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CorpaceroSimuladorTheme {
                SimuladorNavGraph()
            }
        }
    }
}
