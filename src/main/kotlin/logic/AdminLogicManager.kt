package logic

import askInt
import askStr
import data.Dish
import data.interfaces.DishDifficulty
import logic.data.Bank
import logic.data.MenuDataBase
import logic.data.ReviewManager
import kotlin.system.exitProcess

class AdminLogicManager(private val menuDataBase: MenuDataBase,
                        private val saveManager: SaveManager,
                        private val bank: Bank,
                        private val reviewManager: ReviewManager) {



    fun logic() {
        while (true) {
            val input = askStr(
                "\nWhat you want to do:\n" +
                        "1) Add new dish to the menu\n" +
                        "2) Remove dish from menu\n" +
                        "3) Print menu\n" +
                        "4) Print money\n" +
                        "5) review logic\n" +
                        "6) Exit program\nenter"
            )
            when (input) {
                "1" -> addDishToMenu()
                "2" -> removeDishFromMenu()
                "3" -> printMenu()
                "4" -> printTotalMoney()
                "5" -> reviewLogic()
                "6" -> exitProgram()
            }
        }
    }

    private fun reviewLogic() {
        while (true) {
            val input = askStr(
                "\nWhat you want to do:\n" +
                        "1) Check dish reviews\n" +
                        "2) Check average \n" +
                        "3) Print all \n" +
                        "5) return \nenter")

            when (input) {
                "1"-> dishReview()
                "2"-> average()
                "3"-> printAll()
                "5"-> return
            }
        }
    }

    private fun printAll() {
        reviewManager.printAllReviews()
    }

    private fun average() {
        var totalCount = 0
        var totalSum = 0.0
        for (i in reviewManager.dishAverageMap.values) {
            totalSum += i
        }

        totalCount = reviewManager.dishReviewMap.values.count()

        println("$\nAverage is {totalSum / totalCount}")

    }

    private fun dishReview() {
        while (true) {
            val id = askInt("Enter ID of dish")
             val index = menuDataBase.list.indexOfFirst { it.dishId == id }
            if (index != -1 && id in reviewManager.dishReviewMap.keys) {
                println("Reviews for ${menuDataBase.list[index].dishName}")

                val list = reviewManager.dishReviewMap[id]?: listOf()

                for (i in list) {
                    println(i)
                }
                return
            }
        }
    }

    private fun printTotalMoney() {
        println("Total summ is ${bank.money}")
    }

    private fun exitProgram() {
        println("exiting...")
        saveManager.saveAll()
        exitProcess(0)
    }


    private fun printMenu() {
        menuDataBase.printMenu()
    }

    private fun removeDishFromMenu() {
        val id = askInt("Print id")
        if (menuDataBase.removeByID(id)) {
            println("Dish $id deleted successfully")
        } else {
            println("Unknown dish ID")
        }
    }

    private fun addDishToMenu() {
        val id = askUniqueId(menuDataBase.list)
        val name = askStr("Enter dish name")
        val dif = askDifficulty()
        val price = askPrice()
        val newDish = Dish(name, dif, price, id)

        menuDataBase.addDish(newDish)
    }

    private fun askPrice(): Int {
        while (true) {
            val res = askInt("Enter price:")
            if (res <= 0) {
                print("Price must be positive value.\n Try again: ")
                continue
            }

            return res
        }
    }

    private fun askDifficulty(): DishDifficulty {
        while (true) {
            val str = askStr(
                "Choose difficulty:\n" +
                        "0) - Easy\n" +
                        "1) - Medium\n" +
                        "2) - Hard\n" +
                        "enter: "
            )

            when (str) {
                "0" -> return DishDifficulty.EASY
                "1" -> return DishDifficulty.MEDIUM
                "2" -> return DishDifficulty.HARD
            }
        }
    }

    private fun askUniqueId(list: MutableList<Dish>): Int {
        while (true) {
            val id = askInt("Enter ID")

            if (list.any { it.dishId == id }) {
                println("ID $id is currently exist. Enter another")
                continue
            }

            return id
        }
    }


}