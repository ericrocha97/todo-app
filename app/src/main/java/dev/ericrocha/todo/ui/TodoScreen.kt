package dev.ericrocha.todo.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import dev.ericrocha.todo.viewmodel.TodoViewModel

// A anotação OptIn é necessária para usar componentes do Material 3 que
// ainda são considerados experimentais e podem mudar no futuro.
@OptIn(ExperimentalMaterial3Api::class)
@Composable // Marca esta função como um componente de UI do Jetpack Compose.
fun TodoScreen(
    // NavController é o objeto que gerencia a navegação entre as telas.
    // Ele é passado pela NavHost.
    navController: NavController,
    // hiltViewModel() é uma função de extensão que obtém a instância do ViewModel
    // gerenciada pelo Hilt para esta tela.
    viewModel: TodoViewModel = hiltViewModel(),
) {
    // `collectAsState` coleta os valores do StateFlow `todos` do ViewModel
    // e os converte em um State do Compose. Sempre que o StateFlow emitir
    // uma nova lista, a UI será recomposta (redesenhada) automaticamente.
    val todos by viewModel.todos.collectAsState()

    // `remember` e `mutableStateOf` criam um estado para a UI que sobrevive a
    // recomposições. `newTodo` armazena o texto que o usuário está digitando
    // no campo de nova tarefa.
    var newTodo by remember { mutableStateOf("") }

    // Scaffold é um layout pré-definido do Material Design que fornece uma
    // estrutura para a tela, incluindo slots para TopAppBar, BottomAppBar, etc.
    Scaffold(
        topBar = {
            // TopAppBar é a barra no topo da tela.
            TopAppBar(
                title = { Text("To-Do") }, // O título exibido na barra.
                actions = {
                    // `actions` é um slot para ícones de ação no final da barra.
                    IconButton(onClick = { navController.navigate("profile") }) {
                        Icon(Icons.Default.Person, contentDescription = "Perfil")
                    }
                },
            )
        },
    ) { padding ->
        // O conteúdo principal da tela recebe o preenchimento (padding)
        // para não ficar por baixo da TopAppBar.
        Column(modifier = Modifier.padding(padding).padding(16.dp)) {
            Row {
                TextField(
                    value = newTodo, // O valor exibido é o do nosso estado.
                    onValueChange = { newTodo = it }, // Quando o usuário digita, o estado é atualizado.
                    label = { Text("Nova tarefa") },
                )
                Spacer(modifier = Modifier.width(8.dp)) // Um espaço horizontal.
                Button(onClick = {
                    viewModel.addTodo(newTodo) // Chama a função do ViewModel para adicionar a tarefa.
                    newTodo = "" // Limpa o campo de texto após adicionar.
                }) { Text("Adicionar") }
            }
            Spacer(modifier = Modifier.height(16.dp)) // Um espaço vertical.
            // LazyColumn é uma lista rolável que só renderiza os itens visíveis
            // na tela, otimizando o desempenho para listas longas.
            LazyColumn {
                // `items` é uma função de extensão para LazyColumn que recebe uma lista
                // e cria um item de UI para cada elemento da lista.
                items(todos) { todo ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth().clickable { viewModel.toggleTodo(todo) },
                    ) {
                        // Checkbox para marcar a tarefa como concluída.
                        // `onCheckedChange = null` o torna somente leitura; o clique na linha inteira
                        // é o que dispara a ação de mudança.
                        Checkbox(checked = todo.isCompleted, onCheckedChange = null)
                        Text(
                            todo.text,
                            // Aplica um estilo de texto riscado se a tarefa estiver concluída.
                            textDecoration = if (todo.isCompleted) TextDecoration.LineThrough else null,
                        )
                    }
                }
            }
        }
    }
}
