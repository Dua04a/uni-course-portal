package com.bdua.students.Security

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

//this class responsible for auth every request
//works on every request arrived in the server before arrive to controller
@Component
class JwtAuthFilter : OncePerRequestFilter() {

    @Autowired
    private lateinit var jwtUtil: JwtUtil

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        //read the header
        val authHeader = request.getHeader("Authorization")
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            val jwt = authHeader.substring(7)

            try {
                val username = jwtUtil.extractUsername(jwt)
                val role = jwtUtil.extractRole(jwt)

                if (jwtUtil.isTokenValid(jwt, username)) {
                    val authority = SimpleGrantedAuthority("ROLE_$role")

                    val authentication =
                        UsernamePasswordAuthenticationToken.authenticated(
                            username,
                            null,
                            listOf(authority)
                        )

                    SecurityContextHolder.getContext().authentication = authentication
                }

            } catch (e: Exception) {
                println("Invalid JWT token: ${e.message}")
            }
        }

        filterChain.doFilter(request, response)
    }
}
