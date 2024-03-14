package logic

import data.Dish
import data.Review
import logic.data.Bank
import users.User

class PreLoader(private val serializationManager: SerializationManager) {
    var bank: Bank = Bank()
        get() = field

    var passwords: MutableMap<String, String> = mutableMapOf()
        get() = field

    var users: MutableMap<String, User> = mutableMapOf()
        get() = field

    var menu: MutableList<Dish> = mutableListOf()
        get() = field

    var reviews1: MutableMap<Int, MutableList<Review>> = mutableMapOf()
        get() = field
    var reviews2: MutableMap<Int, Double> = mutableMapOf()
        get() = field

    private val passwordsPath = "data/accounts/passwords.json"
    private val usersPath = "data/accounts/users.json"
    private val menuPath = "data/menu/dishes.json"
    private val reviewsPath = "data/reviews/reviews.json"
    private val reviewsPath2 = "data/reviews/reviews2.json"
    private val bankPath = "data/bank/bank.json"


    fun preLoad() {
        loadUserData()
        loadMenuData()
        loadReviewData()
        loadBank()
        println("Data loading ended! \n")
    }

    private fun loadBank() {
        println("loading bank data...")
        val res = serializationManager.loadFromFile<Bank>(bankPath)
        if (res != null) {
            bank = res
        } else println("error while loading bank data.")
    }

    private fun loadReviewData() {
        println("loading review data...")
        val res = serializationManager.loadFromFile<MutableMap<Int, MutableList<Review>>>(reviewsPath)
        val res2 = serializationManager.loadFromFile<MutableMap<Int, Double>>(reviewsPath2)
        if (res != null) {
            reviews1 = res
            reviews2 = res2?: mutableMapOf()
        } else println("error while loading review data.")
    }

    private fun loadMenuData() {
        println("loading menu data...")
        val res = serializationManager.loadFromFile<MutableList<Dish>>(menuPath)
        if (res != null) {
            menu = res
        } else println("error while loading menu data.")
    }

    private fun loadUserData() {
        println("loading user data...")
        val res1 = serializationManager.loadFromFile<MutableMap<String, String>>(passwordsPath)
        val res2 = serializationManager.loadFromFile<MutableMap<String, User>>(usersPath)
        if (res1 != null && res2 != null) {
            passwords = res1
            users = res2
        } else println("error while loading user data.")
    }
}