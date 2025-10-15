package sw2025.canary.domain.user.presentation

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import sw2025.canary.global.security.jwt.JwtProvider

@RestController
class UserController(
    private val jwtProvider: JwtProvider
) {
    @PostMapping("/user")
    fun a () = jwtProvider.generateToken(1)

    @GetMapping("/private")
    fun b() = "정답이다 연금술사!"
}