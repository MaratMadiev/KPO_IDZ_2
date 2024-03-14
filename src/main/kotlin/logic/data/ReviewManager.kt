package logic.data


import data.Review

class ReviewManager(
    val dishReviewMap: MutableMap<Int, MutableList<Review>> = mutableMapOf(),

    val dishAverageMap: MutableMap<Int, Double> = mutableMapOf())
    {

    fun addReview(dishId: Int, review: Review) {
        if (dishId in dishReviewMap.keys) {
            val nowAver = dishAverageMap[dishId]?:0.0
            val count = dishReviewMap[dishId]?.count() as Int
            dishAverageMap[dishId] = (nowAver * count + review.rating.ordinal + 1) / (count + 1)
            dishReviewMap[dishId]?.add(review)


        } else {
            dishReviewMap[dishId] = mutableListOf()
            dishAverageMap[dishId] = 0.0
            dishReviewMap[dishId]?.add(review)
            dishAverageMap[dishId] = review.rating.ordinal + 1.0
        }
    }

    fun printAllReviews() {
        for (i in dishReviewMap) {
            println("Reviews for ID ${i.key}")
            for (j in i.value) {
                println(j)
            }
        }
    }
}