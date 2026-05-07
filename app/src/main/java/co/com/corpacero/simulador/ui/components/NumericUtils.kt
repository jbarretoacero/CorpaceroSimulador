package co.com.corpacero.simulador.ui.components

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

/** Parsea aceptando coma o punto. Devuelve null si no es válido o es ≤ 0. */
fun String.toPositiveDoubleOrNull(): Double? =
    this.replace(',', '.').toDoubleOrNull()?.takeIf { it > 0.0 }

/** Igual que arriba pero acepta cero (para inputs neutrales). */
fun String.toNonNegativeDoubleOrNull(): Double? =
    this.replace(',', '.').toDoubleOrNull()?.takeIf { it >= 0.0 }

private val symbols = DecimalFormatSymbols(Locale.US).apply {
    decimalSeparator = '.'
    groupingSeparator = ','
}

private val fmt2 = DecimalFormat("#,##0.00", symbols)
private val fmt4 = DecimalFormat("#,##0.0000", symbols)

fun Double.fmt(decimals: Int = 2): String =
    if (decimals >= 4) fmt4.format(this) else fmt2.format(this)
