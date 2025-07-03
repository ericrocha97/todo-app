package dev.ericrocha.todo.data

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @Entity marca esta data class como uma tabela no banco de dados do Room.
 * Por padrão, o nome da tabela será o mesmo nome da classe ("Todo").
 * Você pode especificar um nome diferente com `@Entity(tableName = "minhas_tarefas")`.
 */
@Entity
data class Todo(
    /**
     * @PrimaryKey marca este campo como a chave primária da tabela.
     * A chave primária é um identificador único para cada linha (registro) na tabela.
     *
     * @param autoGenerate = true instrui o Room a gerar automaticamente um valor único
     * para este campo sempre que um novo `Todo` for inserido.
     * Isso é útil para IDs que são apenas contadores incrementais.
     */
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0, // O ID da tarefa. O valor padrão 0 é usado pelo Room ao criar novas instâncias.
    // Define uma coluna para armazenar o texto da tarefa.
    // O Room criará uma coluna do tipo TEXT no banco de dados para este campo.
    val text: String,
    // Define uma coluna para armazenar o estado da tarefa (concluída ou não).
    // O Room criará uma coluna do tipo BOOLEAN (geralmente armazenado como 0 ou 1)
    // para este campo. O valor padrão é `false`.
    val isCompleted: Boolean = false,
)
