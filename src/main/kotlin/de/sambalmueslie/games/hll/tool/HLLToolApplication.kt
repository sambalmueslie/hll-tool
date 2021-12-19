package de.sambalmueslie.games.hll.tool

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import io.micronaut.context.event.BeanCreatedEvent
import io.micronaut.context.event.BeanCreatedEventListener
import io.micronaut.runtime.Micronaut
import jakarta.inject.Singleton


class HLLToolApplication {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            Micronaut.build()
                .args(*args)
                .packages("de.sambalmueslie.games.hll.tool")
                .start()
        }
    }

    @Singleton
    internal class ObjectMapperBeanEventListener : BeanCreatedEventListener<ObjectMapper> {
        override fun onCreated(event: BeanCreatedEvent<ObjectMapper>): ObjectMapper {
            val mapper: ObjectMapper = event.bean
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            mapper.disable(SerializationFeature.WRITE_DURATIONS_AS_TIMESTAMPS)
            return mapper
        }
    }
}



