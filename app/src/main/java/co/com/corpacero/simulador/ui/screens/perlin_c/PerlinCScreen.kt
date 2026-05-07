package co.com.corpacero.simulador.ui.screens.perlin_c

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import co.com.corpacero.simulador.R
import co.com.corpacero.simulador.domain.calculators.Calculators
import co.com.corpacero.simulador.ui.components.*
import co.com.corpacero.simulador.ui.theme.CorpBlueDeep
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text

private data class Inputs(
    val a: String = "160",   // altura mm
    val b: String = "60",    // base mm
    val u: String = "19",    // pestaña mm
    val e: String = "3",     // espesor mm
    val l: String = "6",     // longitud m
)

@Composable
fun PerlinCScreen(onBack: () -> Unit) {
    var inputs by remember { mutableStateOf(Inputs()) }

    val a = inputs.a.toPositiveDoubleOrNull()
    val b = inputs.b.toPositiveDoubleOrNull()
    val u = inputs.u.toPositiveDoubleOrNull()
    val e = inputs.e.toPositiveDoubleOrNull()
    val l = inputs.l.toPositiveDoubleOrNull()
    val ready = a != null && b != null && u != null && e != null && l != null

    val pesoNegroM   = if (ready) Calculators.perlinCPesoNegroKgM(a!!, b!!, u!!, e!!) else null
    val pesoNegroUnd = if (ready) Calculators.perlinCPesoNegroKgUnd(pesoNegroM!!, l!!) else null
    val pesoGalvM    = if (ready) Calculators.perlinCPesoGalvKgM(pesoNegroM!!) else null
    val pesoGalvUnd  = if (ready) Calculators.perlinCPesoGalvKgUnd(pesoGalvM!!, l!!) else null

    CalculatorScaffold(
        title = stringResource(R.string.calc_perlin_c),
        onBack = onBack,
        actions = { ResetButton(onClick = { inputs = Inputs() }) },
    ) {
        DiagramHero(R.drawable.diag_perlin_c, "Diagrama Perlín C")

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
                Text("Negro", style = MaterialTheme.typography.titleSmall, color = CorpBlueDeep)
                ResultRow("Peso negro",      pesoNegroM?.fmt(2)   ?: "—", "kg/m",   highlighted = true)
                ResultRow("Peso negro",      pesoNegroUnd?.fmt(2) ?: "—", "kg/und")
                Spacer(Modifier.height(6.dp))
                Text("Galvanizado", style = MaterialTheme.typography.titleSmall, color = CorpBlueDeep)
                ResultRow("Peso galvanizado",pesoGalvM?.fmt(2)    ?: "—", "kg/m",   highlighted = true)
                ResultRow("Peso galvanizado",pesoGalvUnd?.fmt(2)  ?: "—", "kg/und")
            }
        }

        DisclaimerBanner(stringResource(R.string.disclaimer))
    }
}
