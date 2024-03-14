package users

import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer


@Serializable
class Admin(override val name: String) : User() {
    override val state: UserState = UserState.ADMIN
        get() = field


    override fun displayUserInfo() {
        println("Administrator ${this.name}")
    }
}