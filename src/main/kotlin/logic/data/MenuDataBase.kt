package logic.data

import data.Dish

class MenuDataBase(val list: MutableList<Dish>) {
    fun addDish(dish: Dish){
        list.add(dish)
    }

    fun removeByID(id: Int): Boolean {
        val index = list.indexOfFirst {
            it.dishId == id
        }

        if (index >= 0) {
            list.removeAt(index)
            return true
        }
        return false
    }

    fun printMenu() {
        println("Here is the menu:")
        for (dish in list) {
            println(dish.toString())
        }
        println()
    }
}