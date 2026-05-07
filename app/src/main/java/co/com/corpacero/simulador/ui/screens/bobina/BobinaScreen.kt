package co.com.corpacero.simulador.ui.screens.bobina

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import co.com.corpacero.simulador.R
import co.com.corpacero.simulador.domain.calculators.Calculators
import co.com.corpacero.simulador.ui.components.*
import co.com.corpacero.simulador.ui.theme.CorpBlue

private data class WeightInputs(
    val di: String = "450",
    val de: String = "1600",
    val w:  String = "1530",
    val e:  String = "4",
)

private data class LengthInputs(
    val w:  String = "1220",
    val e:  String = "3",
    val we: String = "10000",
)

@OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)
@Composable
fun BobinaScreen(onBack: () -> Unit) {
    var tab by remember { mutableIntStateOf(0) }
    var weight by remember { mutableStateOf(WeightInputs()) }
    var length by remember { mutableStateOf(LengthInputs()) }

    CalculatorScaffold(
        title = stringResource(R.string.calc_bobina),
        onBack = onBack,
        actions = {
            ResetButton(onClick = {
                if (tab == 0) weight = WeightInputs() else length = LengthInputs()
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
