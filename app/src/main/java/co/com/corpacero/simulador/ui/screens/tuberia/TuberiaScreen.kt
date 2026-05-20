package co.com.corpacero.simulador.ui.screens.tuberia

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

private const val STORAGE_KEY_RECT = "tuberia_rect"
private const val STORAGE_KEY_CIRC = "tuberia_circ"

private data class RectInputs(
    val b: String = "",
    val h: String = "",
    val e: String = "",
    val l: String = "",
)

private data class CircInputs(
    val dext: String = "",
    val e: String = "",
    val l: String = "",
)

@OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)
@Composable
fun TuberiaScreen(onBack: () -> Unit) {
    val context = LocalContext.current
    var tab by remember { mutableIntStateOf(0) }
    var rect by remember {
        val saved = CalcStorage.load(context, STORAGE_KEY_RECT)
        mutableStateOf(
            if (saved != null) RectInputs(
                b = saved["b"] ?: "",
                h = saved["h"] ?: "",
                e = saved["e"] ?: "",
                l = saved["l"] ?: "",
            ) else RectInputs()
        )
    }
    var circ by remember {
        val saved = CalcStorage.load(context, STORAGE_KEY_CIRC)
        mutableStateOf(
            if (saved != null) CircInputs(
                dext = saved["dext"] ?: "",
                e = saved["e"] ?: "",
                l = saved["l"] ?: "",
            ) else CircInputs()
        )
    }

    CalculatorScaffold(
        title = stringResource(R.string.calc_tuberia),
        onBack = onBack,
        actions = {
            SaveButton(onClick = {
                CalcStorage.save(context, STORAGE_KEY_RECT, mapOf(
                    "b" to rect.b, "h" to rect.h, "e" to rect.e, "l" to rect.l,
                ))
                CalcStorage.save(context, STORAGE_KEY_CIRC, mapOf(
                    "dext" to circ.dext, "e" to circ.e, "l" to circ.l,
                ))
                Toast.makeText(context, R.string.saved_toast, Toast.LENGTH_SHORT).show()
            })
            ClearButton(onClick = {
                rect = RectInputs()
                circ = CircInputs()
                CalcStorage.clear(context, STORAGE_KEY_RECT)
                CalcStorage.clear(context, STORAGE_KEY_CIRC)
                Toast.makeText(context, R.string.cleared_toast, Toast.LENGTH_SHORT).show()
            })
        },
    ) {
        PrimaryTabRow(
            selectedTabIndex = tab,
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = CorpBlue,
        ) {
            Tab(selected = tab == 0, onClick = { tab = 0 }, text = { Text("Rectangular / cuadrada", fontWeight = FontWeight.SemiBold) })
            Tab(selected = tab == 1, onClick = { tab = 1 }, text = { Text("Circular", fontWeight = FontWeight.SemiBold) })
        }

        if (tab == 0) RectContent(rect) { rect = it } else CircContent(circ) { circ = it }

        DisclaimerBanner(stringResource(R.string.disclaimer))
    }
}

@Composable
private fun RectContent(inputs: RectInputs, onChange: (RectInputs) -> Unit) {
    val b = inputs.b.toPositiveDoubleOrNull()
    val h = inputs.h.toPositiveDoubleOrNull()
    val e = inputs.e.toPositiveDoubleOrNull()
    val l = inputs.l.toPositiveDoubleOrNull()
    val ready = b != null && h != null && e != null && l != null

    val pesoM   = if (ready) Calculators.tuberiaRectPesoKgM(b!!, h!!, e!!) else null
    val pesoUnd = if (ready) Calculators.tuberiaPesoKgUnd(pesoM!!, l!!) else null

    DiagramHero(R.drawable.diag_tuberia_rect, "Diagrama tubería rectangular")

    SectionCard(stringResource(R.string.parameters)) {
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            NumericInputField("[B] Base",    inputs.b, { onChange(inputs.copy(b = it)) }, suffix = "mm")
            NumericInputField("[H] Altura",  inputs.h, { onChange(inputs.copy(h = it)) }, suffix = "mm")
            NumericInputField("[e] Espesor", inputs.e, { onChange(inputs.copy(e = it)) }, suffix = "mm")
            NumericInputField("Longitud",    inputs.l, { onChange(inputs.copy(l = it)) }, suffix = "m")
        }
    }

    SectionCard(stringResource(R.string.results)) {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            ResultRow("Peso", pesoM?.fmt(2)   ?: "—", "kg/m", highlighted = true)
            ResultRow("Peso", pesoUnd?.fmt(2) ?: "—", "kg/und")
        }
    }
}

@Composable
private fun CircContent(inputs: CircInputs, onChange: (CircInputs) -> Unit) {
    val dext = inputs.dext.toPositiveDoubleOrNull()
    val e = inputs.e.toPositiveDoubleOrNull()
    val l = inputs.l.toPositiveDoubleOrNull()
    val ready = dext != null && e != null && l != null

    val dIn   = if (dext != null) Calculators.mmToInch(dext) else null
    val pesoM = if (ready) Calculators.tuberiaCircPesoKgM(dext!!, e!!) else null
    val pesoUnd = if (ready) Calculators.tuberiaPesoKgUnd(pesoM!!, l!!) else null

    DiagramHero(R.drawable.diag_tuberia_circ, "Diagrama tubería circular")

    SectionCard(stringResource(R.string.parameters)) {
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            NumericInputField(
                "Diámetro Dext",
                inputs.dext,
                { onChange(inputs.copy(dext = it)) },
                suffix = "mm",
                supportingText = dIn?.let { "≈ ${it.fmt(4)} in" },
            )
            NumericInputField("[e] Espesor", inputs.e, { onChange(inputs.copy(e = it)) }, suffix = "mm")
            NumericInputField("Longitud",    inputs.l, { onChange(inputs.copy(l = it)) }, suffix = "m")
        }
    }

    SectionCard(stringResource(R.string.results)) {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            ResultRow("Peso", pesoM?.fmt(2)   ?: "—", "kg/m", highlighted = true)
            ResultRow("Peso", pesoUnd?.fmt(2) ?: "—", "kg/und")
        }
    }
}
