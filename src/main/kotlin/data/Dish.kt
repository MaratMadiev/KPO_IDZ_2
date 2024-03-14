package data

import data.interfaces.DishDifficulty
import formatStateName
import kotlinx.serialization.Serializable

@Serializable
sealed interface IDish {

    // Метод для отображения информации о блюде
    fun displayDishInfo()
}


@Serializable
class Dish(private val name: String, private val difficulty: DishDifficulty, private var price: Int, private val id: Int) : IDish {

    val dishId: Int
        get() = id

    val dishName: String
        get() = name

    val dishDifficultyOrdinal: Int
        get() = difficulty.ordinal

    val dishDifficulty: DishDifficulty
        get() = difficulty

    var dishPrice: Int
        get() = price
        set(value) {
            if (value > 0){
                price = value
            }
        }

    override fun displayDishInfo() {
        println("$name - $difficulty")
    }

    override fun toString(): String {
        return "id: $id | name: $name | price: $price | difficulty: ${formatStateName(difficulty.name)}"
    }
}

