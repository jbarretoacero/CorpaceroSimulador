package co.com.corpacero.simulador

import co.com.corpacero.simulador.domain.calculators.Calculators
import org.junit.Assert.assertEquals
import org.junit.Test
import kotlin.math.abs

/**
 * Estos tests reproducen los valores calculados por la versión actual del
 * Simulador_V3.xlsx. Si una fórmula del Excel cambia, este test debe
 * actualizarse — y eso obliga a documentar el cambio.
 */
class CalculatorsTest {

    private fun assertClose(expected: Double, actual: Double, tolPct: Double = 1e-5) {
        val diff = abs(expected - actual)
        val ok = diff < 1e-6 || diff / abs(expected) < tolPct
        assertEquals("Expected $expected, got $actual (diff=$diff)", true, ok)
    }

    // PERLIN C — A=160 B=60 U=19 e=3 L=6
    @Test fun perlinC_pesoNegroKgM()    = assertClose(7.353216,  Calculators.perlinCPesoNegroKgM(160.0, 60.0, 19.0, 3.0))
    @Test fun perlinC_pesoNegroKgUnd()  = assertClose(44.119296, Calculators.perlinCPesoNegroKgUnd(7.353216, 6.0))
    @Test fun perlinC_pesoGalvKgM()     = assertClose(7.4657202, Calculators.perlinCPesoGalvKgM(7.353216))
    @Test fun perlinC_pesoGalvKgUnd()   = assertClose(44.7943212,Calculators.perlinCPesoGalvKgUnd(7.4657202, 6.0))

    // PERLIN CAJÓN — A=76 B=38 U=10 e=2 L=6
    @Test fun perlinCajon_pesoNegroKgM()   = assertClose(5.279232,  Calculators.perlinCajonPesoNegroKgM(76.0, 38.0, 10.0, 2.0))
    @Test fun perlinCajon_pesoNegroKgUnd() = assertClose(31.675392, Calculators.perlinCajonPesoNegroKgUnd(5.279232, 6.0))

    // TUBERÍA cuadrada — B=H=200 e=5 L=12
    @Test fun tuberiaRect_cuadrada_kgM() = assertClose(30.87828756, Calculators.tuberiaRectPesoKgM(200.0, 200.0, 5.0))
    @Test fun tuberiaRect_kgUnd()        = assertClose(370.5394507, Calculators.tuberiaPesoKgUnd(30.87828756, 12.0))

    // TUBERÍA circular — Dext=88.9 e=3 L=1
    @Test fun tuberiaCirc_kgM() = assertClose(6.47301915, Calculators.tuberiaCircPesoKgM(88.9, 3.0))

    // LÁMINA — A=400 L=2000 e=1.2 G90=275
    @Test fun lamina_kgUnd() = assertClose(7.756, Calculators.laminaPesoKgUnd(400.0, 2000.0, 1.2, 275.0))

    // CUB ARQ — e=0.35 G90=275
    @Test fun cubArq_galv_kgMl() = assertClose(3.68745, Calculators.cubArqGalvPesoKgMl(0.35, 275.0))
    @Test fun cubArq_galv_kgM2() = assertClose(3.51185714, Calculators.cubArqKgM2(3.68745))
    @Test fun cubArq_pint_kgMl() = assertClose(3.73861351, Calculators.cubArqPintPesoKgMl(0.35, 275.0))

    // CORPATECHO — e=0.75 G90=275
    @Test fun corpatecho_galv_kgMl() = assertClose(7.51825, Calculators.corpatechoGalvPesoKgMl(0.75, 275.0))
    @Test fun corpatecho_galv_kgM2() = assertClose(8.35361111, Calculators.corpatechoKgM2(7.51825))

    // TEJA ZINC — e=0.18 G90=275 (galv) / e=0.17 G40=120 (pint)
    @Test fun teja_galv_kgMl() = assertClose(1.50232, Calculators.tejaGalvPesoKgMl(0.18, 275.0))
    @Test fun teja_galv_kgM2() = assertClose(1.97154856, Calculators.tejaKgM2(1.50232))
    @Test fun teja_pint_kgMl() = assertClose(1.33182920, Calculators.tejaPintPesoKgMl(0.17, 120.0))

    // CORPALOSA — calibre 18 → e=1.2; ref=1.5"  (debe dividirse por 0.9)
    @Test fun corpalosa_kgM2_ref15()  = assertClose(13.02309333, Calculators.corpalosaPesoKgM2(1.2, "1.5\""))
    @Test fun corpalosa_kgM2_ref2()   = assertClose(11.720784,   Calculators.corpalosaPesoKgM2(1.2, "2\"MAX"))
    @Test fun corpalosa_kgM2_ref3()   = assertClose(13.02309333, Calculators.corpalosaPesoKgM2(1.2, "3\""))
    @Test fun corpalosa_kgM()         = assertClose(11.501184,   Calculators.corpalosaPesoKgM(1.2))

    // BOBINA — DI=450 DE=1600 W=1530 e=4
    @Test fun bobina_pesoKg()         = assertClose(22214.89058601, Calculators.bobinaPesoKg(450.0, 1600.0, 1530.0))
    @Test fun bobina_longitudDesdeP() = assertClose(462.89404255,   Calculators.bobinaLongitudDesdePesoM(22214.89058601, 1530.0, 4.0))
    @Test fun bobina_longitudM()      = assertClose(348.42374918,   Calculators.bobinaLongitudM(1220.0, 3.0, 10000.0))
}
