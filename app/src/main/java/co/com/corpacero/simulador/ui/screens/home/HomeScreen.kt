package co.com.corpacero.simulador.ui.screens.home

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import co.com.corpacero.simulador.R
import co.com.corpacero.simulador.ui.components.BrandGradient
import co.com.corpacero.simulador.ui.components.DisclaimerBanner
import co.com.corpacero.simulador.ui.navigation.Routes
import co.com.corpacero.simulador.ui.theme.CorpBlueAccent
import co.com.corpacero.simulador.ui.theme.CorpBlueDeep
import co.com.corpacero.simulador.ui.theme.CorpBlueSoft
import co.com.corpacero.simulador.ui.theme.CorpSlate100
import co.com.corpacero.simulador.ui.theme.CorpSlate200
import co.com.corpacero.simulador.ui.theme.CorpSlate50
import co.com.corpacero.simulador.ui.theme.CorpSlate500
import co.com.corpacero.simulador.ui.theme.CorpSlate700

private data class CalcEntry(
    val route: Routes,
    val titleRes: Int,
    @DrawableRes val image: Int,
    val category: Category,
)

private enum class Category(val labelRes: Int) {
    Perfiles(R.string.cat_perfiles),
    Cubiertas(R.string.cat_cubiertas),
    Otros(R.string.cat_otros),
}

private val calcs = listOf(
    CalcEntry(Routes.PerlinC,     R.string.calc_perlin_c,     R.drawable.diag_perlin_c,     Category.Perfiles),
    CalcEntry(Routes.PerlinCajon, R.string.calc_perlin_cajon, R.drawable.diag_perlin_cajon, Category.Perfiles),
    CalcEntry(Routes.Tuberia,     R.string.calc_tuberia,      R.drawable.diag_tuberia_rect, Category.Perfiles),

    CalcEntry(Routes.Lamina,      R.string.calc_lamina,       R.drawable.diag_lamina,       Category.Cubiertas),
    CalcEntry(Routes.CubArq,      R.string.calc_cub_arq,      R.drawable.diag_cub_arq,      Category.Cubiertas),
    CalcEntry(Routes.Corpatecho,  R.string.calc_corpatecho,   R.drawable.diag_corpatecho,   Category.Cubiertas),
    CalcEntry(Routes.TejaZinc,    R.string.calc_teja_zinc,    R.drawable.diag_teja_zinc,    Category.Cubiertas),

    CalcEntry(Routes.Corpalosa,   R.string.calc_corpalosa,    R.drawable.diag_corpalosa_15, Category.Otros),
    CalcEntry(Routes.Bobina,      R.string.calc_bobina,       R.drawable.diag_bobina,       Category.Otros),
)

@OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(onSelect: (Routes) -> Unit) {
    Scaffold(containerColor = CorpSlate50) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
        ) {
            BrandHeader()
            CalcGrid(modifier = Modifier.weight(1f), onSelect = onSelect)
            DisclaimerBanner(
                text = stringResource(R.string.disclaimer),
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
            )
        }
    }
}

@Composable
private fun BrandHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(bottomStart = 28.dp, bottomEnd = 28.dp))
            .background(BrandGradient())
            .padding(horizontal = 24.dp, vertical = 28.dp),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(
                        Brush.linearGradient(
                            colors = listOf(Color.White, CorpBlueSoft),
                        )
                    ),
                contentAlignment = Alignment.Center,
            ) {
                Image(
                    painter = painterResource(R.drawable.logo_corpacero),
                    contentDescription = "Corpacero",
                    modifier = Modifier.padding(8.dp),
                    contentScale = ContentScale.Fit,
                )
            }
            Spacer(Modifier.width(18.dp))
            Column {
                Text(
                    text = stringResource(R.string.app_name),
                    color = Color.White,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.SemiBold,
                )
                Spacer(Modifier.height(2.dp))
                Text(
                    text = stringResource(R.string.app_subtitle).uppercase(),
                    color = Color.White.copy(alpha = 0.78f),
                    style = MaterialTheme.typography.labelSmall,
                )
            }
        }
    }
}

@Composable
private fun CalcGrid(modifier: Modifier = Modifier, onSelect: (Routes) -> Unit) {
    val grouped = calcs.groupBy { it.category }
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 14.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        grouped.forEach { (cat, items) ->
            item(span = { androidx.compose.foundation.lazy.grid.GridItemSpan(maxLineSpan) }) {
                CategoryHeader(stringResource(cat.labelRes))
            }
            items(items, key = { it.route.path }) { entry ->
                CalcCard(entry = entry, onClick = { onSelect(entry.route) })
            }
        }
    }
}

@Composable
private fun CategoryHeader(text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(horizontal = 4.dp, vertical = 6.dp),
    ) {
        Box(
            Modifier
                .height(14.dp)
                .width(3.dp)
                .background(CorpBlueAccent, RoundedCornerShape(2.dp)),
        )
        Spacer(Modifier.width(10.dp))
        Text(
            text = text.uppercase(),
            style = MaterialTheme.typography.labelLarge,
            color = CorpSlate700,
            fontWeight = FontWeight.SemiBold,
        )
    }
}

@Composable
private fun CalcCard(entry: CalcEntry, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .clickable(onClick = onClick)
            .border(BorderStroke(1.dp, CorpSlate200), RoundedCornerShape(12.dp)),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = CorpSlate100),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(96.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .border(BorderStroke(1.dp, CorpSlate200), RoundedCornerShape(8.dp))
                    .background(Color.White),
                contentAlignment = Alignment.Center,
            ) {
                Image(
                    painter = painterResource(entry.image),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp),
                )
            }
            Spacer(Modifier.height(12.dp))
            Text(
                text = stringResource(entry.titleRes),
                style = MaterialTheme.typography.titleSmall,
                color = CorpBlueDeep,
                fontWeight = FontWeight.SemiBold,
            )
            Spacer(Modifier.height(2.dp))
            Text(
                text = "Calcular peso",
                style = MaterialTheme.typography.labelSmall,
                color = CorpSlate500,
            )
        }
    }
}
