package data.interfaces

import kotlinx.serialization.Serializable


@Serializable
enum class DishState {
    PREPARING,
    READY,
    ACCEPTED,
    CANCELLED
}