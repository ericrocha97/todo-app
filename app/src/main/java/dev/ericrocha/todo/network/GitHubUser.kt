package dev.ericrocha.todo.network

/**
 * Esta é uma "data class" que representa o modelo de dados de um usuário do GitHub,
 * conforme retornado pela API.
 * O Retrofit e o Gson usarão esta classe para mapear os campos do JSON da resposta
 * para as propriedades desta classe.
 *
 * É crucial que os nomes das propriedades nesta classe correspondam exatamente
 * aos nomes das chaves no objeto JSON retornado pela API. Se os nomes forem
 * diferentes, você pode usar a anotação `@SerializedName("nome_no_json")`
 * em cada propriedade.
 */
data class GitHubUser(
    // Mapeia a chave "login" do JSON para a propriedade `login`.
    val login: String,
    // Mapeia a chave "avatar_url" do JSON para a propriedade `avatar_url`.
    // Armazena a URL da imagem de perfil do usuário.
    val avatar_url: String,
    // Mapeia a chave "name" do JSON.
    // Esta propriedade é anulável (String?) porque nem todos os usuários do GitHub
    // definem um nome público. Se o campo não existir no JSON, o Gson o atribuirá
    // como `null` em vez de causar um erro.
    val name: String?,
    // Mapeia a chave "bio" do JSON.
    // Também é anulável, pois a biografia é um campo opcional no perfil do GitHub.
    val bio: String?,
)
