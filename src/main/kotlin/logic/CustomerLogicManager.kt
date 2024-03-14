package logic

import askBool
import askInt
import askStr
import data.Dish
import data.IDish
import data.Order
import data.Review
import data.interfaces.DishState
import data.interfaces.RateState
import logic.data.Bank
import logic.data.MenuDataBase
import logic.data.ReviewManager
import users.Customer
import kotlin.system.exitProcess

class CustomerLogicManager(private val menuDataBase: MenuDataBase,
                           private val saveManager: SaveManager,
                           private val customer: Customer,
                           private val kitchenEmulator: KitchenEmulator, private val bank: Bank,
                           private val reviewManager: ReviewManager) {
    fun logic() {
        while (true) {
            val input = askStr(
                "\nWhat you want to do:\n" +
                        "1) Print menu\n" +
                        "2) Make an order\n" +
                        "3) Print all orders\n" +
                        "4) Add dishes to order\n"+
                        "5) Accept order\n"+
                        "5) Cancel order\n"+
                        "7) Save and exit program\n enter"
            )
            when (input) {
                "1" -> printMenu()
                "2" -> makeOrder()
                "3" -> printOrders()
                "4" -> addDishesToOrder()
                "5" -> acceptOrder()
                "6" -> cancelOrder()
                "7" -> saveAndExit()
            }
        }
    }

    private fun cancelOrder() {
        val index = askInt("Enter order ID or -1 to exit")
        if (index == -1) {
            println("returning...")
            return
        }

        if (customer.orders.indexOfFirst { it.orderID == index } == -1) {
            println("No such ID")
        }

        if (customer.orders[index].state != DishState.ACCEPTED &&
            customer.orders[index].state != DishState.CANCELLED){

            customer.orders[index].state = DishState.CANCELLED
            return
        }
        println("Incorrect data")
    }

    private fun saveAndExit() {
        if (!askBool("Your unaccepted orders will be cancelled. Continue?")) return

        cancelOrders()

        println("exiting...")
        saveManager.saveAll()
        exitProcess(0)
    }

    private fun cancelOrders() {
        for (i in customer.orders) {
            if (i.state != DishState.ACCEPTED) {
                i.state = DishState.CANCELLED
            }
        }
    }

    private fun acceptOrder() {
        val index = askInt("Enter order ID or -1 to exit")
        if (index == -1) {
            println("returning...")
            return
        }

        if (customer.orders.indexOfFirst { it.orderID == index } != -1 && customer.orders[index].state == DishState.READY){
            customer.orders[index].state = DishState.ACCEPTED
            pay(customer.orders[index])
            println("ID $index accepted")

            askReview()
            return
        }
        println("Incorrect data")
    }

    private fun askReview() {
        if (!askBool("Do you want to give a review?")) return

        println("Please, give review about dishes.")
        while (true) {
            val id = askInt("Enter ID of dish enter ")
            val index = menuDataBase.list.indexOfFirst { it.dishId == id }
            if (index == -1) {
                println("unknown ID")
                continue
            }

            val rate = askRate()
            val text = askStr("Enter review text")

            val state = enumValues<RateState>().find { it.value == (rate - 1) }

            val review = Review(state?:RateState.VERY_BAD ,text)

            reviewManager.addReview(id, review)

            if (!askBool("Continue?")) return

        }
    }

    private fun askRate(): Int {
        while (true) {
            val res = askInt("Enter rate for dish [1-5]:")
            if (res < 1 || res > 5) {
                println("Number is in range from 1 to 5")
                continue
            }
            return res
        }
    }

    private fun pay(order: Order) {
        for(i in  order.orderDishes){
            bank.addMoney((i as Dish).dishPrice)
        }
    }

    private fun addDishesToOrder() {
        val index = askInt("Enter order ID or -1 to exit")
        if (index == -1) {
            println("returning...")
            return
        }

        if (customer.orders.indexOfFirst { it.orderID == index } != -1 && customer.orders[index].state == DishState.PREPARING) {
            println("You can add dishes to order.")

            val newList = mutableListOf<IDish>()
            fillList(newList)

            if (customer.orders[index].state != DishState.PREPARING) {
                println("Order is ready yet...")
                return
            }

            customer.orders[index].orderDishes.addAll(newList)
            println("successfully added")
        }
    }

    private fun printOrders() {
        println("\nHere is all orders:")
        for (i in customer.orders) {
            i.displayOrderInfo()
            println("---------")
        }
    }

    private fun makeOrder() {
        println("started making new order")
        val listOfDishes = mutableListOf<IDish>()
        fillList(listOfDishes)

        if (listOfDishes.isEmpty()) {
            println("cancelling order")
            return
        }

        val order = Order(listOfDishes, customer.orders.size)

        customer.addOrder(order)
        kitchenEmulator.addOrder(order)
    }

    private fun fillList(listOfDishes: MutableList<IDish>) {
        println("Print ID of dish to add it. If list will be empty - order will cancel")

        while (true) {
            val int = askInt("Enter ID")

            val index = menuDataBase.list.indexOfFirst { it.dishId == int }

            if (index == -1) {
                println("No such ID")
                continue
            }

            if (askBool("Do you want to add \"${menuDataBase.list[index].dishName}\" with ID $int")){
                listOfDishes.add(menuDataBase.list[index] as IDish)
            }
            if (!askBool("Continue?")) return
        }
    }

    private fun printMenu() {
        menuDataBase.printMenu()
    }
}