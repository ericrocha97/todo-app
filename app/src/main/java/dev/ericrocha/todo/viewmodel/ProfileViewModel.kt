package dev.ericrocha.todo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.ericrocha.todo.network.GitHubService
import dev.ericrocha.todo.network.GitHubUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @HiltViewModel anota a classe ViewModel para que o Hilt possa criá-la e
 * injetar suas dependências. Isso permite que o ViewModel seja facilmente
 * obtido na UI (Activity ou Composable) usando `hiltViewModel()`.
 */
@HiltViewModel
class ProfileViewModel
/**
     * @Inject constructor informa ao Hilt como criar uma instância deste ViewModel.
     * O Hilt irá automaticamente fornecer as dependências listadas como parâmetros
     * (neste caso, o `GitHubService`), pois ele já sabe como criá-las
     * a partir do seu AppModule.
     */
    @Inject
    constructor(
        private val service: GitHubService, // Dependência injetada pelo Hilt.
    ) : ViewModel() {
        // _user é um MutableStateFlow, um fluxo de dados que pode ter seu valor alterado.
        // Ele armazena o estado do usuário do GitHub. É privado ("_") para que
        // apenas o ViewModel possa modificá-lo.
        // O valor inicial é null, indicando que nenhum usuário foi carregado ainda.
        private val _user = MutableStateFlow<GitHubUser?>(null)

        // user é um StateFlow, uma versão somente leitura do _user.
        // A UI irá observar este fluxo para receber atualizações quando os dados do usuário mudarem.
        val user: StateFlow<GitHubUser?> = _user

        // Fluxo para armazenar mensagens de erro.
        private val _error = MutableStateFlow<String?>(null)

        // Versão somente leitura do fluxo de erro para a UI observar.
        val error: StateFlow<String?> = _error

        /**
         * Função pública que a UI chamará para buscar os dados de um usuário.
         * @param username O nome de usuário do GitHub a ser pesquisado.
         */
        fun fetchUser(username: String) {
            // viewModelScope.launch inicia uma nova coroutine no escopo do ViewModel.
            // Esta coroutine será automaticamente cancelada quando o ViewModel for destruído,
            // evitando vazamentos de memória e trabalho desnecessário.
            viewModelScope.launch {
                try {
                    // Limpa qualquer erro anterior antes de fazer a nova requisição.
                    _error.value = null
                    // Chama a função suspend `getUser` do nosso serviço de rede.
                    // O resultado (um objeto GitHubUser) é atribuído ao nosso StateFlow.
                    // A UI que está observando `user` será notificada e se redesenhará.
                    _user.value = service.getUser(username)
                } catch (e: Exception) {
                    // Se a chamada de rede falhar, o erro é capturado.
                    // A mensagem de erro é atribuída ao StateFlow `_error`.
                    // A UI pode observar `error` para mostrar uma mensagem ao usuário.
                    _error.value = e.message ?: "Erro ao carregar usuário"
                }
            }
        }
    }
