package co.com.corpacero.simulador.ui.screens.cub_arq

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import co.com.corpacero.simulador.R
import co.com.corpacero.simulador.domain.calculators.Calculators
import co.com.corpacero.simulador.ui.components.CalcStorage
import co.com.corpacero.simulador.ui.components.CalculatorScaffold
import co.com.corpacero.simulador.ui.components.ClearButton
import co.com.corpacero.simulador.ui.components.CubiertaVarianteCard
import co.com.corpacero.simulador.ui.components.DiagramHero
import co.com.corpacero.simulador.ui.components.DisclaimerBanner
import co.com.corpacero.simulador.ui.components.SaveButton
import co.com.corpacero.simulador.ui.components.toPositiveDoubleOrNull

private const val STORAGE_KEY = "cub_arq"
private const val DEFAULT_REC = "G90"

private data class Inputs(
    val espGalv: String = "",
    val recGalv: String = DEFAULT_REC,
    val espPint: String = "",
    val recPint: String = DEFAULT_REC,
)

@Composable
fun CubArqScreen(onBack: () -> Unit) {
    val context = LocalContext.current
    var inputs by remember {
        val saved = CalcStorage.load(context, STORAGE_KEY)
        mutableStateOf(
            if (saved != null) Inputs(
                espGalv = saved["espGalv"] ?: "",
                recGalv = saved["recGalv"] ?: DEFAULT_REC,
                espPint = saved["espPint"] ?: "",
                recPint = saved["recPint"] ?: DEFAULT_REC,
            ) else Inputs()
        )
    }
    val recOptions = Calculators.recubrimientosCubArq.keys.toList()

    val eG = inputs.espGalv.toPositiveDoubleOrNull()
    val rG = Calculators.recubrimientosCubArq[inputs.recGalv]
    val galvMl = if (eG != null && rG != null) Calculators.cubArqGalvPesoKgMl(eG, rG) else null
    val galvM2 = galvMl?.let { Calculators.cubArqKgM2(it) }

    val eP = inputs.espPint.toPositiveDoubleOrNull()
    val rP = Calculators.recubrimientosCubArq[inputs.recPint]
    val pintMl = if (eP != null && rP != null) Calculators.cubArqPintPesoKgMl(eP, rP) else null
    val pintM2 = pintMl?.let { Calculators.cubArqKgM2(it) }

    CalculatorScaffold(
        title = stringResource(R.string.calc_cub_arq),
        onBack = onBack,
        actions = {
            SaveButton(onClick = {
                CalcStorage.save(context, STORAGE_KEY, mapOf(
                    "espGalv" to inputs.espGalv, "recGalv" to inputs.recGalv,
                    "espPint" to inputs.espPint, "recPint" to inputs.recPint,
                ))
                Toast.makeText(context, R.string.saved_toast, Toast.LENGTH_SHORT).show()
            })
            ClearButton(onClick = {
                inputs = Inputs()
                CalcStorage.clear(context, STORAGE_KEY)
                Toast.makeText(context, R.string.cleared_toast, Toast.LENGTH_SHORT).show()
            })
        },
    ) {
        DiagramHero(R.drawable.diag_cub_arq, stringResource(R.string.calc_cub_arq), height = 160.dp)

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
