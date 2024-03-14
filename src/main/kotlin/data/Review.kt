package data

import data.interfaces.IReview
import data.interfaces.RateState
import kotlinx.serialization.Serializable

@Serializable
class Review : IReview {
    var rating: RateState = RateState.VERY_BAD
        get() = field  // getter для свойства оценки

    var reviewText: String = ""
        get() = field  // getter для свойства текста оценки

    constructor(rating: RateState, reviewText: String) {
        this.rating = rating
        this.reviewText = reviewText
    }

    override fun toString(): String {
        return "Rate: $rating | Text: $reviewText"
    }
}