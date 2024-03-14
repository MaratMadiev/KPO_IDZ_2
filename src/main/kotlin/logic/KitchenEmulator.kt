package logic

import data.Dish
import data.Order
import data.interfaces.DishState
import kotlinx.coroutines.*
import java.util.LinkedList
import java.util.Queue

class KitchenEmulator {
    private val orderQueue: Queue<Order> = LinkedList()
    var shouldContinue: Boolean = true
        set(value) {
            field = value
        }

    fun addOrder(order: Order){
        orderQueue.add(order)
    }

    suspend fun emulateOneChef(i: Int) {
        while (shouldContinue) {
            delay(100)

            if (orderQueue.isEmpty()){

                continue
            }
            val first = orderQueue.poll() ?: continue

            for (dish in first.orderDishes) {
                val d = dish as Dish
                delay(calculateTime(d.dishDifficultyOrdinal))
            }

            first.state = DishState.READY
            // kprintln("$i finished")
        }
    }

    private fun calculateTime(dishDifficulty: Int): Long {
        val minSeconds = 10
        return (minSeconds + dishDifficulty * 5) * 1000.toLong()
    }

    @DelicateCoroutinesApi
    fun emulate(numberOfChefs: Int) {
        GlobalScope.launch {
            repeat(numberOfChefs){
                delay(3)
                launch{
                    emulateOneChef(it)
                }
            }
        }
    }
}