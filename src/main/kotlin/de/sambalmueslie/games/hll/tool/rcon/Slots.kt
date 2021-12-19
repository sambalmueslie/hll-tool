package de.sambalmueslie.games.hll.tool.rcon

data class Slots(
    val active: Int = 0,
    val available: Int = 0
) {
    fun isValid(): Boolean {
        return available > 0
    }

    companion object {
        private val REGEX = "(\\d*)/(\\d*)".toRegex()
        fun parse(msg: String): Slots {
            val result = REGEX.find(msg) ?: return Slots()
            if (result.groupValues.size < 3) return Slots()

            val active = result.groupValues[1].toIntOrNull() ?: return Slots()
            val available = result.groupValues[2].toIntOrNull() ?: return Slots()
            return Slots(active, available)
        }
    }
}
