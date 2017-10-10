package net.mkengineering.studies.ces.persistence;

import java.util.List;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import net.mkengineering.studies.ces.Command;

@Component
@ConditionalOnProperty(name="repository.location", havingValue="database")
public class JpaRepository implements CommandRepository {

	@Override
	public List<CommandEntity> getAllCommands(String vin) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CommandEntity> getPendingCommands(String vin) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CommandEntity getCommandInfo(String vin, String commandid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String putCommand(String vin, Command command) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
