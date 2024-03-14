package logic

import askStr
import logic.data.Bank
import logic.data.MenuDataBase
import logic.data.ReviewManager

class SaveManager (private val accountManager: AccountManager,
                   private val menuDataBase: MenuDataBase,
                   private val reviewManager: ReviewManager,
                   private val serializationManager: SerializationManager,
                   private val bank: Bank) {

    private val passwords = "data/accounts/passwords.json"
    private val users = "data/accounts/users.json"
    private val menu = "data/menu/dishes.json"
    private val reviews = "data/reviews/reviews.json"
    private val reviews2 = "data/reviews/reviews2.json"
    private val bankPath = "data/bank/bank.json"

    fun saveAll() {
        saveUserData()
        saveMenu()
        saveReviews()
        saveBank()
        println("all saved successfully.")
    }

    private fun saveBank() {
        serializationManager.saveToFile(bank, bankPath)
        println("saved bank data...")
    }

    private fun saveReviews() {
        serializationManager.saveToFile(reviewManager.dishReviewMap, reviews)
        serializationManager.saveToFile(reviewManager.dishAverageMap, reviews2)
        println("saved preview data...")
    }

    private fun saveMenu() {
        serializationManager.saveToFile(menuDataBase.list, menu)
        println("saved dishes data...")
    }

    private fun saveUserData() {
        serializationManager.saveToFile(accountManager.pasMap, passwords)
        serializationManager.saveToFile(accountManager.userMap, users)
        println("saved user data...")
    }
}