package de.sambalmueslie.games.hll.tool.monitor.slot.kafka


import de.sambalmueslie.games.hll.tool.monitor.slot.SlotStatsProcessor
import de.sambalmueslie.games.hll.tool.monitor.slot.api.SlotsChangeEvent
import io.micronaut.configuration.kafka.annotation.KafkaKey
import io.micronaut.configuration.kafka.annotation.KafkaListener
import io.micronaut.configuration.kafka.annotation.Topic

@KafkaListener(groupId = "hll-tool")
class SlotChangeEventConsumer(private val processor: SlotStatsProcessor) {

    @Topic(SlotChangeEventProducer.TOPIC)
    fun handleMapChange(@KafkaKey serverName: String, event: SlotsChangeEvent) {
        processor.slotChangeEvent(serverName, event)
    }

}
