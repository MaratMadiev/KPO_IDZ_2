package data

import data.interfaces.DishState
import data.interfaces.IOrder
import kotlinx.serialization.Serializable


@Serializable
class Order(private val dishes : MutableList<IDish>, private val id: Int) : IOrder {

    val orderID : Int
        get() = id

    val orderDishes : MutableList<IDish>
        get() = dishes

    var state: DishState = DishState.PREPARING
        set(value) {
            field = value
        }
        get() = field


    override fun addDish(dish: IDish) {
        dishes.add(dish as Dish)
    }

    override fun removeDish(dish: IDish) {
        dishes.remove(dish)
    }

    override fun displayOrderInfo() {
        println("\nID: $id | status: $state")
        println("Order list:")
        for (i in dishes){

            println(i.toString())
        }
    }
}