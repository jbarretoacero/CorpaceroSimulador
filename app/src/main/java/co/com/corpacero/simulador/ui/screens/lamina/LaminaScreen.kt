package co.com.corpacero.simulador.ui.screens.lamina

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import co.com.corpacero.simulador.R
import co.com.corpacero.simulador.domain.calculators.Calculators
import co.com.corpacero.simulador.ui.components.*

private data class Inputs(
    val ancho: String = "400",
    val largo: String = "2000",
    val espesor: String = "1.2",
    val recubrimiento: String = "G90",
)

@Composable
fun LaminaScreen(onBack: () -> Unit) {
    var inputs by remember { mutableStateOf(Inputs()) }
    val a = inputs.ancho.toPositiveDoubleOrNull()
    val l = inputs.largo.toPositiveDoubleOrNull()
    val e = inputs.espesor.toPositiveDoubleOrNull()
    val recVal = Calculators.recubrimientosLamina[inputs.recubrimiento]
    val ready = a != null && l != null && e != null && recVal != null

    val peso = if (ready) Calculators.laminaPesoKgUnd(a!!, l!!, e!!, recVal!!) else null

    CalculatorScaffold(
        title = stringResource(R.string.calc_lamina),
        onBack = onBack,
        actions = { ResetButton(onClick = { inputs = Inputs() }) },
    ) {
        DiagramHero(R.drawable.diag_lamina, "Diagrama lámina")

        SectionCard(stringResource(R.string.parameters)) {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                NumericInputField("[A] Ancho",   inputs.ancho,   { inputs = inputs.copy(ancho = it) },   suffix = "mm")
                NumericInputField("[L] Largo",   inputs.largo,   { inputs = inputs.copy(largo = it) },   suffix = "mm")
                NumericInputField("[e] Espesor", inputs.espesor, { inputs = inputs.copy(espesor = it) }, suffix = "mm")
                DropdownField(
                    label = "Recubrimiento",
                    value = inputs.recubrimiento,
                    options = Calculators.recubrimientosLamina.keys.toList(),
                    onSelect = { inputs = inputs.copy(recubrimiento = it) },
                )
                if (recVal != null) {
                    androidx.compose.material3.Text(
                        text = "Recubrimiento: ${recVal.toInt()} g/m²",
                        style = androidx.compose.material3.MaterialTheme.typography.bodySmall,
                        color = co.com.corpacero.simulador.ui.theme.CorpTextMuted,
                    )
                }
            }
        }

        SectionCard(stringResource(R.string.results)) {
            ResultRow("Peso", peso?.fmt(2) ?: "—", "kg/und", highlighted = true)
        }

        DisclaimerBanner(stringResource(R.string.disclaimer))
    }
}
