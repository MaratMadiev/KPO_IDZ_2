import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.delay
import logic.*
import logic.data.Bank
import logic.data.MenuDataBase
import logic.data.ReviewManager
import users.Admin
import users.Customer
import users.User

@OptIn(DelicateCoroutinesApi::class)
fun main(args: Array<String>) {
    val emulator = KitchenEmulator();
    emulator.emulate(4)

    val bank = Bank()

    val serializationManager = SerializationManager()

    val preLoader = PreLoader(serializationManager)
    preLoader.preLoad()


    val accountManager = AccountManager(preLoader.passwords, preLoader.users);
    val loginManager = LoginManager(accountManager)
    val reviewManager = ReviewManager(preLoader.reviews1, preLoader.reviews2)
    val menuDataBase = MenuDataBase(preLoader.menu)


    val saveManager = SaveManager(accountManager, menuDataBase, reviewManager, serializationManager, bank)

    while (true) {
        val user: User = loginManager.enterProgram()

        if (user is Admin) {

            val admLogicManager = AdminLogicManager(menuDataBase, saveManager, bank, reviewManager)
            admLogicManager.logic()

        } else if (user is Customer) {

            val customerLogicManager = CustomerLogicManager(menuDataBase, saveManager, user, emulator, bank, reviewManager)
            customerLogicManager.logic()

        }
    }

}

suspend fun f(i: Int) {
    println("$i start  ")
    delay(i * 1000.toLong())
    println("$i finish ")
}

