package co.com.corpacero.simulador.ui.navigation

sealed class Routes(val path: String) {
    data object Splash        : Routes("splash")
    data object Home          : Routes("home")
    data object PerlinC       : Routes("perlin_c")
    data object PerlinCajon   : Routes("perlin_cajon")
    data object Tuberia       : Routes("tuberia")
    data object Lamina        : Routes("lamina")
    data object CubArq        : Routes("cub_arq")
    data object Corpatecho    : Routes("corpatecho")
    data object TejaZinc      : Routes("teja_zinc")
    data object Corpalosa     : Routes("corpalosa")
    data object Bobina        : Routes("bobina")
}
