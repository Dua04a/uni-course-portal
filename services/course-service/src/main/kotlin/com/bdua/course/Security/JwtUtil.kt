package com.bdua.course.Security

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import java.util.Date

//this class responsible for create,read,Auth token
@Component
class JwtUtil {

    @Value("\${jwt.secret}")
    private lateinit var secret: String

    @Value("\${jwt.expiration}")
    private var expiration: Long = 0

    private fun getKey() =
        Keys.hmacShaKeyFor(secret.toByteArray(StandardCharsets.UTF_8))
//create
fun generateToken(username: String, role: String): String {
    return Jwts.builder()
        .subject(username)
        .claim("role", role)
        .issuedAt(Date())
        .expiration(Date(System.currentTimeMillis() + expiration))
        .signWith(getKey())
        .compact()
    }
//extract
    fun extractUsername(token: String): String {

        return Jwts.parser()
            .verifyWith(getKey())
            .build()
            .parseSignedClaims(token)
            .payload
            .subject
    }
    //extract role
    fun extractRole(token: String): String {
        return Jwts.parser()
            .verifyWith(getKey())
            .build()
            .parseSignedClaims(token)
            .payload["role"]
            .toString()
    }
//make sure that the token belong to the user and make sure the token is unexpired
    fun isTokenValid(token: String, username: String): Boolean {

        return try {
            val extractedUsername = extractUsername(token)

            extractedUsername == username &&
                    !isTokenExpired(token)

        } catch (e: Exception) {
            false
        }
    }

    private fun isTokenExpired(token: String): Boolean {

        val expirationDate = Jwts.parser()
            .verifyWith(getKey())
            .build()
            .parseSignedClaims(token)
            .payload
            .expiration

        return expirationDate.before(Date())
        //compare time
    }
}