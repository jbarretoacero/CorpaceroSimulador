package co.com.corpacero.simulador.ui.screens.corpalosa

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import co.com.corpacero.simulador.R
import co.com.corpacero.simulador.domain.calculators.Calculators
import co.com.corpacero.simulador.ui.components.*
import co.com.corpacero.simulador.ui.theme.CorpTextMuted

private const val STORAGE_KEY = "corpalosa"
private const val DEFAULT_CALIBRE = 18
private const val DEFAULT_REFERENCIA = "1.5\""

private data class Inputs(
    val calibre: Int = DEFAULT_CALIBRE,
    val referencia: String = DEFAULT_REFERENCIA,
)

@Composable
fun CorpalosaScreen(onBack: () -> Unit) {
    val context = LocalContext.current
    var inputs by remember {
        val saved = CalcStorage.load(context, STORAGE_KEY)
        mutableStateOf(
            if (saved != null) Inputs(
                calibre = saved["calibre"]?.toIntOrNull() ?: DEFAULT_CALIBRE,
                referencia = saved["referencia"] ?: DEFAULT_REFERENCIA,
            ) else Inputs()
        )
    }
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
        actions = {
            SaveButton(onClick = {
                CalcStorage.save(context, STORAGE_KEY, mapOf(
                    "calibre" to inputs.calibre.toString(),
                    "referencia" to inputs.referencia,
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
