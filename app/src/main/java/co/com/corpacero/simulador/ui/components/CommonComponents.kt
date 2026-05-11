package co.com.corpacero.simulador.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import co.com.corpacero.simulador.ui.theme.CorpBlue
import co.com.corpacero.simulador.ui.theme.CorpBlueAccent
import co.com.corpacero.simulador.ui.theme.CorpBlueDark
import co.com.corpacero.simulador.ui.theme.CorpBlueDeep
import co.com.corpacero.simulador.ui.theme.CorpBlueSoft
import co.com.corpacero.simulador.ui.theme.CorpSlate100
import co.com.corpacero.simulador.ui.theme.CorpSlate200
import co.com.corpacero.simulador.ui.theme.CorpSlate500
import co.com.corpacero.simulador.ui.theme.CorpSlate700

/* ----------------------------- Section card ----------------------------- */
/**
 * Tarjeta institucional: borde fino + elevación mínima.
 */
@Composable
fun SectionCard(
    title: String? = null,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {
    ElevatedCard(
        modifier = modifier
            .fillMaxWidth()
            .border(BorderStroke(1.dp, CorpSlate200), RoundedCornerShape(14.dp)),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.elevatedCardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 1.dp),
    ) {
        Column(Modifier.padding(horizontal = 18.dp, vertical = 18.dp)) {
            if (title != null) {
                Text(
                    text = title.uppercase(),
                    style = MaterialTheme.typography.labelLarge,
                    color = CorpBlueDeep,
                    fontWeight = FontWeight.SemiBold,
                )
                Spacer(Modifier.height(4.dp))
                Box(
                    Modifier
                        .height(2.dp)
                        .width(28.dp)
                        .background(CorpBlueAccent, RoundedCornerShape(1.dp)),
                )
                Spacer(Modifier.height(14.dp))
            }
            content()
        }
    }
}

/* ----------------------------- Numeric input ----------------------------- */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NumericInputField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    suffix: String? = null,
    isError: Boolean = false,
    supportingText: String? = null,
    allowDecimal: Boolean = true,
) {
    OutlinedTextField(
        value = value,
        onValueChange = { new ->
            // Solo dígitos, signo opcional al inicio y un punto si allowDecimal
            val filtered = new.filterIndexed { i, c ->
                c.isDigit() ||
                    (c == '.' && allowDecimal && new.indexOf('.') == i) ||
                    (c == '-' && i == 0)
            }
            onValueChange(filtered)
        },
        label = { Text(label) },
        modifier = modifier.fillMaxWidth(),
        singleLine = true,
        isError = isError,
        keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(
            keyboardType = if (allowDecimal) KeyboardType.Decimal else KeyboardType.Number
        ),
        suffix = suffix?.let { { Text(it, color = CorpSlate500) } },
        supportingText = supportingText?.let { { Text(it) } },
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = CorpBlue,
            focusedLabelColor = CorpBlue,
            unfocusedBorderColor = CorpSlate200,
            unfocusedLabelColor = CorpSlate500,
            cursorColor = CorpBlue,
        ),
        shape = RoundedCornerShape(10.dp),
    )
}

/* ----------------------------- Dropdown field ---------------------------- */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownField(
    label: String,
    value: String,
    options: List<String>,
    onSelect: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    var expanded by remember { mutableStateOf(false) }
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier,
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = {},
            readOnly = true,
            label = { Text(label) },
            trailingIcon = { Icon(Icons.Default.ArrowDropDown, null, tint = CorpSlate500) },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = CorpBlue,
                focusedLabelColor = CorpBlue,
                unfocusedBorderColor = CorpSlate200,
                unfocusedLabelColor = CorpSlate500,
            ),
            shape = RoundedCornerShape(10.dp),
        )
        ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            options.forEach { opt ->
                DropdownMenuItem(
                    text = { Text(opt) },
                    onClick = {
                        onSelect(opt)
                        expanded = false
                    },
                )
            }
        }
    }
}

/* ------------------------------ Result row ------------------------------ */
/**
 * Fila de resultado tipo "dashboard" corporativo. La variante destacada usa
 * un acento de color a la izquierda en lugar de un fondo coloreado, lo que
 * da un acabado más sobrio y profesional.
 */
