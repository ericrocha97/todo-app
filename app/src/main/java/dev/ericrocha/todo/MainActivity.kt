package dev.ericrocha.todo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.ericrocha.todo.ui.ProfileScreen
import dev.ericrocha.todo.ui.TodoScreen

/**
 * @AndroidEntryPoint habilita a injeção de dependências do Hilt nesta Activity.
 * Isso significa que o Hilt pode fornecer instâncias de objetos (como ViewModels)
 * para esta classe.
 * Para que funcione, a Activity também precisa herdar de ComponentActivity (ou FragmentActivity).
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    /**
     * O método onCreate é o primeiro a ser chamado quando a Activity é criada.
     * É aqui que a maior parte da inicialização da UI e da lógica deve acontecer.
     * @param savedInstanceState Se a Activity estiver sendo recriada após ter sido
     * destruída (ex: rotação de tela), este Bundle contém o estado salvo anteriormente.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        // Chama a implementação do método onCreate da classe pai (ComponentActivity).
        // É crucial chamar super.onCreate() para que o ciclo de vida da Activity funcione corretamente.
        super.onCreate(savedInstanceState)

        // setContent é a função principal do Jetpack Compose em uma Activity.
        // Ela define o conteúdo da tela usando funções Composable.
        // Todo o código de UI dentro deste bloco será renderizado na tela.
        setContent {
            // **CORREÇÃO:** Verifica se o tema do sistema está no modo escuro.
            val isSystemInDarkMode = isSystemInDarkTheme()

            // **CORREÇÃO:** Escolhe o esquema de cores dinâmico (Material You) com base no tema do sistema.
            val colorScheme =
                if (isSystemInDarkMode) {
                    dynamicDarkColorScheme(this) // Se estiver escuro, usa o tema dinâmico escuro.
                } else {
                    dynamicLightColorScheme(this) // Senão, usa o tema dinâmico claro.
                }

            // MaterialTheme aplica o tema definido (cores, tipografia, formas)
            // a todos os Composables dentro de seu escopo.
            MaterialTheme(colorScheme = colorScheme) {
                // rememberNavController cria e gerencia o estado do controlador de navegação.
                // "remember" garante que o controlador não seja recriado a cada recomposição,
                // mantendo o estado da navegação (a pilha de telas).
                val navController = rememberNavController()

                // NavHost é o contêiner que exibe os destinos (telas) da navegação.
                // Ele precisa do navController para gerenciar as transições e da
                // rota inicial (startDestination).
                NavHost(navController = navController, startDestination = "todo") {
                    // A função composable mapeia uma "rota" (uma String única) para uma tela (um Composable).
                    // Quando o navController navegar para a rota "todo", o conteúdo deste bloco será exibido.
                    composable("todo") {
                        // Renderiza a tela de tarefas, passando o navController para que
                        // ela possa iniciar a navegação para outras telas.
                        TodoScreen(navController)
                    }

                    // Define a segunda rota, "profile".
                    composable("profile") {
                        // Renderiza a tela de perfil.
                        ProfileScreen(navController)
                    }
                }
            }
        }
    }
}
