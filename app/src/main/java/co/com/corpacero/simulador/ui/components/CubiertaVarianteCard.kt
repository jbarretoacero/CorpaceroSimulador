package co.com.corpacero.simulador.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import co.com.corpacero.simulador.ui.theme.CorpTextMuted

/**
 * Bloque de variante (Galvanizada / Pintada) usado por las pantallas de cubierta.
 * Recibe los valores ya calculados para evitar pasar lambdas no-composables a un
 * Composable, lo cual confunde al inferidor de tipos de Kotlin con Compose.
 */
@Composable
fun CubiertaVarianteCard(
    titulo: String,
    espesor: String,
    onEspesorChange: (String) -> Unit,
    recubrimiento: String,
    recubrimientos: List<String>,
    onRecubrimientoChange: (String) -> Unit,
    recubrimientoGm2: Double?,
    pesoKgMl: Double?,
    pesoKgM2: Double?,
) {
    SectionCard(titulo) {
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            NumericInputField(
                label = "[e] Espesor",
                value = espesor,
                onValueChange = onEspesorChange,
                suffix = "mm",
            )
            DropdownField(
                label = "Recubrimiento",
                value = recubrimiento,
                options = recubrimientos,
                onSelect = onRecubrimientoChange,
            )
            if (recubrimientoGm2 != null) {
                Text(
                    text = "Recubrimiento: ${recubrimientoGm2.toInt()} g/m²",
                    style = MaterialTheme.typography.bodySmall,
                    color = CorpTextMuted,
                )
            }
            Spacer(Modifier.height(2.dp))
            ResultRow("Peso", pesoKgMl?.fmt(2) ?: "—", "kg/ml", highlighted = true)
            ResultRow("Peso", pesoKgM2?.fmt(2) ?: "—", "kg/m²")
        }
    }
}
