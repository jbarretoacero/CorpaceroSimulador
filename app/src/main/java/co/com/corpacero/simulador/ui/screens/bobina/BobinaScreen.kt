package co.com.corpacero.simulador.ui.screens.bobina

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import co.com.corpacero.simulador.R
import co.com.corpacero.simulador.domain.calculators.Calculators
import co.com.corpacero.simulador.ui.components.*
import co.com.corpacero.simulador.ui.theme.CorpBlue

private const val STORAGE_KEY_WEIGHT = "bobina_weight"
private const val STORAGE_KEY_LENGTH = "bobina_length"

private data class WeightInputs(
    val di: String = "",
    val de: String = "",
    val w:  String = "",
    val e:  String = "",
)

private data class LengthInputs(
    val w:  String = "",
    val e:  String = "",
    val we: String = "",
)

@OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)
@Composable
fun BobinaScreen(onBack: () -> Unit) {
    val context = LocalContext.current
    var tab by remember { mutableIntStateOf(0) }
    var weight by remember {
        val saved = CalcStorage.load(context, STORAGE_KEY_WEIGHT)
        mutableStateOf(
            if (saved != null) WeightInputs(
                di = saved["di"] ?: "",
                de = saved["de"] ?: "",
                w  = saved["w"]  ?: "",
                e  = saved["e"]  ?: "",
            ) else WeightInputs()
        )
    }
    var length by remember {
        val saved = CalcStorage.load(context, STORAGE_KEY_LENGTH)
        mutableStateOf(
            if (saved != null) LengthInputs(
                w  = saved["w"]  ?: "",
                e  = saved["e"]  ?: "",
                we = saved["we"] ?: "",
            ) else LengthInputs()
        )
    }

    CalculatorScaffold(
        title = stringResource(R.string.calc_bobina),
        onBack = onBack,
        actions = {
            SaveButton(onClick = {
                CalcStorage.save(context, STORAGE_KEY_WEIGHT, mapOf(
                    "di" to weight.di, "de" to weight.de,
                    "w" to weight.w,   "e" to weight.e,
                ))
                CalcStorage.save(context, STORAGE_KEY_LENGTH, mapOf(
                    "w" to length.w, "e" to length.e, "we" to length.we,
                ))
                Toast.makeText(context, R.string.saved_toast, Toast.LENGTH_SHORT).show()
            })
            ClearButton(onClick = {
                weight = WeightInputs()
                length = LengthInputs()
                CalcStorage.clear(context, STORAGE_KEY_WEIGHT)
                CalcStorage.clear(context, STORAGE_KEY_LENGTH)
                Toast.makeText(context, R.string.cleared_toast, Toast.LENGTH_SHORT).show()
            })
        },
    ) {
        PrimaryTabRow(
            selectedTabIndex = tab,
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = CorpBlue,
        ) {
            Tab(selected = tab == 0, onClick = { tab = 0 }, text = { Text("Peso del rollo", fontWeight = FontWeight.SemiBold) })
            Tab(selected = tab == 1, onClick = { tab = 1 }, text = { Text("Longitud del rollo", fontWeight = FontWeight.SemiBold) })
        }

        DiagramHero(R.drawable.diag_bobina, "Diagrama bobina", height = 200.dp)

        if (tab == 0) WeightContent(weight) { weight = it } else LengthContent(length) { length = it }

        DisclaimerBanner(stringResource(R.string.disclaimer))
    }
}

@Composable
private fun WeightContent(inputs: WeightInputs, onChange: (WeightInputs) -> Unit) {
    val di = inputs.di.toPositiveDoubleOrNull()
    val de = inputs.de.toPositiveDoubleOrNull()
    val w  = inputs.w.toPositiveDoubleOrNull()
    val e  = inputs.e.toPositiveDoubleOrNull()
    val deValid = (de != null && di != null && de > di)

    val ready = di != null && deValid && w != null && e != null

    val peso = if (ready) Calculators.bobinaPesoKg(di!!, de!!, w!!) else null
    val longitud = if (ready && peso != null) Calculators.bobinaLongitudDesdePesoM(peso, w!!, e!!) else null

    SectionCard(stringResource(R.string.parameters)) {
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            NumericInputField("[DI] Diámetro interno", inputs.di, { onChange(inputs.copy(di = it)) }, suffix = "mm")
            NumericInputField(
                "[DE] Diámetro externo",
                inputs.de,
                { onChange(inputs.copy(de = it)) },
                suffix = "mm",
                isError = de != null && di != null && de <= di,
                supportingText = if (de != null && di != null && de <= di) "Debe ser mayor que DI" else null,
            )
            NumericInputField("[W] Ancho del rollo", inputs.w, { onChange(inputs.copy(w = it)) }, suffix = "mm")
            NumericInputField("[e] Espesor",         inputs.e, { onChange(inputs.copy(e = it)) }, suffix = "mm")
        }
    }

    SectionCard(stringResource(R.string.results)) {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            ResultRow("Peso del rollo",     peso?.fmt(2)     ?: "—", "kg", highlighted = true)
            ResultRow("Longitud del rollo", longitud?.fmt(2) ?: "—", "m")
        }
    }
}

@Composable
private fun LengthContent(inputs: LengthInputs, onChange: (LengthInputs) -> Unit) {
    val w  = inputs.w.toPositiveDoubleOrNull()
    val e  = inputs.e.toPositiveDoubleOrNull()
    val we = inputs.we.toPositiveDoubleOrNull()
    val ready = w != null && e != null && we != null

    val longitud = if (ready) Calculators.bobinaLongitudM(w!!, e!!, we!!) else null

    SectionCard(stringResource(R.string.parameters)) {
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            NumericInputField("[W] Ancho del rollo", inputs.w,  { onChange(inputs.copy(w = it)) },  suffix = "mm")
            NumericInputField("[e] Espesor",         inputs.e,  { onChange(inputs.copy(e = it)) },  suffix = "mm")
            NumericInputField("[WE] Peso del rollo", inputs.we, { onChange(inputs.copy(we = it)) }, suffix = "kg")
        }
    }

    SectionCard(stringResource(R.string.results)) {
        ResultRow("Longitud del rollo", longitud?.fmt(2) ?: "—", "m", highlighted = true)
    }
}
