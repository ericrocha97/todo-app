package dev.ericrocha.todo.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import dev.ericrocha.todo.viewmodel.ProfileViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: ProfileViewModel = hiltViewModel(),
) {
    // Coleta os estados de `user` and `error` do ViewModel.
    // A UI será recomposta sempre que um desses valores mudar.
    val user by viewModel.user.collectAsState()
    val error by viewModel.error.collectAsState()

    // LaunchedEffect é usado para executar uma função `suspend` (como uma chamada de API)
    // de forma segura dentro do ciclo de vida de um Composable.
    // `Unit` como chave significa que o efeito será executado apenas uma vez,
    // quando o Composable for exibido pela primeira vez.
    LaunchedEffect(Unit) {
        viewModel.fetchUser("octocat") // Dispara a busca pelo usuário "octocat".
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Perfil") },
                // `navigationIcon` é o slot para um ícone no início da barra,
                // geralmente usado para o botão "voltar".
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        // `popBackStack` remove a tela atual da pilha de navegação,
                        // efetivamente voltando para a tela anterior.
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar")
                    }
                },
            )
        },
    ) { padding ->
        Column(modifier = Modifier.padding(padding).padding(16.dp)) {
            // A estrutura `when` é usada para exibir diferentes UIs com base no estado atual.
            when {
                // 1. Estado de Erro: Se `error` não for nulo, exibe a mensagem de erro.
                error != null -> {
                    Text(
                        text = error!!, // O "!!" é seguro aqui porque já verificamos que não é nulo.
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyLarge,
                    )
                }
                // 2. Estado de Sucesso: Se `user` não for nulo, exibe os dados do perfil.
                user != null -> {
                    // AsyncImage é um Composable da biblioteca Coil que carrega
                    // uma imagem de uma URL de forma assíncrona.
                    AsyncImage(
                        model = user?.avatar_url,
                        contentDescription = "Avatar do usuário",
                        modifier = Modifier.size(80.dp),
                    )
                    Text(
                        "@${user?.login}",
                        style = MaterialTheme.typography.titleLarge,
                    )
                    Text(user?.name ?: "Sem nome") // Usa um texto padrão se o nome for nulo.
                    Text(user?.bio ?: "Sem bio") // Usa um texto padrão se a bio for nula.
                }
                // 3. Estado de Carregamento: Se `error` e `user` forem nulos,
                // significa que a requisição está em andamento.
                else -> {
                    Text("Carregando...")
                }
            }
        }
    }
}
