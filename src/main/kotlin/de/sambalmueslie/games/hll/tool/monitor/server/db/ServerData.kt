package de.sambalmueslie.games.hll.tool.monitor.server.db

import javax.persistence.*

@Entity(name = "Server")
@Table(name = "server")
data class ServerData(
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    var id: Long = 0,
    @Column
    var name: String = "",
    @Column(unique = true)
    var host: String = "",
    @Column(unique = true)
    var port: Int = -1,
    @Column(unique = true)
    var password: String = ""
)
