package ca.sheridancollege.khanvani.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Ticket {

	private Long userid;
	private int id;
	private String name;
	private double price;
	private String event;
	private int gate;
	private String tt;
	private String eventCity;
	private String eventDescription;

}
