package dev.ericrocha.todo.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

// Define o esquema de cores estático para o tema escuro.
// Ele usa as cores definidas no arquivo Color.kt (Purple80, etc.).
// Estas cores são usadas em dispositivos com Android mais antigo (abaixo do 12)
// ou quando a cor dinâmica está desativada.
private val DarkColorScheme =
    darkColorScheme(
        primary = Purple80,
        secondary = PurpleGrey80,
        tertiary = Pink80,
    )

// Define o esquema de cores estático para o tema claro.
private val LightColorScheme =
    lightColorScheme(
        primary = Purple40,
        secondary = PurpleGrey40,
        tertiary = Pink40,
    /* Você pode descomentar e sobrescrever outras cores padrão do tema aqui,
       como a cor de fundo (background), a cor da superfície (surface), etc. */
    )

/**
 * Este é o Composable principal do tema do seu aplicativo.
 * Ele deve envolver todo o conteúdo do seu app (geralmente na MainActivity)
 * para aplicar as cores e a tipografia de forma consistente.
 */
@Composable
fun TodoTheme(
    // Parâmetro para forçar o tema escuro. Por padrão, ele usa a configuração do sistema.
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Parâmetro para habilitar/desabilitar a cor dinâmica (Material You).
    // Por padrão, é habilitada.
    dynamicColor: Boolean = true,
    // O conteúdo da sua UI que receberá o tema.
    content: @Composable () -> Unit,
) {
    // Lógica para determinar qual esquema de cores usar.
    val colorScheme =
        when {
            // Se a cor dinâmica estiver habilitada E o dispositivo for Android 12 (SDK 31) ou superior...
            dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
                val context = LocalContext.current
                // ...então usa um esquema de cores gerado a partir do papel de parede do usuário.
                if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
            }
            // Se a condição acima for falsa, mas o tema escuro estiver ativo...
            darkTheme -> DarkColorScheme // ...usa o nosso esquema de cores escuro estático.
            // Caso contrário...
            else -> LightColorScheme // ...usa o nosso esquema de cores claro estático.
        }

    // MaterialTheme é o Composable que efetivamente aplica o tema.
    MaterialTheme(
        colorScheme = colorScheme, // Aplica o esquema de cores escolhido.
        typography = Typography, // Aplica os estilos de texto definidos em Type.kt.
        content = content, // Renderiza o conteúdo do app dentro deste tema.
    )
}
