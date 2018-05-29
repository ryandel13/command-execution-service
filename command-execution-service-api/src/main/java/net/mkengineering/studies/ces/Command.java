package net.mkengineering.studies.ces;

import lombok.Data;

@Data
public class Command {

	private String name;
	
	private String commandId;
	
	private String user;
	
	private String vin;
	
	private Long timestamp;
	
	private String commandAttribute;
	
	private String state;
}
