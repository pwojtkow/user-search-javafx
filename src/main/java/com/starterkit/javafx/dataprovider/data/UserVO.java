package com.starterkit.javafx.dataprovider.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserVO {

	private long id;
	private String login;
	private String name;
	private String surname;
	private String email;
	private String aboutMe;
	private String lifeMotto;
	private String password;
	
}
