package sw2025.canary.global.security.refresh

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.TimeToLive
import org.springframework.data.redis.core.index.Indexed

@RedisHash(value = "refresh")
class RefreshToken(
    @Id
    val id: Long,

    @Indexed
    var token: String,

    @TimeToLive
    var ttl: Long
) {
    fun update(token: String?, ttl: Long) {
        this.token = token!!
        this.ttl = ttl
    }
}