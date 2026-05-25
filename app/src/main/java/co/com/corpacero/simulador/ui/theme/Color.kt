package co.com.corpacero.simulador.ui.theme

import androidx.compose.ui.graphics.Color

// Paleta corporativa Corpacero — alineada al Manual de Imagen oficial.
// Color institucional: Pantone 2145 C / HEX #003AA9 (R:0 G:58 B:169).
// El manual exige azul sólido sin gradientes ni acentos cyan/violeta.
val CorpBlue        = Color(0xFF003AA9) // Azul institucional (Pantone 2145 C)
val CorpBlueDark    = Color(0xFF002B7F) // Variante para statusbar / hover
val CorpBlueDeep    = Color(0xFF001A55) // Texto sobre claro / headlines
val CorpBlueSoft    = Color(0xFFE6EEFA) // Fondos suaves de tarjetas de resultado
val CorpBlueAccent  = Color(0xFF003AA9) // Acento = mismo institucional (sin desviaciones)

// Neutrales corporativos (familia slate / steel)
val CorpSlate900    = Color(0xFF111827) // Texto principal de alta jerarquía
val CorpSlate700    = Color(0xFF374151) // Texto secundario
val CorpSlate500    = Color(0xFF6B7280) // Texto auxiliar / suffix
val CorpSlate200    = Color(0xFFE5E7EB) // Bordes / divisores
val CorpSlate100    = Color(0xFFF1F4F9) // Fondo de chips / hovers
val CorpSlate50     = Color(0xFFF7F9FC) // Fondo de la app (off-white frío)
val CorpSurface     = Color(0xFFFFFFFF) // Tarjetas

// Aliases preservados para retro-compatibilidad con código existente.
val CorpGreyBg      = CorpSlate50
val CorpGreyCard    = CorpSurface
val CorpTextDark    = CorpSlate900
val CorpTextMuted   = CorpSlate500
val CorpDivider     = CorpSlate200

val CorpSuccess     = Color(0xFF1FA971)
val CorpWarning     = Color(0xFFD97706)
