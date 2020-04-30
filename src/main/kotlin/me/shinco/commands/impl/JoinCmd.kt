package me.shinco.commands.impl

import me.shinco.commands.Command
import me.shinco.utils.ChatUtil
import net.dv8tion.jda.core.events.message.MessageReceivedEvent
import net.dv8tion.jda.core.exceptions.PermissionException

class JoinCmd : Command(name = "입장",
        description = "Bot Open Audio Connection") {

    override fun execute(args: List<String>, e: MessageReceivedEvent) {
        val connectedChanel = e.member.voiceState.channel

        if (connectedChanel == null) {
            ChatUtil(e).reply("당신은 음성 채널에 연결되어 있지 않습니다!")
            return
        }

        val audioManager = e.guild.audioManager
        if (audioManager.isAttemptingToConnect) {
            ChatUtil(e).reply("봇이 연결 중입니다.")
            return
        }

        try {
            audioManager.openAudioConnection(connectedChanel)
        }
        catch (pe: PermissionException) {
            ChatUtil(e).reply("봇에 알맞은 권한이 없습니다 !")
        }
    }
}