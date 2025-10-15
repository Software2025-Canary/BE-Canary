package sw2025.canary.global.security.jwt.exception

import sw2025.canary.global.error.exception.CanaryException
import sw2025.canary.global.error.exception.ErrorCode

object InvalidJwtException :CanaryException(
    ErrorCode.INVALID_TOKEN
)