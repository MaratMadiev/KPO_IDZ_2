package users;

import kotlinx.serialization.Serializable


@Serializable
enum class UserState {
    ADMIN,
    CUSTOMER
}
