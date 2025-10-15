package sw2025.canary.domain.user.exception

import sw2025.canary.global.error.exception.CanaryException
import sw2025.canary.global.error.exception.ErrorCode

object UserNotFoundException : CanaryException(
    ErrorCode.USER_NOT_FOUND,
)
