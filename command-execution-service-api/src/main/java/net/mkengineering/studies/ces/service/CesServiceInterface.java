package net.mkengineering.studies.ces.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import net.mkengineering.studies.ces.Command;
import net.mkengineering.studies.ces.DataResponse;

public interface CesServiceInterface {

public final String CONTEXT = "command";
	
	@RequestMapping(value = CONTEXT + "/{vin}/", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<DataResponse> getAllCommands(@PathVariable("vin") String vin);
	
	@RequestMapping(value = CONTEXT + "/{vin}/pending", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<DataResponse> getPendingCommands(@PathVariable("vin") String vin);
	
	@RequestMapping(value = CONTEXT + "/{vin}/{commandid}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<DataResponse> getCommandInfo(@PathVariable("vin") String vin, @PathVariable("commandid") String commandid);
	
	@RequestMapping( value = CONTEXT + "/{vin}/{commandid}", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Boolean> updateCommandState(@PathVariable("vin") String vin, @PathVariable("commandid") String commandid, @RequestBody String status);
	
	@RequestMapping(value = CONTEXT + "/{vin}/", method = RequestMethod.PUT)
	@ResponseBody
	public String putCommand(@PathVariable("vin") String vin, @RequestBody Command command) throws Exception;
	
}
