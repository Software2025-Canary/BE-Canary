package sw2025.canary.global.security.dto

data class TokenResponse(
    val accessToken: String,
    val refreshToken: String,
)
