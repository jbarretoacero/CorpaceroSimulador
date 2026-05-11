# Simulador Corpacero — Android

Aplicación móvil Android que iguala el archivo
`Simulador_V3.xlsx` (Simulador de Pesos para Productos Transformados de
Corpacero).

## Descarga

Última versión publicada: **v1.0.4** (versionCode 5) — APK firmada release.

[Descargar Simulador Corpacero v1.0.4 (APK)](https://github.com/jbarretoacero/CorpaceroSimulador/releases/download/v1.0.4/CorpaceroSimulador-v1.0.4.apk)

> En Android, abrir la APK descargada y permitir "Instalar de fuentes
> desconocidas" para el navegador / gestor de archivos cuando lo solicite.

> Nota: v1.0.4 fue firmada con un keystore nuevo. Si tienes v1.0.3
> instalada, desinstálala antes de actualizar (Android rechaza
> actualizaciones con firma distinta).

### Novedades de v1.0.4

- Nueva pantalla de splash con el logo completo de Corpacero sobre fondo blanco.
- Icono de la app rediseñado: símbolo "C" centrado y a mayor escala.
- Header del Home y tarjetas de calculadoras con gradiente suave para una
  apariencia más uniforme (sin el borde visible entre el diagrama y el fondo).

## Stack

- **Kotlin** + **Jetpack Compose** (Material 3)
- **AGP 8.5**, **Gradle 8.7**, **JDK 17+**
- `minSdk 24` (Android 7.0+) — `targetSdk 34`
- Arquitectura simple **UI ↔ dominio**, con la lógica de negocio en
  `domain/calculators/Calculators.kt`.
- 100% offline.

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
        ├── splash/                    ← Splash con logo completo
        └── ...                        ← Una pantalla por calculadora
```
---
**Disclaimer**: los valores son de referencia. No deben usarse para
decisiones comerciales sin la validación del área técnica de Corpacero.
