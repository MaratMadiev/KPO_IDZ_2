package logic

import org.mindrot.jbcrypt.BCrypt
import users.Admin
import users.Customer
import users.User
import users.UserState

class AccountManager(
    private val passwordMap: MutableMap<String, String>,
    private val users: MutableMap<String, User>
) {

    val userMap: MutableMap<String, User>
        get() {
            return users
        }

    val pasMap: MutableMap<String, String>
        get() {
            return passwordMap
        }

    fun loginAccount(login: String, pas: String) : User? {
        if (login !in passwordMap){
            return null
        }

        if (checkPasswords(passwordMap[login] as String, pas)) {
            return users[login]
        }
        return null
    }

    private fun checkPasswords(pas1: String, pas2: String): Boolean {
        return BCrypt.checkpw(pas2, pas1)
    }


    fun createAccount(pas : String, login: String, isAdmin: Boolean) : Boolean {
        if (login in passwordMap){
            return false
        }

        val pasHashed = cryptPassword(pas)
        passwordMap[login] = pasHashed


        if (isAdmin) users[login] = Admin(login)
        else users[login] = Customer(login)

        return true
    }

    private fun cryptPassword(pas: String): String {
        val salt: String = BCrypt.gensalt()
        return BCrypt.hashpw(pas, salt)
    }
}