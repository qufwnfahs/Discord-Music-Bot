package me.shinco.utils

import net.dv8tion.jda.core.entities.Message
import net.dv8tion.jda.core.events.message.MessageReceivedEvent

class ChatUtil(val e: MessageReceivedEvent) {
    fun reply(msg: String) {
        e.channel.sendMessage(msg).queue()
    }
}