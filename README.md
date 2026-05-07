# Simulador Corpacero — Android

Aplicación móvil Android que reproduce el comportamiento del archivo
`Simulador_V3.xlsx` (Simulador de Pesos para Productos Transformados de
Corpacero) como una experiencia nativa amigable y de calidad empresarial.

## Stack

- **Kotlin** + **Jetpack Compose** (Material 3)
- **AGP 8.5**, **Gradle 8.7**, **JDK 17**
- `minSdk 24` (Android 7.0+) — `targetSdk 34`
- Arquitectura simple **UI ↔ dominio**, con la lógica de negocio aislada en
  `domain/calculators/Calculators.kt` para auditoría del área técnica.
- Sin dependencias de red, sin base de datos. La app es 100% offline.

## Calculadoras incluidas (1:1 con el Excel)

| # | Pantalla              | Hoja Excel        | Notas                                      |
|---|-----------------------|-------------------|--------------------------------------------|
| 1 | Perlín en C           | CALC PERLIN C     | Negro y galvanizado (kg/m, kg/und)         |
| 2 | Perlín cajón          | CALC PERLIN CAJÓN | Sólo negro                                 |
| 3 | Tubería               | CALC TUBERÍA      | Tabs: rectangular/cuadrada y circular      |
| 4 | Lámina galvanizada    | CALC LÁMINA       | Recubrimientos G30 / G40 / G60 / G90 / Sin |
| 5 | Cubierta arquitect.   | CALC CUB ARQ      | Galvanizada y pintada                      |
| 6 | Corpatecho            | CALC COPR TECHO   | Galvanizada (G60/G90), pintada (G40-G90)   |
| 7 | Teja de zinc          | CALC TEJA ZINC    | Galvanizada y pintada                      |
| 8 | Corpalosa             | CALC CORPALOSA    | Calibres 22/20/18/16, refs 1.5"/2"MAX/3"   |
| 9 | Rollo galvanizado     | BOBINA            | Tabs: peso desde dimensiones / longitud    |

## Fidelidad con el Excel

Todas las fórmulas fueron extraídas literalmente de las celdas del archivo
y comentadas con su origen en `Calculators.kt`. La carpeta
`app/src/test/java/.../CalculatorsTest.kt` valida los 23 resultados contra
los valores recalculados por LibreOffice del `.xlsx` original.

Para correr los tests:

```bash
./gradlew :app:testDebugUnitTest
```

## Recursos visuales

Los **17 recursos gráficos del Excel** (logo Corpacero + 16 diagramas
técnicos) se preservaron tal cual y se ubicaron en `app/src/main/res/drawable/`:

- `logo_corpacero.png` — logo oficial usado en splash y header
- `diag_perlin_c.png`, `diag_perlin_cajon.png`, `diag_tuberia_rect.png`,
  `diag_tuberia_circ.png`, `diag_lamina.png`, `diag_cub_arq.png`,
  `diag_corpatecho.png`, `diag_teja_zinc.png`,
  `diag_corpalosa_15.png`, `diag_corpalosa_2.png`, `diag_corpalosa_3.png`,
  `diag_bobina.png` — diagramas técnicos
- `hero_*` — fotografías del Excel disponibles para uso futuro

La paleta de la app fue extraída del logo:
`#003CA4` (azul primario), `#00B4FF` (cian), `#6978FF` (violeta).

## Estructura

```
app/src/main/java/co/com/corpacero/simulador/
├── MainActivity.kt
├── SimuladorApp.kt
├── domain/
│   └── calculators/Calculators.kt    ← Fórmulas reproducidas del Excel
└── ui/
    ├── theme/                         ← Color, Type, Theme
    ├── navigation/                    ← Routes + NavGraph
    ├── components/                    ← Inputs, dropdowns, cards, scaffold
    └── screens/
        ├── home/                      ← Pantalla de inicio (grid)
        ├── perlin_c/, perlin_cajon/, tuberia/, lamina/,
        ├── cub_arq/, corpatecho/, teja_zinc/,
        ├── corpalosa/, bobina/        ← Una pantalla por calculador
```

## Cómo abrirlo

1. Instala **Android Studio Hedgehog (2023.1.1)** o superior.
2. `File → Open` → selecciona la carpeta `CorpaceroSimulador`.
3. Espera a que Gradle sincronice.
4. Conecta un dispositivo o crea un emulador (API 24+).
5. Run ▶.

> **Nota**: el ZIP no incluye `gradlew` binario. Genera el wrapper con
> `gradle wrapper --gradle-version 8.7` desde la raíz, o deja que Android
> Studio lo cree automáticamente al primer Sync.

## Próximos pasos sugeridos

- Compartir resultados (PDF / mensaje) desde cada pantalla
- Histórico de cálculos persistente con Room
- Modo oscuro institucional
- Internacionalización (en/pt-BR)
- Integración con ventas / generación de cotización

---
**Disclaimer**: los valores son de referencia. No deben usarse para
decisiones comerciales sin la validación del área técnica de Corpacero.
