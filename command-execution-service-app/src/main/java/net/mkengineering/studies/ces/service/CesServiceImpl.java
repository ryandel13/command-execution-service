package net.mkengineering.studies.ces.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import net.mkengineering.studies.ces.Command;
import net.mkengineering.studies.ces.DataResponse;
import net.mkengineering.studies.ces.persistence.CommandEntity;
import net.mkengineering.studies.ces.persistence.CommandRepository;

@RestController
@Slf4j
public class CesServiceImpl implements CesServiceInterface {

	@Autowired
	private CommandRepository commandRepo;
	
	@Override
	public ResponseEntity<DataResponse> getAllCommands(@PathVariable String vin) {
		List<CommandEntity> ces = commandRepo.getAllCommands(vin);
		DataResponse dr = new DataResponse();
		List<Command> commands = new ArrayList<Command>();
		for(CommandEntity ce : ces) {
			commands.add(ce.toCommand());
		}
		dr.setValues(commands);
		return new ResponseEntity<DataResponse>(dr, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<DataResponse> getPendingCommands(@PathVariable String vin) {
		List<CommandEntity> ce = commandRepo.getPendingCommands(vin);
		return null;
	}

	@Override
	public ResponseEntity<DataResponse> getCommandInfo(@PathVariable String vin, @PathVariable String commandid) {
		CommandEntity ce = commandRepo.getCommandInfo(vin, commandid);
		return null;
	}

	@Override
	public String putCommand(@PathVariable String vin, @RequestBody Command command) throws Exception {
		log.info("New command: " + command.getName() + " for " + vin);
		return commandRepo.putCommand(vin, command);
	}

	@Override
	public ResponseEntity<Boolean> updateCommandState(@PathVariable String vin,@PathVariable String commandid, @RequestBody String status) {
		// TODO Auto-generated method stub
		return null;
	}

}
