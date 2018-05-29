package net.mkengineering.studies.ces.persistence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import net.mkengineering.studies.ces.Command;
import net.mkengineering.studies.ces.exceptions.CommandToOldException;
import net.mkengineering.studies.ces.feign.UiFeign;
import net.mkengineering.studies.cms.Message;

@Component
@ConditionalOnProperty(name = "execution.location", havingValue = "local")
@Slf4j
public class LocalCommandProcessor implements CommandRepository {

	private Map<String, Map<Long, CommandEntity>> repository = new HashMap<>();

	@Autowired
	private UiFeign uiFeign;

	@Override
	public List<CommandEntity> getAllCommands(String vin) {
		if (repository.containsKey(vin)) {
			List<CommandEntity> list = new ArrayList<>();
			list.addAll(repository.get(vin).values());
			return list;
		}
		return null;
	}

	@Override
	public List<CommandEntity> getPendingCommands(String vin) {
		if (repository.containsKey(vin)) {
			List<CommandEntity> list = new ArrayList<>();
			for (CommandEntity c : repository.get(vin).values()) {
				if (c.getState() == CommandState.INITIALIZED) {
					list.add(c);
				}
			}
			return list;
		}
		return null;
	}

	@Override
	public CommandEntity getCommandInfo(String vin, String commandid) {
		if (repository.containsKey(vin)) {
			CommandEntity needle = null;
			for (CommandEntity c : repository.get(vin).values()) {
				if (c.getId().equals(commandid)) {
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
		if (!repository.containsKey(vin)) {
			repository.put(vin, new TreeMap<>());
		}

		CommandEntity ce = CommandEntity.createFromCommand(command);
		if (ce.getTimestamp() > command.getTimestamp() + 60 * 1000) {
			throw new CommandToOldException();
		}

		repository.get(vin).put(ce.getTimestamp(), ce);
		
		return ce.getId();
	}

	@Scheduled(fixedRate = 1000)
	private void executePendingCommands() {
		Map<Long, CommandEntity> commands = repository.get("WP0ZZZ94427");
		if (commands == null)
			return;

		for (Entry<Long, CommandEntity> e : commands.entrySet()) {
			if (e.getValue().getState() == CommandState.INITIALIZED) {
				e.getValue().setState(CommandState.EXECUTING);
				e.getValue().increaseRetry();
				try {
					net.mkengineering.studies.ui.Message m = new net.mkengineering.studies.ui.Message();
					m.setType("lang.java.string");
					m.SetMessage(e.getValue().getName(), e.getValue().getId(), e.getValue().getAttributes());
					uiFeign.pushMessage(m);
					e.getValue().setState(CommandState.FINALIZED);
				} catch(Exception ex) {
					log.error(ex.getMessage());
					e.getValue().setState(CommandState.INITIALIZED);
				}
			}
		}
	}
}
