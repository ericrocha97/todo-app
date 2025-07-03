package dev.ericrocha.todo.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.ericrocha.todo.data.AppDatabase
import dev.ericrocha.todo.data.TodoDao
import dev.ericrocha.todo.network.GitHubService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * @Module informa ao Hilt que esta classe (ou objeto) contém definições
 * sobre como fornecer instâncias de certos tipos (dependências).
 *
 * @InstallIn(SingletonComponent::class) especifica o escopo deste módulo.
 * SingletonComponent::class significa que todas as dependências definidas aqui
 * terão um ciclo de vida de singleton (única instância) atrelado ao ciclo de vida
 * da aplicação. Elas serão criadas uma vez e reutilizadas em todo o app.
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    /**
     * @Provides marca esta função como um provedor de dependência.
     * O Hilt usará esta função para saber como criar uma instância de AppDatabase.
     *
     * @Singleton garante que o Hilt crie apenas UMA instância de AppDatabase
     * e a reutilize sempre que for solicitada.
     *
     * @param context O Hilt injeta automaticamente o contexto da aplicação
     * graças à anotação @ApplicationContext.
     */
    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context,
    ): AppDatabase =
        Room
            .databaseBuilder(
                context,
                AppDatabase::class.java,
                "todo-database", // O nome do arquivo do banco de dados.
            ).build()

    /**
     * @Provides ensina ao Hilt como criar uma instância de TodoDao.
     * Como o Hilt já sabe criar um AppDatabase (da função acima), ele pode
     * passar essa instância como parâmetro para esta função.
     * Esta dependência não é @Singleton porque o DAO é leve e está atrelado
     * à instância singleton do banco de dados.
     */
    @Provides
    fun provideTodoDao(database: AppDatabase): TodoDao = database.todoDao()

    /**
     * @Provides e @Singleton para a instância do OkHttpClient.
     * O OkHttpClient é o responsável por fazer as requisições HTTP.
     * É uma boa prática configurá-lo e reutilizá-lo.
     */
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        // Cria um interceptor para logar os detalhes das requisições e respostas.
        // Extremamente útil para depuração de chamadas de rede.
        val logging =
            HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }

        return OkHttpClient
            .Builder()
            .addInterceptor(logging) // Adiciona o interceptor de log.
            .retryOnConnectionFailure(true) // Habilita a nova tentativa automática em caso de falha de conexão.
            .connectTimeout(15, TimeUnit.SECONDS) // Define o tempo máximo para estabelecer uma conexão.
            .readTimeout(15, TimeUnit.SECONDS) // Define o tempo máximo para ler dados da resposta.
            .writeTimeout(15, TimeUnit.SECONDS) // Define o tempo máximo para enviar dados da requisição.
            .build()
    }

    /**
     * @Provides e @Singleton para a instância do Retrofit.
     * Retrofit é a biblioteca que transforma a interface da API (GitHubService)
     * em chamadas de rede concretas.
     *
     * @param okHttpClient O Hilt injeta a instância do OkHttpClient configurada na função acima.
     */
    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit
            .Builder()
            .baseUrl("https://api.github.com/") // A URL base para todas as chamadas da API.
            .client(okHttpClient) // Usa o nosso cliente OkHttp customizado.
            .addConverterFactory(GsonConverterFactory.create()) // Adiciona o conversor para transformar JSON em objetos Kotlin.
            .build()

    /**
     * @Provides e @Singleton para a nossa interface de serviço da API.
     *
     * @param retrofit O Hilt injeta a instância do Retrofit.
     */
    @Provides
    @Singleton
    fun provideGitHubService(retrofit: Retrofit): GitHubService = retrofit.create(GitHubService::class.java)
}
