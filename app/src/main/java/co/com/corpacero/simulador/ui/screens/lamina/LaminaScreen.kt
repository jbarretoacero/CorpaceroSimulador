package co.com.corpacero.simulador.ui.screens.lamina

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

private const val STORAGE_KEY = "lamina"
private const val DEFAULT_REC = "G90"

private data class Inputs(
    val ancho: String = "",
    val largo: String = "",
    val espesor: String = "",
    val recubrimiento: String = DEFAULT_REC,
)

@Composable
fun LaminaScreen(onBack: () -> Unit) {
    val context = LocalContext.current
    var inputs by remember {
        val saved = CalcStorage.load(context, STORAGE_KEY)
        mutableStateOf(
            if (saved != null) Inputs(
                ancho = saved["ancho"] ?: "",
                largo = saved["largo"] ?: "",
                espesor = saved["espesor"] ?: "",
                recubrimiento = saved["recubrimiento"] ?: DEFAULT_REC,
            ) else Inputs()
        )
    }
    val a = inputs.ancho.toPositiveDoubleOrNull()
    val l = inputs.largo.toPositiveDoubleOrNull()
    val e = inputs.espesor.toPositiveDoubleOrNull()
    val recVal = Calculators.recubrimientosLamina[inputs.recubrimiento]
    val ready = a != null && l != null && e != null && recVal != null

    val peso = if (ready) Calculators.laminaPesoKgUnd(a!!, l!!, e!!, recVal!!) else null

    CalculatorScaffold(
        title = stringResource(R.string.calc_lamina),
        onBack = onBack,
        actions = {
            SaveButton(onClick = {
                CalcStorage.save(context, STORAGE_KEY, mapOf(
                    "ancho" to inputs.ancho, "largo" to inputs.largo,
                    "espesor" to inputs.espesor, "recubrimiento" to inputs.recubrimiento,
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