@Composable
fun ResultRow(
    label: String,
    value: String,
    unit: String,
    highlighted: Boolean = false,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(if (highlighted) CorpBlueSoft else CorpSlate100)
            .padding(horizontal = 0.dp, vertical = 0.dp),
    ) {
        // Barra acento vertical en variante destacada
        Box(
            Modifier
                .width(if (highlighted) 3.dp else 0.dp)
                .height(44.dp)
                .background(if (highlighted) CorpBlueAccent else androidx.compose.ui.graphics.Color.Transparent),
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 12.dp),
        ) {
            Text(
                text = label,
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.bodyMedium,
                color = CorpSlate700,
                fontWeight = if (highlighted) FontWeight.Medium else FontWeight.Normal,
            )
            Text(
                text = value,
                style = if (highlighted)
                    MaterialTheme.typography.titleLarge
                else
                    MaterialTheme.typography.titleMedium,
                color = CorpBlueDeep,
                fontWeight = FontWeight.SemiBold,
            )
            Spacer(Modifier.width(6.dp))
            Text(
                text = unit,
                style = MaterialTheme.typography.labelMedium,
                color = CorpSlate500,
            )
        }
    }
}

/* ------------------------------ Diagram hero ----------------------------- */
@Composable
fun DiagramHero(
    @DrawableRes drawable: Int,
    contentDescription: String,
    modifier: Modifier = Modifier,
    height: androidx.compose.ui.unit.Dp = 180.dp,
) {
    Box(
        modifier
            .fillMaxWidth()
            .height(height)
            .clip(RoundedCornerShape(14.dp))
            .background(androidx.compose.ui.graphics.Color.White)
            .border(BorderStroke(1.dp, CorpSlate200), RoundedCornerShape(14.dp)),
        contentAlignment = Alignment.Center,
    ) {
        Image(
            painter = painterResource(drawable),
            contentDescription = contentDescription,
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            contentScale = ContentScale.Fit,
        )
    }
}

/* ----------------------------- Disclaimer ----------------------------- */
@Composable
fun DisclaimerBanner(text: String, modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.Top,
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(CorpSlate100)
            .border(BorderStroke(1.dp, CorpSlate200), RoundedCornerShape(10.dp))
            .padding(horizontal = 14.dp, vertical = 12.dp),
    ) {
        Icon(
            Icons.Outlined.Info,
            contentDescription = null,
            tint = CorpSlate700,
            modifier = Modifier.size(18.dp),
        )
        Spacer(Modifier.width(10.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall,
            color = CorpSlate700,
        )
    }
}

/* ----------------------------- Toolbar action chips ----------------------------- */
@Composable
private fun ToolbarChip(
    label: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    AssistChip(
        onClick = onClick,
        label = { Text(label, fontWeight = FontWeight.Medium) },
        leadingIcon = { Icon(icon, null, modifier = Modifier.size(16.dp)) },
        modifier = modifier,
        colors = AssistChipDefaults.assistChipColors(
            containerColor = androidx.compose.ui.graphics.Color.White.copy(alpha = 0.16f),
            labelColor = androidx.compose.ui.graphics.Color.White,
            leadingIconContentColor = androidx.compose.ui.graphics.Color.White,
        ),
        border = AssistChipDefaults.assistChipBorder(
            enabled = true,
            borderColor = androidx.compose.ui.graphics.Color.White.copy(alpha = 0.32f),
        ),
    )
}

@Composable
fun ResetButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
    ToolbarChip("Restablecer", Icons.Default.Refresh, onClick, modifier)
}

@Composable
fun SaveButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
    ToolbarChip("Guardar", Icons.Default.Save, onClick, modifier)
}

@Composable
fun ClearButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
    ToolbarChip("Limpiar", Icons.Default.DeleteOutline, onClick, modifier)
}

/* ----------------------------- Brand gradient header ----------------------------- */
/** Gradiente vertical sobrio en dos tonos del azul corporativo. */
@Composable
fun BrandGradient(): Brush = Brush.verticalGradient(
    colors = listOf(CorpBlueDark, CorpBlue)
)
