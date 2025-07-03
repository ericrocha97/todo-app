package dev.ericrocha.todo.data

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * @Database é a anotação principal do Room para definir a classe do banco de dados.
 *
 * @param entities É uma lista de todas as classes (tabelas) que farão parte deste banco de dados.
 * Neste caso, apenas a tabela `Todo` está incluída.
 *
 * @param version É a versão do seu banco de dados. Se você alterar o esquema (mudar colunas,
 * adicionar tabelas, etc.), você DEVE incrementar este número e fornecer uma
 * estratégia de migração, caso contrário o app irá quebrar.
 *
 * @param exportSchema Por padrão, o Room exporta o esquema do banco de dados para um arquivo JSON
 * na pasta do projeto. Isso é útil para versionamento e depuração.
 * Definir como `false` desabilita essa exportação, o que é comum para projetos
 * menores ou quando você não pretende gerenciar o histórico de esquemas.
 */
@Database(entities = [Todo::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    // Esta é a classe principal do banco de dados. Ela deve ser abstrata e herdar de RoomDatabase.
    // O Room irá gerar uma implementação concreta desta classe em tempo de compilação.

    /**
     * Declara uma função abstrata que retorna a interface DAO (Data Access Object).
     * Para cada DAO que seu banco de dados tiver, você precisa de uma função abstrata como esta.
     * O Room usará esta função para fornecer uma instância concreta do seu `TodoDao`.
     *
     * @return Uma instância da interface TodoDao para interagir com a tabela "todo".
     */
    abstract fun todoDao(): TodoDao
}
