package co.com.corpacero.simulador.ui.screens.teja_zinc

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import co.com.corpacero.simulador.R
import co.com.corpacero.simulador.domain.calculators.Calculators
import co.com.corpacero.simulador.ui.components.CalculatorScaffold
import co.com.corpacero.simulador.ui.components.CubiertaVarianteCard
import co.com.corpacero.simulador.ui.components.DiagramHero
import co.com.corpacero.simulador.ui.components.DisclaimerBanner
import co.com.corpacero.simulador.ui.components.ResetButton
import co.com.corpacero.simulador.ui.components.toPositiveDoubleOrNull

private data class Inputs(
    val espGalv: String = "0.18",
    val recGalv: String = "G90",
    val espPint: String = "0.17",
    val recPint: String = "G40",
)

@Composable
fun TejaZincScreen(onBack: () -> Unit) {
    var inputs by remember { mutableStateOf(Inputs()) }
    val recOptions = Calculators.recubrimientosTeja.keys.toList()

    val eG = inputs.espGalv.toPositiveDoubleOrNull()
    val rG = Calculators.recubrimientosTeja[inputs.recGalv]
    val galvMl = if (eG != null && rG != null) Calculators.tejaGalvPesoKgMl(eG, rG) else null
    val galvM2 = galvMl?.let { Calculators.tejaKgM2(it) }

    val eP = inputs.espPint.toPositiveDoubleOrNull()
    val rP = Calculators.recubrimientosTeja[inputs.recPint]
    val pintMl = if (eP != null && rP != null) Calculators.tejaPintPesoKgMl(eP, rP) else null
    val pintM2 = pintMl?.let { Calculators.tejaKgM2(it) }

    CalculatorScaffold(
        title = stringResource(R.string.calc_teja_zinc),
        onBack = onBack,
        actions = { ResetButton(onClick = { inputs = Inputs() }) },
    ) {
        DiagramHero(R.drawable.diag_teja_zinc, stringResource(R.string.calc_teja_zinc), height = 160.dp)

        CubiertaVarianteCard(
            titulo = "Galvanizada",
            espesor = inputs.espGalv,
            onEspesorChange = { inputs = inputs.copy(espGalv = it) },
            recubrimiento = inputs.recGalv,
            recubrimientos = recOptions,
            onRecubrimientoChange = { inputs = inputs.copy(recGalv = it) },
            recubrimientoGm2 = rG,
            pesoKgMl = galvMl,
            pesoKgM2 = galvM2,
        )

        CubiertaVarianteCard(
            titulo = "Pintada",
            espesor = inputs.espPint,
            onEspesorChange = { inputs = inputs.copy(espPint = it) },
            recubrimiento = inputs.recPint,
            recubrimientos = recOptions,
            onRecubrimientoChange = { inputs = inputs.copy(recPint = it) },
            recubrimientoGm2 = rP,
            pesoKgMl = pintMl,
            pesoKgM2 = pintM2,
        )

        DisclaimerBanner(stringResource(R.string.disclaimer))
    }
}
