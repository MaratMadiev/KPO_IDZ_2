package logic.data

import kotlinx.serialization.Serializable

@Serializable
class Bank {
    var money = 0
        get() = field

    fun addMoney(int: Int){
        if (int > 0) money += int
    }
}