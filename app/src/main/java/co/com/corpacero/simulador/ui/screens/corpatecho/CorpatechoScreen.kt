package co.com.corpacero.simulador.ui.screens.corpatecho

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
    val espGalv: String = "0.75",
    val recGalv: String = "G90",
    val espPint: String = "0.75",
    val recPint: String = "G90",
)

@Composable
fun CorpatechoScreen(onBack: () -> Unit) {
    var inputs by remember { mutableStateOf(Inputs()) }
    val recGalvOpts = Calculators.recubrimientosCorpatechoGalv.keys.toList()
    val recPintOpts = Calculators.recubrimientosCorpatechoPint.keys.toList()

    val eG = inputs.espGalv.toPositiveDoubleOrNull()
    val rG = Calculators.recubrimientosCorpatechoGalv[inputs.recGalv]
    val galvMl = if (eG != null && rG != null) Calculators.corpatechoGalvPesoKgMl(eG, rG) else null
    val galvM2 = galvMl?.let { Calculators.corpatechoKgM2(it) }

    val eP = inputs.espPint.toPositiveDoubleOrNull()
    val rP = Calculators.recubrimientosCorpatechoPint[inputs.recPint]
    val pintMl = if (eP != null && rP != null) Calculators.corpatechoPintPesoKgMl(eP, rP) else null
    val pintM2 = pintMl?.let { Calculators.corpatechoKgM2(it) }

    CalculatorScaffold(
        title = stringResource(R.string.calc_corpatecho),
        onBack = onBack,
        actions = { ResetButton(onClick = { inputs = Inputs() }) },
    ) {
        DiagramHero(R.drawable.diag_corpatecho, stringResource(R.string.calc_corpatecho), height = 160.dp)

        CubiertaVarianteCard(
            titulo = "Galvanizada",
            espesor = inputs.espGalv,
            onEspesorChange = { inputs = inputs.copy(espGalv = it) },
            recubrimiento = inputs.recGalv,
            recubrimientos = recGalvOpts,
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
            recubrimientos = recPintOpts,
            onRecubrimientoChange = { inputs = inputs.copy(recPint = it) },
            recubrimientoGm2 = rP,
            pesoKgMl = pintMl,
            pesoKgM2 = pintM2,
        )

        DisclaimerBanner(stringResource(R.string.disclaimer))
    }
}
