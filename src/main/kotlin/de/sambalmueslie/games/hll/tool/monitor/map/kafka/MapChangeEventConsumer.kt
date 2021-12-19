package de.sambalmueslie.games.hll.tool.monitor.map.kafka


import de.sambalmueslie.games.hll.tool.monitor.map.MapStatsProcessor
import de.sambalmueslie.games.hll.tool.monitor.map.api.MapChangeEvent
import io.micronaut.configuration.kafka.annotation.KafkaKey
import io.micronaut.configuration.kafka.annotation.KafkaListener
import io.micronaut.configuration.kafka.annotation.Topic

@KafkaListener(groupId = "hll-tool")
class MapChangeEventConsumer(private val processor: MapStatsProcessor) {

    @Topic(MapChangeEventProducer.TOPIC)
    fun handleMapChange(@KafkaKey serverId: Long, event: MapChangeEvent) {
        processor.mapChangeEvent(serverId, event)
    }

}
