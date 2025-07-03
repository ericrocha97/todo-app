package dev.ericrocha.todo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.ericrocha.todo.data.Todo
import dev.ericrocha.todo.data.TodoDao
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @HiltViewModel, assim como no outro ViewModel, permite que o Hilt gerencie
 * a criação e o ciclo de vida desta classe.
 */
@HiltViewModel
class TodoViewModel
/**
     * @Inject constructor instrui o Hilt a fornecer a dependência `TodoDao`,
     * que foi definida no AppModule.
     */
    @Inject
    constructor(
        private val dao: TodoDao, // O DAO para interagir com o banco de dados.
    ) : ViewModel() {
        /**
         * Esta é a forma moderna de expor dados do Room para a UI com Compose.
         * 1. `dao.getAll()` retorna um `Flow<List<Todo>>`, que emite uma nova lista
         * sempre que os dados da tabela mudam.
         * 2. `stateIn` converte este `Flow` frio em um `StateFlow` quente.
         * - `viewModelScope`: O escopo da coroutine em que o fluxo será coletado.
         * - `SharingStarted.Eagerly`: Inicia a coleta do fluxo imediatamente e a
         * mantém ativa enquanto o `viewModelScope` estiver ativo. Uma alternativa
         * comum é `SharingStarted.WhileSubscribed(5000)` que mantém o fluxo
         * ativo por 5 segundos após o último observador se desinscrever.
         * - `emptyList()`: O valor inicial do `StateFlow` enquanto a primeira
         * consulta ao banco de dados não for concluída.
         */
        val todos = dao.getAll().stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

        /**
         * Função para adicionar uma nova tarefa.
         * @param text O conteúdo da tarefa.
         */
        fun addTodo(text: String) {
            // Inicia uma coroutine para executar a operação de banco de dados.
            viewModelScope.launch {
                // Chama a função suspend `insert` do DAO.
                // Como `todos` está observando o banco de dados, a UI será
                // atualizada automaticamente assim que a inserção for concluída.
                dao.insert(Todo(text = text))
            }
        }

        /**
         * Função para marcar uma tarefa como concluída ou não concluída.
         * @param todo O objeto de tarefa a ser modificado.
         */
        fun toggleTodo(todo: Todo) {
            // Inicia uma coroutine para a operação de atualização.
            viewModelScope.launch {
                // Chama a função suspend `update` do DAO.
                // `todo.copy(...)` cria uma nova instância de Todo com o valor
                // de `isCompleted` invertido, mantendo a imutabilidade.
                dao.update(todo.copy(isCompleted = !todo.isCompleted))
            }
        }
    }
