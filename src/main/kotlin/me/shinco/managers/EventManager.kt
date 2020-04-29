package me.shinco.managers

import net.dv8tion.jda.core.events.ReadyEvent
import net.dv8tion.jda.core.events.message.MessageReceivedEvent
import net.dv8tion.jda.core.hooks.ListenerAdapter

/* todo: EventManager 구현
         입장 시 일정 duration으로 connect, disconnect Bug Fix 필요
 */
class EventManager : ListenerAdapter() {
    override fun onReady(event: ReadyEvent) {
        val selfUser = event.jda.selfUser
        println("Account Info: ${selfUser.name}#${selfUser.discriminator} (ID: ${selfUser.id})\n" +
                "Connected to ${event.jda.guilds.size} guilds, ${event.jda.textChannels.size} text channels")
    }

    override fun onMessageReceived(event: MessageReceivedEvent) {
        val content = event.message.rawContent  // 메세지 Raw 내용
        val selfId = event.jda.selfUser.id      // bot Id

        val author = event.author.name          // 메세지 작성자 (author)
        val channel = event.channel

        println("Content : $content, selfId : $selfId, author name : $author")

        if (content == "!입장") {
            val connectedChannel = event.member.voiceState.channel

            if (connectedChannel == null) {
                channel.sendMessage("당신은 현재 음성 채널에 연결되어 있지 않습니다!")
                return
            }

            val audioManager = event.guild.audioManager
            if (audioManager.isAttemptingToConnect) {
                channel.sendMessage("봇이 현재 연결하려는 중입니다. 음성 채널에 들어가 계세요.").queue()
                return
            }

            audioManager.openAudioConnection(connectedChannel)
            channel.sendMessage("봇이 연결되었습니다!").queue()
        }
        else if (content == "!퇴장") {
            val connectedChannel = event.guild.selfMember.voiceState.channel
            if (connectedChannel == null) {
                channel.sendMessage("이미 연결되어 있지 않습니다!").queue()
                return
            }

            event.guild.audioManager.closeAudioConnection()
            channel.sendMessage("봇의 연결이 끊어졌습니다!").queue()
        }
    }
}