package co.com.corpacero.simulador.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import co.com.corpacero.simulador.R

// Fuente institucional: Montserrat (Manual de Imagen Corpacero).
val Montserrat = FontFamily(
    Font(R.font.montserrat_regular,   FontWeight.Normal),
    Font(R.font.montserrat_medium,    FontWeight.Medium),
    Font(R.font.montserrat_semibold,  FontWeight.SemiBold),
    Font(R.font.montserrat_bold,      FontWeight.Bold),
    Font(R.font.montserrat_extrabold, FontWeight.ExtraBold),
)

// Escala tipográfica corporativa. Privilegia jerarquía estricta y pesos
// medidos: SemiBold para títulos y SemiBold/Bold sólo en valores numéricos
// destacados. Regular en cuerpo para máxima legibilidad.
val CorpTypography = Typography(
    displayLarge   = TextStyle(fontFamily = Montserrat, fontWeight = FontWeight.Bold,     fontSize = 32.sp, lineHeight = 38.sp, letterSpacing = (-0.4).sp),
    headlineLarge  = TextStyle(fontFamily = Montserrat, fontWeight = FontWeight.SemiBold, fontSize = 24.sp, lineHeight = 30.sp, letterSpacing = (-0.2).sp),
    headlineMedium = TextStyle(fontFamily = Montserrat, fontWeight = FontWeight.SemiBold, fontSize = 20.sp, lineHeight = 26.sp),
    titleLarge     = TextStyle(fontFamily = Montserrat, fontWeight = FontWeight.SemiBold, fontSize = 18.sp, lineHeight = 24.sp),
    titleMedium    = TextStyle(fontFamily = Montserrat, fontWeight = FontWeight.SemiBold, fontSize = 15.sp, lineHeight = 20.sp),
    titleSmall     = TextStyle(fontFamily = Montserrat, fontWeight = FontWeight.Medium,   fontSize = 13.sp, lineHeight = 18.sp),
    bodyLarge      = TextStyle(fontFamily = Montserrat, fontWeight = FontWeight.Normal,   fontSize = 15.sp, lineHeight = 22.sp),
    bodyMedium     = TextStyle(fontFamily = Montserrat, fontWeight = FontWeight.Normal,   fontSize = 14.sp, lineHeight = 20.sp),
    bodySmall      = TextStyle(fontFamily = Montserrat, fontWeight = FontWeight.Normal,   fontSize = 12.sp, lineHeight = 16.sp),
    labelLarge     = TextStyle(fontFamily = Montserrat, fontWeight = FontWeight.SemiBold, fontSize = 13.sp, lineHeight = 18.sp, letterSpacing = 0.4.sp),
    labelMedium    = TextStyle(fontFamily = Montserrat, fontWeight = FontWeight.Medium,   fontSize = 12.sp, lineHeight = 16.sp, letterSpacing = 0.6.sp),
    labelSmall     = TextStyle(fontFamily = Montserrat, fontWeight = FontWeight.Medium,   fontSize = 11.sp, lineHeight = 14.sp, letterSpacing = 0.8.sp),
)
