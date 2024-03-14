package logic

import askBool
import askStr
import users.User

class LoginManager(private val accountManager: AccountManager) {
    fun AuthoriseUser() : User? {
        while (true){
            val login: String = askStr("Enter login")
            val pass: String = askStr("Enter password")

            val user = accountManager.loginAccount(login, pass)

            if (user != null) return user
            if (!askBool("Data is incorrect. Do you want to retry?")) return null
        }
    }

    private fun createAccount(isAdmin: Boolean) {
        while (true){
            val login: String = askStr("Enter login")
            if (login.length < 1) {
                println("Please, enter correct login")
                continue
            }
            if (login in accountManager.pasMap.keys) {
                println("Account already exist")
                continue
            }


            val pass: String = askPassword()

            val success = accountManager.createAccount(pass, login, isAdmin)

            if (success) {
                if (isAdmin) println("Administrator account created")
                else println("Customer account created")
                return
            }
            if (!askBool("Data is incorrect. Do you want to retry?")) return
        }
    }

    private fun askPassword(): String {
        while (true) {
            val res = askStr("Enter password")

            if (res.length > 3) {
                return res
            }
            println("password must be longer, then 3 symbols")
        }
    }

    fun enterProgram (): User {
        var result: User? = null
        while (true) {
            val input = askStr("Welcome to cafe program. Choose your option:\n" +
                    "1) log in to account\n" +
                    "2) create customer account\n" +
                    "3) create administrator account\nEnter")

            when (input) {
                "1" -> {
                    result = AuthoriseUser()
                }
                "2" -> {
                    createAccount(false)
                }
                "3" -> createAccount(true)
            }

            if (result != null) break
        }

        return result as User
    }
}