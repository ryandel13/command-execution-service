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
		HttpStatus out = HttpStatus.NOT_FOUND;
		List<CommandEntity> ces = commandRepo.getAllCommands(vin);
		DataResponse dr = null;
		if (ces != null) {
			dr = new DataResponse();
			out = HttpStatus.OK;
			List<Command> commands = new ArrayList<Command>();
			for (CommandEntity ce : ces) {
				commands.add(ce.toCommand());
			}
			dr.setValues(commands);
		}
		return new ResponseEntity<DataResponse>(dr, out);
	}

	@Override
	public ResponseEntity<DataResponse> getPendingCommands(@PathVariable String vin) {
		HttpStatus out = HttpStatus.NOT_FOUND;
		List<CommandEntity> ces = commandRepo.getPendingCommands(vin);
		DataResponse dr = new DataResponse();
		List<Command> commands = new ArrayList<>();
		for(CommandEntity cE : ces) {
			commands.add(cE.toCommand());
		}
		dr.setValues(commands);
		out = HttpStatus.OK;
		return new ResponseEntity<DataResponse>(dr, out);
	}

	@Override
	public ResponseEntity<DataResponse> getCommandInfo(@PathVariable String vin, @PathVariable String commandid) {
		CommandEntity ce = commandRepo.getCommandInfo(vin, commandid);
		return null;
	}

	@Override
	public ResponseEntity<Boolean> putCommand(@PathVariable String vin, @RequestBody Command command) throws Exception {
		log.info("New command: " + command.getName() + " for " + vin);
		HttpStatus out = HttpStatus.BAD_REQUEST;
		if(commandRepo.putCommand(vin, command) != null) {
			out = HttpStatus.OK;
		}
		return new ResponseEntity<Boolean>(out);
	}

	@Override
	public ResponseEntity<Boolean> updateCommandState(@PathVariable String vin, @PathVariable String commandid,
			@RequestBody String status) {
		// TODO Auto-generated method stub
		return null;
	}

}
