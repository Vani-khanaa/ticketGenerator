package ca.sheridancollege.khanvani.repositories;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import ca.sheridancollege.khanvani.beans.Ticket;
import ca.sheridancollege.khanvani.beans.User;
import lombok.AllArgsConstructor;

@Repository
@AllArgsConstructor
public class TicketRepository {
	private NamedParameterJdbcTemplate jdbc;

	public ArrayList<Ticket> getTickets() {

		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "Select * from tickets";
		ArrayList<Ticket> tickets = (ArrayList<Ticket>) jdbc.query(query, parameters,
				new BeanPropertyRowMapper<Ticket>(Ticket.class));
		return tickets;

	}

	public void addTicket(Ticket ticket) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();

		String query = "INSERT INTO tickets" + "(name, price, event, gate, tt, eventCity, eventDescription)" + "VALUES"
				+ "(:na, :p, :e, :g, :tt, :ec, :des)";

		parameters.addValue("na", ticket.getName());
		parameters.addValue("p", ticket.getPrice());
		parameters.addValue("e", ticket.getEvent());
		parameters.addValue("g", ticket.getGate());
		parameters.addValue("tt", ticket.getTt());
		parameters.addValue("ec", ticket.getEventCity());
		parameters.addValue("des", ticket.getEventDescription());
		jdbc.update(query, parameters);

	}

	public Ticket getTicketById(int id) {

		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "Select * from tickets WHERE id=:woof";
		parameters.addValue("woof", id);
		ArrayList<Ticket> tickets = (ArrayList<Ticket>) jdbc.query(query, parameters,
				new BeanPropertyRowMapper<Ticket>(Ticket.class)); // this statement converts table in to the arraylist
																	// of drinks
		if (tickets.size() > 0) {
			return tickets.get(0);
		} else {
			return null;
		}

	}

	public void editTicket(Ticket ticket) {

		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "UPDATE tickets SET name=:na, price=:p, "
				+ "event=:e, gate=:g, tt=:tt , eventCity=:ec , eventDescription=:des " + "WHERE id=:woof";

		parameters.addValue("na", ticket.getName());
		parameters.addValue("p", ticket.getPrice());
		parameters.addValue("e", ticket.getEvent());
		parameters.addValue("g", ticket.getGate());
		parameters.addValue("tt", ticket.getTt());
		parameters.addValue("ec", ticket.getEventCity());
		parameters.addValue("des", ticket.getEventDescription());
		parameters.addValue("woof", ticket.getId());

		jdbc.update(query, parameters);

	}

	public void deleteTicket(int id) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "DELETE FROM tickets WHERE id=:woof";
		parameters.addValue("woof", id);
		jdbc.update(query, parameters);

	}

	public ArrayList<Ticket> getTicketsbyUsername(String username) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "SELECT * FROM tickets where name=:name";
		parameters.addValue("name", username);
		ArrayList<Ticket> tickets = (ArrayList<Ticket>) jdbc.query(query, parameters,
				new BeanPropertyRowMapper<Ticket>(Ticket.class)); // this statement converts table in to the arraylist
																	// of drinks
		return tickets;
	}

}
