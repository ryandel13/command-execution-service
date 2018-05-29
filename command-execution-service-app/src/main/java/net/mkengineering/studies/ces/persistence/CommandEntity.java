package net.mkengineering.studies.ces.persistence;

import java.util.UUID;

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
	
	private int retries = 0;
	
	public CommandEntity() {
		id = UUID.randomUUID().toString();
	}
	
	public static CommandEntity createFromCommand(Command cmd) {
		CommandEntity ce = new CommandEntity();
		ce.setVin(cmd.getVin());
		ce.setName(cmd.getName());
		ce.setAttributes(cmd.getCommandAttribute());
		ce.setTimestamp(System.currentTimeMillis());
		ce.setState(CommandState.INITIALIZED);
		
		return ce;
	}

	
	public Command toCommand() {
		Command ce = new Command();
		ce.setVin(this.getVin());
		ce.setName(this.getName());
		ce.setCommandAttribute(this.getAttributes());
		ce.setTimestamp(this.getTimestamp());
		ce.setCommandId(this.getId());
		ce.setState(this.getState().toString());
		return ce;
	}
	
	public void increaseRetry() {
		retries++;
		if(retries > 5) {
			state = CommandState.FAILED;
		}
	}
	
	public void setState(CommandState state) {
		if(this.state == CommandState.FAILED) return;
		if(this.state == CommandState.FINALIZED) return;
		if(this.state == CommandState.TERMINATED) return;
		if(this.state == CommandState.OUTDATED) return;
		this.state = state;
	}
}
