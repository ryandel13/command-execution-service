package net.mkengineering.studies.ces.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import net.mkengineering.studies.ces.Command;
import net.mkengineering.studies.ces.DataResponse;
import net.mkengineering.studies.ces.persistence.CommandEntity;
import net.mkengineering.studies.ces.persistence.CommandRepository;

@RestController
public class CesServiceImpl implements CesServiceInterface {

	@Autowired
	private CommandRepository commandRepo;
	
	@Override
	public ResponseEntity<DataResponse> getAllCommands(@PathVariable String vin) {
		List<CommandEntity> ce = commandRepo.getPendingCommands(vin);
		return null;
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
		return commandRepo.putCommand(vin, command);
	}

	@Override
	public ResponseEntity<Boolean> updateCommandState(@PathVariable String vin,@PathVariable String commandid, @RequestBody String status) {
		// TODO Auto-generated method stub
		return null;
	}

}
