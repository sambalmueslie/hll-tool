package de.sambalmueslie.games.hll.tool.util


import io.micronaut.core.convert.ConversionContext
import io.micronaut.data.model.runtime.convert.AttributeConverter
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.time.Duration

@Singleton
class DurationConverter : AttributeConverter<Duration, Long> {

    companion object {
        val logger: Logger = LoggerFactory.getLogger(DurationConverter::class.java)
    }

    override fun convertToPersistedValue(entityValue: Duration?, context: ConversionContext): Long? {
        return entityValue?.seconds
    }

    override fun convertToEntityValue(persistedValue: Long?, context: ConversionContext): Duration? {
        return persistedValue?.let { Duration.ofSeconds(persistedValue) }
    }


}
