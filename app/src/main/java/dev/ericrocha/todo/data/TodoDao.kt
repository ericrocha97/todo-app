package dev.ericrocha.todo.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

/**
 * @Dao (Data Access Object) marca esta interface como um objeto de acesso a dados do Room.
 * É aqui que você define todos os seus métodos para interagir com o banco de dados
 * (ler, inserir, atualizar, deletar).
 * O Room gerará o código necessário para implementar os métodos desta interface.
 */
@Dao
interface TodoDao {
    /**
     * @Query permite que você escreva uma consulta SQL personalizada.
     * "SELECT * FROM todo" seleciona todas as colunas (*) de todas as linhas da tabela "todo".
     *
     * O tipo de retorno é Flow<List<Todo>>. Isso é muito poderoso:
     * - Flow é um fluxo de dados assíncrono do Kotlin Coroutines.
     * - O Room fará com que este Flow emita automaticamente uma nova lista de `Todo`
     * sempre que os dados na tabela "todo" mudarem.
     * - Isso permite que sua UI reaja a mudanças no banco de dados em tempo real,
     * sem a necessidade de consultar o banco de dados manualmente de novo.
     */
    @Query("SELECT * FROM todo")
    fun getAll(): Flow<List<Todo>>

    /**
     * @Insert marca este método para inserir um novo registro no banco de dados.
     * O Room gerará o código para inserir o objeto `Todo` passado como parâmetro.
     *
     * A palavra-chave `suspend` indica que esta é uma função de longa duração
     * que deve ser chamada de dentro de uma coroutine (para não bloquear a thread principal).
     * Todas as operações de banco de dados (exceto as que retornam Flow) devem ser `suspend`.
     */
    @Insert
    suspend fun insert(todo: Todo)

    /**
     * @Update marca este método para atualizar um registro existente no banco de dados.
     * O Room usa a chave primária (`id`) do objeto `Todo` passado como parâmetro
     * para encontrar o registro correspondente na tabela e atualizar seus campos.
     *
     * Assim como @Insert, esta função também é `suspend`.
     */
    @Update
    suspend fun update(todo: Todo)
}
