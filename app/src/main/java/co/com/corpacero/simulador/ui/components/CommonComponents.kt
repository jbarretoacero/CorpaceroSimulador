package co.com.corpacero.simulador.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.sp
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
/**
 * Etiqueta superpuesta sobre el diagrama. Las coordenadas se expresan como
 * fracciones (0..1) del rectángulo real de la imagen (no de la tarjeta), de
 * modo que el texto se mantiene en su sitio aunque la imagen se reescale o
 * tenga letterbox.
 */
data class DiagramOverlay(
    val text: String,
    val xFraction: Float,
    val yFraction: Float,
)

@Composable
fun DiagramHero(
    @DrawableRes drawable: Int,
    contentDescription: String,
    modifier: Modifier = Modifier,
    height: androidx.compose.ui.unit.Dp = 180.dp,
    overlays: List<DiagramOverlay> = emptyList(),
) {
    val painter = painterResource(drawable)
    val intrinsic = painter.intrinsicSize
    val imageAspect = if (intrinsic.width > 0f && intrinsic.height > 0f)
        intrinsic.width / intrinsic.height else 1f

    Box(
        modifier
            .fillMaxWidth()
            .height(height)
            .clip(RoundedCornerShape(14.dp))
            .background(androidx.compose.ui.graphics.Color.White)
            .border(BorderStroke(1.dp, CorpSlate200), RoundedCornerShape(14.dp)),
        contentAlignment = Alignment.Center,
    ) {
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            contentAlignment = Alignment.Center,
        ) {
            val containerW = maxWidth
            val containerH = maxHeight
            val containerAspect = if (containerH.value > 0f) containerW.value / containerH.value else 1f
            val imgW: androidx.compose.ui.unit.Dp
            val imgH: androidx.compose.ui.unit.Dp
            if (imageAspect >= containerAspect) {
                imgW = containerW
                imgH = containerW / imageAspect
            } else {
                imgH = containerH
                imgW = containerH * imageAspect
            }

            Column(modifier = Modifier.fillMaxSize()) {
                Box(modifier = Modifier
                    .size(width = imgW, height = imgH)
                    .align(Alignment.CenterHorizontally)
                ) {
                    Image(
                        painter = painter,
                        contentDescription = contentDescription,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Fit,
                    )
                }
                if (overlays.isNotEmpty()) {
                    val fontSizeSp = (imgH.value * 0.065f).coerceIn(7f, 9f)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 6.dp)
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                    ) {
                        overlays.forEach { o ->
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(CorpBlue.copy(alpha = 0.08f))
                                    .border(
                                        BorderStroke(0.5.dp, CorpBlue.copy(alpha = 0.3f)),
                                        RoundedCornerShape(4.dp),
                                    )
                                    .padding(horizontal = 5.dp, vertical = 2.dp),
                            ) {
                                Text(
                                    text = o.text,
                                    fontSize = fontSizeSp.sp,
                                    color = CorpBlueDeep,
                                    fontWeight = FontWeight.Medium,
                                    maxLines = 1,
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                }
            }
        }
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

/* ----------------------------- Toolbar action buttons ----------------------------- */
/**
 * Acción de barra superior con icono en cuadro redondeado arriba y etiqueta
 * compacta debajo. Pensada para usarse sobre el header azul oscuro.
 */
@Composable
private fun ToolbarAction(
    label: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 8.dp, vertical = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier
                .size(34.dp)
                .clip(RoundedCornerShape(8.dp))
                .border(
                    BorderStroke(1.dp, androidx.compose.ui.graphics.Color.White.copy(alpha = 0.45f)),
                    RoundedCornerShape(8.dp),
                ),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                icon,
                contentDescription = label,
                tint = androidx.compose.ui.graphics.Color.White,
                modifier = Modifier.size(18.dp),
            )
        }
        Spacer(Modifier.height(2.dp))
        Text(
            text = label,
            color = androidx.compose.ui.graphics.Color.White,
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Medium,
        )
    }
}

@Composable
fun ResetButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
    ToolbarAction("Restablecer", Icons.Default.Refresh, onClick, modifier)
}

@Composable
fun SaveButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
    ToolbarAction("Guardar", Icons.Default.Save, onClick, modifier)
}

@Composable
fun ClearButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
    ToolbarAction("Limpiar", Icons.Default.DeleteOutline, onClick, modifier)
}

/* ----------------------------- Brand gradient header ----------------------------- */
/** Gradiente vertical sobrio en dos tonos del azul corporativo. */
@Composable
fun BrandGradient(): Brush = Brush.verticalGradient(
    colors = listOf(CorpBlueDark, CorpBlue)
)
