package users

import data.Order
import kotlinx.serialization.Serializable


@Serializable
class Customer(override val name: String, ) : User() {
    override val state: UserState = UserState.CUSTOMER
        get() = field

    val orders : MutableList<Order> = mutableListOf()
        get() = field

    fun addOrder(order: Order){
        orders.add(order)
    }

    override fun displayUserInfo() {
        println("Customer ${this.name}")
    }
}