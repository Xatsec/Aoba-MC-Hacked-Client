/*
* Aoba Hacked Client
* Copyright (C) 2019-2023 coltonk9043
*
* This program is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

/**
 * A class to represent an exception thrown when a command is typed with invalid syntax.
 */
package net.aoba.cmd;

public class InvalidSyntaxException extends CommandException {
	private static final long serialVersionUID = 1L;
	
	public InvalidSyntaxException(Command cmd) {
		super(cmd);
	}

	@Override
	public void PrintToChat() {
		CommandManager.sendChatMessage("Invalid Usage! Usage: .aoba " + cmd.getName() + " " + cmd.getSyntax());
	}
}