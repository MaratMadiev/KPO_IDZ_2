package users

import kotlinx.serialization.Serializable

@Serializable
sealed class User {
    protected abstract val name: String
    protected abstract val state: UserState
    abstract fun displayUserInfo()

}