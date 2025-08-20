package learn_mate_it.dev.domain.auth.infra.application.dto.response


data class ApplePublicKeyResponse(
    val keys: List<Key>
)

data class Key(
    val kty: String,
    val kid: String,
    val use: String,
    val alg: String,
    val n: String,
    val e: String
)