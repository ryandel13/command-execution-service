package net.mkengineering.studies.ces.processors;

import java.util.Date;
import java.util.List;

import net.mkengineering.studies.ces.persistence.ValidCommands;

public interface CommandExecution {
	Boolean execute(String vin, ValidCommands cmd, Date cmdDate, List<String> attributes);
}
