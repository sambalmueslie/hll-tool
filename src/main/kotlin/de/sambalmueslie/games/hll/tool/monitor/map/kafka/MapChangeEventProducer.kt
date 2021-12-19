package de.sambalmueslie.games.hll.tool.monitor.map.kafka

import de.sambalmueslie.games.hll.tool.monitor.map.api.MapChangeEvent
import io.micronaut.configuration.kafka.annotation.KafkaClient
import io.micronaut.configuration.kafka.annotation.KafkaKey
import io.micronaut.configuration.kafka.annotation.Topic

@KafkaClient
interface MapChangeEventProducer {
    @Topic(TOPIC)
    fun sendEvent(@KafkaKey serverId: Long, event: MapChangeEvent)

    companion object {
        const val TOPIC = "hll.evt.maps"
    }
}
