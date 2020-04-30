package me.shinco.managers

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers
import com.sedmelluq.discord.lavaplayer.track.AudioTrack
import me.shinco.commands.Registry
import net.dv8tion.jda.core.audio.hooks.ConnectionListener
import net.dv8tion.jda.core.audio.hooks.ConnectionStatus
import net.dv8tion.jda.core.entities.ChannelType
import net.dv8tion.jda.core.entities.User
import net.dv8tion.jda.core.events.DisconnectEvent
import net.dv8tion.jda.core.events.ReadyEvent
import net.dv8tion.jda.core.events.ReconnectedEvent
import net.dv8tion.jda.core.events.message.MessageReceivedEvent
import net.dv8tion.jda.core.exceptions.PermissionException
import net.dv8tion.jda.core.hooks.ListenerAdapter


/* todo: EventManager 구현
 */
class EventManager : ListenerAdapter() {
    /* Bot 실행 후 EventManager가 처음 준비완료 됬을 때 Call */
    override fun onReady(event: ReadyEvent) {
        val selfUser = event.jda.selfUser
        println("Account Info: ${selfUser.name}#${selfUser.discriminator} (ID: ${selfUser.id})\n" +
                "Connected to ${event.jda.guilds.size} guilds, ${event.jda.textChannels.size} text channels")
    }

    override fun onMessageReceived(event: MessageReceivedEvent) {
        val content = event.message.contentRaw  // 메세지 Raw 내용
        val author = event.author.name          // 메세지 작성자 (author)

        val prefix = "!"                        // 명령어 prefix : !

        println("Content : $content, author name : $author")

        if (event.isFromType(ChannelType.PRIVATE)) {
            println("PRIVATE MESSAGE IS TEMPORARY DENIED")
            return
        }

        val index = prefix.length
        val allArgs = content.substring(index).split("\\s+".toRegex())  // prefix 후로 문자열

        val command = Registry.getCommandByName(allArgs[0])
        val args = allArgs.drop(1)

        command?.execute(args, event)
    }
}