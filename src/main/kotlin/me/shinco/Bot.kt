package me.shinco

import me.shinco.managers.EventManager
import net.dv8tion.jda.core.AccountType
import net.dv8tion.jda.core.JDABuilder
import javax.security.auth.login.LoginException
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    // Discord Bot Token
    // token : args[0]
    val token = args[0]

    connect(token)
}

private fun connect(token: String) {
    // Discord Bot 연결
    // Java Discord API와 결합
    try {
        JDABuilder(AccountType.BOT)
                .addEventListener(EventManager()) // EventListener를 추가
                .setToken(token)                  // Bot의 Token 지정
                .buildBlocking()
    } catch (e: LoginException) {
        System.err.println(e.message)
        exitProcess(ExitStatus.INVALID_TOKEN.code)
    }
}

enum class ExitStatus(val code: Int) {
    // Non error
    UPDATE(10),
    SHUTDOWN(11),
    RESTART(12),
    NEW_CONFIG(13),

    // Error
    INVALID_TOKEN(20),
    CONFIG_MISSING(21),
    INSUFFICIENT_ARGS(22),

    // SQL
    SQL_ACCESS_DENIED(30),
    SQL_INVALID_PASSWORD(31),
    SQL_UNKNOWN_HOST(32),
    SQL_UNKNOWN_DATABASE(33)
}
