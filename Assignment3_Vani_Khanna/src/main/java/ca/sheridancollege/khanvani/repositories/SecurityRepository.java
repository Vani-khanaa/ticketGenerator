package ca.sheridancollege.khanvani.repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import ca.sheridancollege.khanvani.beans.Ticket;
import ca.sheridancollege.khanvani.beans.User;
import lombok.AllArgsConstructor;

@Repository
@AllArgsConstructor

public class SecurityRepository {
	private NamedParameterJdbcTemplate jdbc;

	public ArrayList<User> getUsers() {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "SELECT * FROM users";
		ArrayList<User> users = (ArrayList<User>) jdbc.query(query, parameters,
				new BeanPropertyRowMapper<User>(User.class));
		return users;
	}

	public User getUsersByUserName(String userName) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "SELECT * FROM users where username=:name";
		parameters.addValue("name", userName);
		ArrayList<User> users = (ArrayList<User>) jdbc.query(query, parameters,
				new BeanPropertyRowMapper<User>(User.class));
		if (users.size() > 0) {
			return users.get(0);
		} else {
			return null;
		}
	}

	public List<String> getRolesByUserId(long userId) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "SELECT user_role.userId, ROLES.roleName FROM user_role, ROLES WHERE user_role.roleId=ROLES.roleId AND userId=:id";
		parameters.addValue("id", userId);

		ArrayList<String> roles = new ArrayList<String>();
		List<Map<String, Object>> rows = jdbc.queryForList(query, parameters);

		for (Map<String, Object> row : rows) {
			roles.add((String) row.get("roleName"));
		}

		return roles;

	}

	public void createUser(String username, String password) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "insert into users (username, encryptedPassword, ENABLED) " + "values (:name, :ep, 1)";
		parameters.addValue("name", username);
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		parameters.addValue("ep", encoder.encode(password));
		jdbc.update(query, parameters);
	}

	public void createRole(long userId, long roleId) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "insert into user_role (userId, roleId) " + "values (:uid, :rid)";
		parameters.addValue("uid", userId);
		parameters.addValue("rid", roleId);
		jdbc.update(query, parameters);
	}

	public ArrayList<User> getAllGuestUsers() {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "SELECT u.userId, u.userName, u.encryptedPassword " + "FROM users u "
				+ "JOIN user_role ur ON u.userId = ur.userId " + "JOIN roles r ON ur.roleId = r.roleId "
				+ "WHERE r.roleName = 'ROLE_GUEST'";

		ArrayList<User> guestUsers = (ArrayList<User>) jdbc.query(query, parameters,
				new BeanPropertyRowMapper<User>(User.class));
		return guestUsers;
	}

	public User getGuestsUserNames(String username) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "select * from users u JOIN user_role ur ON u.userId=ur.userId where u.userName=:na";
		parameters.addValue("na", username);
		ArrayList<User> guUsers = (ArrayList<User>) jdbc.query(query, parameters,
				new BeanPropertyRowMapper<User>(User.class));
		return guUsers.get(0);

	}

}
