package net.mkengineering.studies.ces.persistence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import net.mkengineering.studies.ces.Command;
import net.mkengineering.studies.ces.exceptions.CommandToOldException;
import net.mkengineering.studies.ces.feign.CommunicationServiceFeign;
import net.mkengineering.studies.cms.Message;

@Component
@ConditionalOnProperty(name="repository.location", havingValue="memory")
public class MockRepository implements CommandRepository {

	@Autowired
	CommunicationServiceFeign cmsInterface;
	
	Map<String, Map<Long, CommandEntity>> repository = new HashMap<>();
	
	@Override
	public List<CommandEntity> getAllCommands(String vin) {
		if(repository.containsKey(vin)) {
			List<CommandEntity> list = new ArrayList<>();
			list.addAll(repository.get(vin).values());
			return list;
		}
		return null;
	}

	@Override
	public List<CommandEntity> getPendingCommands(String vin) {
		if(repository.containsKey(vin)) {
			List<CommandEntity> list = new ArrayList<>();
			for(CommandEntity c : repository.get(vin).values()) {
				if(c.getState() == CommandState.INITIALIZED) {
					list.add(c);
				}
			}
			return list;
		}
		return null;
	}

	@Override
	public CommandEntity getCommandInfo(String vin, String commandid) {
		if(repository.containsKey(vin)) {
			CommandEntity needle = null;
			for(CommandEntity c : repository.get(vin).values()) {
				if(c.getId().equals(commandid)) {
					needle = c;
					break;
				}
			}
			return needle;
		}
		return null;
	}

	@Override
	public String putCommand(String vin, Command command) throws Exception {
		if(!repository.containsKey(vin)) {
			repository.put(vin, new TreeMap<>());
		}
		
		CommandEntity ce = CommandEntity.createFromCommand(command);
		if(ce.getTimestamp() > command.getTimestamp() + 60 * 1000) {
			throw new CommandToOldException();
		}
		
		repository.get(vin).put(ce.getTimestamp(), ce);
		
		Message msg = new Message();
		msg.setVin(vin);
		msg.setTimestamp(ce.getTimestamp());
		msg.setContent(command.getName() + "|" + command.getUser() + "|" + command.getCommandId());
		
		cmsInterface.sendCommand(vin, msg);
		
		return ce.getId();
	}

}
