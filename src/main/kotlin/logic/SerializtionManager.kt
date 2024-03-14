package logic

import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString
import kotlinx.serialization.decodeFromString
import java.io.File

class SerializationManager {
    val json = Json { prettyPrint = true }
    val currentDir: String = System.getProperty("user.dir")

    inline fun <reified T> convertToJSON(obj: T): String {
        return json.encodeToString(obj)
    }

    inline fun <reified T> saveToFile(obj: T, fileName: String) {
        try {
            val jsonStr = convertToJSON(obj)

            val filePath = File(currentDir, fileName)
            filePath.writeText(jsonStr)
        }catch (e: Exception) {
            println("Unexpected error while serialization (${e.toString()}).")
        }
    }

    inline fun <reified T> loadFromFile(fileName: String): T? {
        try {
            val filePath = File(currentDir, fileName)
            val jsonStr = filePath.readText()
            return json.decodeFromString<T>(jsonStr)
        } catch (e: Exception){
            println("Unexpected error while deserialization (${e.toString()}). Returning null.")
        }

        return null
    }
}