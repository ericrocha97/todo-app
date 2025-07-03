package dev.ericrocha.todo.network

import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Esta é uma interface que define os "endpoints" da API que seu aplicativo irá acessar.
 * A biblioteca Retrofit lerá esta interface e gerará o código de rede necessário
 * para fazer as chamadas HTTP.
 */
interface GitHubService {
    /**
     * @GET anota uma função que fará uma requisição HTTP GET.
     * O valor dentro dos parênteses ("users/{username}") é o caminho relativo
     * que será anexado à URL base definida na sua instância do Retrofit.
     *
     * "{username}" é um placeholder que será substituído dinamicamente.
     */
    @GET("users/{username}")
    /**
     * Define a função que buscará os dados de um usuário no GitHub.
     *
     * A palavra-chave `suspend` indica que esta é uma função de longa duração
     * que deve ser executada em uma coroutine para não bloquear a thread principal
     * enquanto espera a resposta da rede.
     *
     * @param username O valor deste parâmetro será usado para substituir o
     * placeholder "{username}" na URL da requisição. A anotação @Path("username")
     * faz essa ligação.
     *
     * @return Retorna um objeto do tipo `GitHubUser`. O Retrofit (com um conversor como o Gson)
     * irá automaticamente transformar a resposta JSON da API em uma instância desta classe.
     */
    suspend fun getUser(
        @Path("username") username: String,
    ): GitHubUser
}
