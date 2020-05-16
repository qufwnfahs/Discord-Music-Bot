package me.shinco

import me.shinco.managers.AudioManager
import me.shinco.commands.Registry
import me.shinco.managers.EventManager
import net.dv8tion.jda.core.AccountType
import net.dv8tion.jda.core.JDABuilder
import javax.security.auth.login.LoginException
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    // Discord Bot Token
    // token : args[0]
    val token = args[0]             // Bot Token 얻기

    Registry().loadCommands()       // 명령어 활성화
    AudioManager()                  // PlayerManager 객체화
    connect(token)                  // 봇 연결
}

private fun connect(token: String) {
    // Discord Bot 연결
    // Java Discord API와 결합
    try {
        JDABuilder(AccountType.BOT)
                .setToken(token)                  // Bot의 Token 지정
                .setAudioEnabled(true)
                .addEventListener(EventManager()) // EventListener를 추가
                .setBulkDeleteSplittingEnabled(true)
                .build()
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