package data.interfaces

import kotlinx.serialization.Serializable


@Serializable
enum class RateState(val value: Int) {
    VERY_BAD(0),
    BAD(1),
    NOT_SO_GOOD(2),
    GOOD(3),
    VERY_GOOD(4),
}