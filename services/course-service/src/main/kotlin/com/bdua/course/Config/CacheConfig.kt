package com.bdua.course.Config

import com.bdua.course.DTO.CourseResponseDto
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.serializer.JacksonJsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializationContext

@Configuration
@EnableCaching
class CacheConfig {

    @Bean
    fun redisCacheConfiguration(): RedisCacheConfiguration {
//dto to json to redis
        val serializer =
            JacksonJsonRedisSerializer(CourseResponseDto::class.java)

        return RedisCacheConfiguration.defaultCacheConfig()
            .serializeValuesWith(

                RedisSerializationContext.SerializationPair.fromSerializer(
                    serializer
                )
            )
    }
}