package co.com.corpacero.simulador.ui.screens.perlin_cajon

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import co.com.corpacero.simulador.R
import co.com.corpacero.simulador.domain.calculators.Calculators
import co.com.corpacero.simulador.ui.components.*

private const val STORAGE_KEY = "perlin_cajon"

private data class Inputs(
    val a: String = "",
    val b: String = "",
    val u: String = "",
    val e: String = "",
    val l: String = "",
)

@Composable
fun PerlinCajonScreen(onBack: () -> Unit) {
    val context = LocalContext.current
    var inputs by remember {
        val saved = CalcStorage.load(context, STORAGE_KEY)
        mutableStateOf(
            if (saved != null) Inputs(
                a = saved["a"] ?: "",
                b = saved["b"] ?: "",
                u = saved["u"] ?: "",
                e = saved["e"] ?: "",
                l = saved["l"] ?: "",
            ) else Inputs()
        )
    }
    val a = inputs.a.toPositiveDoubleOrNull()
    val b = inputs.b.toPositiveDoubleOrNull()
    val u = inputs.u.toPositiveDoubleOrNull()
    val e = inputs.e.toPositiveDoubleOrNull()
    val l = inputs.l.toPositiveDoubleOrNull()
    val ready = a != null && b != null && u != null && e != null && l != null

    val pesoM   = if (ready) Calculators.perlinCajonPesoNegroKgM(a!!, b!!, u!!, e!!) else null
    val pesoUnd = if (ready) Calculators.perlinCajonPesoNegroKgUnd(pesoM!!, l!!) else null

    CalculatorScaffold(
        title = stringResource(R.string.calc_perlin_cajon),
        onBack = onBack,
        actions = {
            SaveButton(onClick = {
                CalcStorage.save(context, STORAGE_KEY, mapOf(
                    "a" to inputs.a, "b" to inputs.b, "u" to inputs.u,
                    "e" to inputs.e, "l" to inputs.l,
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
        DiagramHero(R.drawable.diag_perlin_cajon, "Diagrama Perlín cajón")

        SectionCard(stringResource(R.string.parameters)) {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                NumericInputField("[A] Altura", inputs.a, { inputs = inputs.copy(a = it) }, suffix = "mm")
                NumericInputField("[B] Base",   inputs.b, { inputs = inputs.copy(b = it) }, suffix = "mm")
                NumericInputField("[U] Pestaña",inputs.u, { inputs = inputs.copy(u = it) }, suffix = "mm")
                NumericInputField("[e] Espesor",inputs.e, { inputs = inputs.copy(e = it) }, suffix = "mm")
                NumericInputField("Longitud",   inputs.l, { inputs = inputs.copy(l = it) }, suffix = "m")
            }
        }

        SectionCard(stringResource(R.string.results)) {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                ResultRow("Peso negro", pesoM?.fmt(2)   ?: "—", "kg/m",  highlighted = true)
                ResultRow("Peso negro", pesoUnd?.fmt(2) ?: "—", "kg/und")
            }
        }

        DisclaimerBanner(stringResource(R.string.disclaimer))
    }
}
