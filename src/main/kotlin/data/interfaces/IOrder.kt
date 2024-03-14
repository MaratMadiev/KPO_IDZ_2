package data.interfaces

import data.IDish

interface IOrder {

    fun addDish(dish: IDish)

    fun removeDish(dish: IDish)

    fun displayOrderInfo()
}