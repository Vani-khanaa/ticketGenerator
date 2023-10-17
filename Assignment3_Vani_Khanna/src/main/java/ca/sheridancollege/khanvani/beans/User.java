package ca.sheridancollege.khanvani.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class User {
	private long userId;
	private String userName;
	private String encryptedPassword;
	private byte enabled;

}
