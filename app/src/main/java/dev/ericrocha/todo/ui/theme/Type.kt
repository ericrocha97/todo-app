package dev.ericrocha.todo.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// O objeto Typography define a "escala de tipos" do seu aplicativo.
// É aqui que você define os estilos de texto padrão para diferentes usos,
// como títulos, corpo de texto, legendas, etc.
val Typography =
    Typography(
        // Define o estilo para "bodyLarge", que é um estilo comum para o corpo de texto principal.
        bodyLarge =
            TextStyle(
                fontFamily = FontFamily.Default, // Usa a fonte padrão do sistema (Roboto no Android).
                fontWeight = FontWeight.Normal, // Define a "grossura" da fonte.
                fontSize = 16.sp, // Define o tamanho da fonte em "scale-independent pixels".
                lineHeight = 24.sp, // Define a altura da linha para melhor legibilidade.
                letterSpacing = 0.5.sp, // Define o espaçamento entre as letras.
            ),
    /* Você pode descomentar e customizar outros estilos de texto aqui,
       como titleLarge (títulos grandes) ou labelSmall (rótulos pequenos).
       Manter uma escala de tipos consistente é fundamental para um bom design de UI.
     */
    )
