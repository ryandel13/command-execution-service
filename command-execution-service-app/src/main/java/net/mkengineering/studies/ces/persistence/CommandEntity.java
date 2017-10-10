package net.mkengineering.studies.ces.persistence;

import lombok.Data;
import net.mkengineering.studies.ces.Command;

@Data
public class CommandEntity {

	private String name;
	
	private String id; //GeneratedUUID
	
	private String vin;
	
	private String attributes;
	
	private Long timestamp;
	
	private CommandState state;
	
	public static CommandEntity createFromCommand(Command cmd) {
		CommandEntity ce = new CommandEntity();
		ce.setVin(cmd.getVin());
		ce.setName(cmd.getName());
		ce.setAttributes(cmd.getCommandAttribute());
		ce.setTimestamp(System.currentTimeMillis());
		ce.setState(CommandState.INITIALIZED);
		
		return ce;
	}

}
