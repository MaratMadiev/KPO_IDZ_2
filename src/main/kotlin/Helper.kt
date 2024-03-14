import java.util.*

fun askStr(string: String) : String {
    print("$string: ")
    return readlnOrNull() ?: ""
}

fun askBool(string: String) : Boolean {
    print("$string [Y/N]: ")
    while (true){
        val ans = readln()
        if (ans.lowercase() == "y") return true
        if (ans.lowercase() == "n") return false
    }
}

fun askInt(string: String) : Int {
    print("$string: ")
    while (true) {
        val str = readlnOrNull() ?: ""
        val int = str.toIntOrNull()
        if (int != null) return int
        println("Incorrect data. Try again.")
        print("Enter int: ")
    }

}

fun formatStateName(enumString: String): String {
    return enumString.lowercase()
        .split('_')
        .joinToString(" ") { it.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
        }
}