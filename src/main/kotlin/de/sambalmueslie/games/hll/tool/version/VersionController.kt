package de.sambalmueslie.games.hll.tool.version


import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get

@Controller("/api/version")
class VersionController {

    private val properties = VersionProperties("V1.0.0", "HLL Tool", "IEE1394")

    @Get()
    fun get() = properties
}
