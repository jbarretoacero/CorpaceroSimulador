package co.com.corpacero.simulador.ui.screens.corpalosa

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import co.com.corpacero.simulador.R
import co.com.corpacero.simulador.domain.calculators.Calculators
import co.com.corpacero.simulador.ui.components.*
import co.com.corpacero.simulador.ui.theme.CorpTextMuted

private data class Inputs(
    val calibre: Int = 18,
    val referencia: String = "1.5\"",
)

@Composable
fun CorpalosaScreen(onBack: () -> Unit) {
    var inputs by remember { mutableStateOf(Inputs()) }
    val espesor = Calculators.calibresCorpalosa[inputs.calibre]

    val pesoM2 = espesor?.let { Calculators.corpalosaPesoKgM2(it, inputs.referencia) }
    val pesoM  = espesor?.let { Calculators.corpalosaPesoKgM(it) }

    val diag = when (inputs.referencia) {
        "1.5\""  -> R.drawable.diag_corpalosa_15
        "2\"MAX" -> R.drawable.diag_corpalosa_2
        "3\""    -> R.drawable.diag_corpalosa_3
        else     -> R.drawable.diag_corpalosa_15
    }

    CalculatorScaffold(
        title = stringResource(R.string.calc_corpalosa),
        onBack = onBack,
        actions = { ResetButton(onClick = { inputs = Inputs() }) },
    ) {
        DiagramHero(diag, "Diagrama corpalosa ${inputs.referencia}", height = 140.dp)

        SectionCard(stringResource(R.string.parameters)) {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                DropdownField(
                    label = "Calibre",
                    value = inputs.calibre.toString(),
                    options = Calculators.calibresCorpalosa.keys.map { it.toString() },
                    onSelect = { inputs = inputs.copy(calibre = it.toInt()) },
                )
                DropdownField(
                    label = "Referencia",
                    value = inputs.referencia,
                    options = Calculators.referenciasCorpalosa,
                    onSelect = { inputs = inputs.copy(referencia = it) },
                )
                if (espesor != null) {
                    Text(
                        text = "Espesor equivalente: ${espesor.fmt(2)} mm",
                        style = MaterialTheme.typography.bodySmall,
                        color = CorpTextMuted,
                    )
                }
            }
        }

        SectionCard(stringResource(R.string.results)) {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                ResultRow("Peso G60", pesoM2?.fmt(2) ?: "—", "kg/m²", highlighted = true)
                ResultRow("Peso G60", pesoM?.fmt(2)  ?: "—", "kg/m")
            }
        }

        DisclaimerBanner(stringResource(R.string.disclaimer))
    }
}
