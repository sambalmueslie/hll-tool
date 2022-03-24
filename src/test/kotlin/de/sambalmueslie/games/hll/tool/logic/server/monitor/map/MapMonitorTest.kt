package de.sambalmueslie.games.hll.tool.logic.server.monitor.map

import de.sambalmueslie.games.hll.tool.logic.server.db.ServerConnectionData
import de.sambalmueslie.games.hll.tool.logic.server.db.ServerConnectionRepository
import de.sambalmueslie.games.hll.tool.logic.server.db.ServerData
import de.sambalmueslie.games.hll.tool.logic.server.db.ServerRepository
import de.sambalmueslie.games.hll.tool.logic.server.monitor.ServerClient
import de.sambalmueslie.games.hll.tool.logic.server.monitor.db.ServerMonitorSettingsData
import de.sambalmueslie.games.hll.tool.logic.server.monitor.map.db.ServerMapRepository
import de.sambalmueslie.games.hll.tool.logic.server.monitor.map.db.ServerMapStatsEntryRepository
import de.sambalmueslie.games.hll.tool.rcon.HellLetLooseServerClient
import de.sambalmueslie.games.hll.tool.rcon.api.HellLetLooseClient
import de.sambalmueslie.games.hll.tool.rcon.api.HellLetLooseClientFactory
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.concurrent.Future

@MicronautTest
internal class MapMonitorTest(
    private val monitor: MapMonitor,
    private val serverRepository: ServerRepository,
    private val serverConnectionRepository: ServerConnectionRepository,
    private val mapRepo: ServerMapRepository,
    private val mapStatsRepo: ServerMapStatsEntryRepository,
) {


    @Test
    fun `run some cycles with map change`() {
        val server = serverRepository.save(ServerData(0, "Test", "Test Description"))
        val connection = serverConnectionRepository.save(ServerConnectionData(server.id, "127.0.0.1", 9876, "secret"))
        val settings = ServerMonitorSettingsData(server.id, mapTrackingEnabled = true)
        val factory = mockk<HellLetLooseClientFactory>()
        val hllClient = mockk<HellLetLooseClient>()
        val future = mockk<Future<Boolean>>()
        every { future.get() } returns true
        every { hllClient.connect() } returns future
        val serverName = "Test Server Name"
        every { hllClient.getServerName() } returns serverName
        every { hllClient.getMapsInRotation() } returns HellLetLooseServerClient.maps.toList()
        every { factory.create(any()) } returns hllClient

        val client = ServerClient(server, connection, settings, factory)

        verify { future.get() }
        verify { hllClient.connect() }
        verify { hllClient.getServerName() }
        verify { hllClient.getMapsInRotation() }
        confirmVerified(future)
        confirmVerified(hllClient)

        val firstMap = HellLetLooseServerClient.maps.first()
        every { client.getMap() } returns firstMap

        monitor.runCycle(client)
        confirmVerified(hllClient)
        monitor.runCycle(client)
        confirmVerified(hllClient)
        monitor.runCycle(client)
        confirmVerified(hllClient)
        monitor.runCycle(client)
        confirmVerified(hllClient)
        monitor.runCycle(client)
        verify { client.getMap() }
        confirmVerified(hllClient)

        var data = mapRepo.findAll()
        assertEquals(1, data.count())
        val d1 = data.first()
        assertEquals(firstMap, d1.name)
        assertEquals(server.id, d1.serverId)
        assertEquals(0, mapStatsRepo.findAll().count())

        // map change
        val secondMap = HellLetLooseServerClient.maps.last()
        every { client.getMap() } returns secondMap

        monitor.runCycle(client)
        confirmVerified(hllClient)
        monitor.runCycle(client)
        confirmVerified(hllClient)
        monitor.runCycle(client)
        confirmVerified(hllClient)
        monitor.runCycle(client)
        confirmVerified(hllClient)
        monitor.runCycle(client)
        verify { client.getMap() }
        confirmVerified(hllClient)

        data = mapRepo.findAll()
        assertEquals(2, data.count())
        val d2 = data.last()
        assertEquals(secondMap, d2.name)
        assertEquals(server.id, d2.serverId)

        val stats = mapStatsRepo.findAll()
        assertEquals(1, stats.count())
        val s1 = stats.last()
        assertEquals(d2.id, s1.mapId)
        assertEquals(server.id, s1.serverId)

    }
}
