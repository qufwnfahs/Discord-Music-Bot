package me.shinco.commands.impl

import me.shinco.commands.Command
import me.shinco.utils.ChatUtil
import net.dv8tion.jda.core.events.message.MessageReceivedEvent
import net.dv8tion.jda.core.exceptions.PermissionException

class LeaveCmd : Command(name = "퇴장",
        description = "Bot Close Audio Connection") {

    override fun execute(args: List<String>, e: MessageReceivedEvent) {
        val connectedChanel = e.guild.selfMember.voiceState.channel ?: return   // 봇이 연결된 채널이 없다면 종료 (return)

        try {
            e.guild.audioManager.closeAudioConnection()
        }
        catch (pe: PermissionException) {
            ChatUtil(e).reply("봇에 알맞은 권한이 없습니다 !")
        }
    }
}