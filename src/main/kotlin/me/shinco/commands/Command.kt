package me.shinco.commands

import net.dv8tion.jda.core.Permission
import net.dv8tion.jda.core.events.message.MessageReceivedEvent
import java.util.*

abstract class Command(val name: String,
                       val description: String,
                       val alias: Array<String> = arrayOf(name),
                       val allowPrivate: Boolean = true,
                       val authorExclusive: Boolean = false,
                       val requiredPermissions: Array<Permission> = arrayOf(),
                       val userRequiresPermissions: Boolean = true,
                       val botRequiresPermissions: Boolean = true) : EventListener {

    /* abstract class Command를 상속하는 클래스가 객체 생성될 때 Call */
    init {
        register()
    }

    abstract fun execute(args: List<String>, e: MessageReceivedEvent)

    /* Registry class가 관리하는 commands set에 Command 객체 추가 */
    private fun register() = Registry.registerCommand(this)
}