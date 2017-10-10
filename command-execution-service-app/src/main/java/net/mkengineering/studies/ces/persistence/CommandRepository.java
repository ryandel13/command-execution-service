package net.mkengineering.studies.ces.persistence;

import java.util.List;

import org.springframework.http.ResponseEntity;

import net.mkengineering.studies.ces.Command;
import net.mkengineering.studies.ces.DataResponse;

public interface CommandRepository {
	
	public List<CommandEntity> getAllCommands(String vin);

	public List<CommandEntity> getPendingCommands(String vin);
	
	public CommandEntity getCommandInfo(String vin, String commandid);

	public String putCommand(String vin, Command command) throws Exception;
}
