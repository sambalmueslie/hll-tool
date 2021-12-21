package de.sambalmueslie.games.hll.tool.monitor.slot.kafka

import de.sambalmueslie.games.hll.tool.monitor.slot.api.SlotsChangeEvent
import io.micronaut.configuration.kafka.annotation.KafkaClient
import io.micronaut.configuration.kafka.annotation.KafkaKey
import io.micronaut.configuration.kafka.annotation.Topic

@KafkaClient()
interface SlotChangeEventProducer {
    @Topic(TOPIC)
    fun sendEvent(@KafkaKey key: String, event: SlotsChangeEvent)

    companion object {
        const val TOPIC = "hll.evt.slots"
    }
}
