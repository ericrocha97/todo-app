package dev.ericrocha.todo

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * A anotação @HiltAndroidApp é o ponto de partida para o Dagger Hilt.
 * Ela instrui o Hilt a gerar o código necessário para a injeção de dependência
 * em todo o aplicativo.
 *
 * Esta anotação deve ser colocada na classe que herda de Application.
 * Ela cria um contêiner de dependências que fica anexado ao ciclo de vida
 * do próprio aplicativo.
 */
@HiltAndroidApp
class TodoApp : Application() {
    // Esta classe herda de android.app.Application.
    // O Android cria uma instância desta classe quando o processo do seu
    // aplicativo é iniciado.
    //
    // Ao anotá-la com @HiltAndroidApp, você habilita o Hilt a gerenciar
    // o ciclo de vida das dependências em nível de aplicação.
    //
    // Geralmente, esta classe fica vazia, servindo apenas como o ponto
    // de entrada para a configuração do Hilt. Você pode adicionar lógicas
    // aqui que precisam ser executadas uma única vez quando o app inicia,
    // mas com Hilt, muitas dessas inicializações são feitas nos módulos de injeção.
}
