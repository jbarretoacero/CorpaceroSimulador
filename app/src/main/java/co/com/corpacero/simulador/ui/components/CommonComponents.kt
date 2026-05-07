package co.com.corpacero.simulador.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Refresh
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
import co.com.corpacero.simulador.ui.theme.CorpBlueDeep
import co.com.corpacero.simulador.ui.theme.CorpBlueSoft
import co.com.corpacero.simulador.ui.theme.CorpCyan
import co.com.corpacero.simulador.ui.theme.CorpTextMuted
import co.com.corpacero.simulador.ui.theme.CorpViolet

/* ----------------------------- Section card ----------------------------- */
@Composable
fun SectionCard(
    title: String? = null,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {
    ElevatedCard(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.elevatedCardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp),
    ) {
        Column(Modifier.padding(20.dp)) {
            if (title != null) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    color = CorpBlueDeep,
                    fontWeight = FontWeight.SemiBold,
                )
                Spacer(Modifier.height(12.dp))
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
        suffix = suffix?.let { { Text(it, color = CorpTextMuted) } },
        supportingText = supportingText?.let { { Text(it) } },
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = CorpBlue,
            focusedLabelColor = CorpBlue,
            cursorColor = CorpBlue,
        ),
        shape = RoundedCornerShape(14.dp),
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
            trailingIcon = { Icon(Icons.Default.ArrowDropDown, null) },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = CorpBlue,
                focusedLabelColor = CorpBlue,
            ),
            shape = RoundedCornerShape(14.dp),
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
            .clip(RoundedCornerShape(12.dp))
            .background(if (highlighted) CorpBlueSoft else MaterialTheme.colorScheme.surface)
            .padding(horizontal = 14.dp, vertical = 12.dp),
    ) {
        Text(
            text = label,
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.bodyMedium,
            color = CorpTextMuted,
        )
        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium,
            color = CorpBlueDeep,
            fontWeight = FontWeight.Bold,
        )
        Spacer(Modifier.width(6.dp))
        Text(
            text = unit,
            style = MaterialTheme.typography.labelMedium,
            color = CorpTextMuted,
        )
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
            .clip(RoundedCornerShape(20.dp))
            .background(MaterialTheme.colorScheme.surface),
        contentAlignment = Alignment.Center,
    ) {
        Image(
            painter = painterResource(drawable),
            contentDescription = contentDescription,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
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
            .clip(RoundedCornerShape(14.dp))
            .background(CorpBlueSoft)
            .padding(14.dp),
    ) {
        Icon(
            Icons.Default.Info,
            contentDescription = null,
            tint = CorpBlue,
            modifier = Modifier.size(20.dp),
        )
        Spacer(Modifier.width(10.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall,
            color = CorpBlueDeep,
        )
    }
}

/* ----------------------------- Reset chip ----------------------------- */
@Composable
fun ResetButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
    AssistChip(
        onClick = onClick,
        label = { Text("Restablecer") },
        leadingIcon = { Icon(Icons.Default.Refresh, null) },
        modifier = modifier,
        colors = AssistChipDefaults.assistChipColors(
            containerColor = CorpBlueSoft,
            labelColor = CorpBlueDeep,
            leadingIconContentColor = CorpBlue,
        ),
    )
}

/* ----------------------------- Brand gradient header ----------------------------- */
@Composable
fun BrandGradient(): Brush = Brush.horizontalGradient(
    colors = listOf(CorpBlue, CorpViolet, CorpCyan)
)
