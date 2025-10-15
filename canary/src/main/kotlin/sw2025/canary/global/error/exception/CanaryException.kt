package sw2025.canary.global.error.exception

abstract class CanaryException (
    val errorCode: ErrorCode
) : RuntimeException()