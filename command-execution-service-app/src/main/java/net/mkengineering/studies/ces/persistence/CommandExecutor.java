package net.mkengineering.studies.ces.persistence;

import net.mkengineering.studies.ces.Command;

public class CommandExecutor {
	
	private Boolean checkCommandValidity(Command command) {
		ValidCommands vCom = ValidCommands.valueOf(command.getName().toUpperCase());
		if(vCom != null) return true;
		else return false;
	}
	
	public Command executeCommand(String vin, Command command) {
		if(checkCommandValidity(command)) {
			
		}
		return command;
	}
	
}
