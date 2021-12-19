package de.sambalmueslie.games.hll.tool.monitor.map.kafka

import de.sambalmueslie.games.hll.tool.monitor.map.api.MapChangeEventStats
import io.micronaut.configuration.kafka.annotation.KafkaClient
import io.micronaut.configuration.kafka.annotation.KafkaKey
import io.micronaut.configuration.kafka.annotation.Topic

@KafkaClient(id = "hll-tool")
interface MapChangeEventStatsProducer {
    @Topic(TOPIC)
    fun sendEvent(@KafkaKey serverId: Long, event: MapChangeEventStats)

    companion object {
        const val TOPIC = "hll.stats.map"
    }
}
