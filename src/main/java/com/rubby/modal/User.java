package com.rubby.modal;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import lombok.Data;

@Data
@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	private String full_name;
	private String email;
	private String profile_picture;
	private String password;
	
//	@OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
//	private List<Notification>notifications=new ArrayList<>();
	
	public User() {
		
	}

	public  User(Integer id, String full_name, String email, String profile_picture, String password) {
		super();
		this.id = id;
		this.full_name = full_name;
		this.email = email;
		this.profile_picture = profile_picture;
		this.password = password;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(email, other.email) && Objects.equals(full_name, other.full_name)
				&& Objects.equals(id, other.id) && Objects.equals(password, other.password)
				&& Objects.equals(profile_picture, other.profile_picture);
	}

	@Override
	public int hashCode() {
		return Objects.hash(email, full_name, id, password, profile_picture);
	}
	
	
}
