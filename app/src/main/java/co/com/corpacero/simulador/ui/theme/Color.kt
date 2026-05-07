package co.com.corpacero.simulador.ui.theme

import androidx.compose.ui.graphics.Color

// Paleta corporativa Corpacero — refinada para uso institucional.
// La identidad oficial está construida sobre el azul; el cyan se reserva como
// acento de datos. El violeta del logo histórico se mantiene como variable
// para compatibilidad pero no se usa en la UI.
val CorpBlue        = Color(0xFF003CA4) // Azul institucional (primary)
val CorpBlueDark    = Color(0xFF00257A) // Hover / barra superior
val CorpBlueDeep    = Color(0xFF001A55) // Texto sobre claro / headlines
val CorpBlueSoft    = Color(0xFFEAF0FA) // Fondos suaves de tarjetas de resultado
val CorpBlueAccent  = Color(0xFF1F5FD0) // Acento de barras y bordes activos
val CorpCyan        = Color(0xFF0098D7) // Acento de datos (sin saturación excesiva)
val CorpViolet      = Color(0xFF6978FF) // Legacy — no usar en nuevas vistas

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
