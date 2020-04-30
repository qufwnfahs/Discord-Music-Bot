package me.shinco.commands

import org.reflections.Reflections

class Registry {
    /* Reflections을 이용한 패키지 탐색
       Command class type을 모두 읽어들여
       각 객체 생성
     */
    fun loadCommands() {
        Reflections("me.shinco.commands.impl")
                .getSubTypesOf(Command::class.java)
                .forEach { it.newInstance() }
    }

    companion object {
        private val commands = mutableSetOf<Command>()

        fun registerCommand(cmd: Command): Boolean = commands.add(cmd)

        fun getCommandByName(name: String): Command?
                = commands.find { name in it.alias }
    }
}