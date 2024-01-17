package com.rubby.modal;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class Chat {
      @Id
      @GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private String chat_name;
	private String Chat_image;
	
	@ManyToMany
	private Set<User>admins=new HashSet<>();
	
	
	   
	  @Column(name="is_group")
	  private boolean isGroup;
	  
	  @JoinColumn(name="created_by")
	  @ManyToOne
	  private User createdBy;
	  
	  @ManyToMany
	  private Set<User>users=new HashSet<>();
	  
	  @OneToMany
	  private List<Message>messages=new ArrayList<>();
	  
	  public Chat() {
		  
	  }

	public Chat(Integer id, String chat_name, String chat_image, Set<User> admins, boolean isGroup, User createdBy,
			Set<User> users, List<Message> messages) {
		super();
		this.id = id;
		this.chat_name = chat_name;
		Chat_image = chat_image;
		this.admins = admins;
		this.isGroup = isGroup;
		this.createdBy = createdBy;
		this.users = users;
		this.messages = messages;
	}

	
	  
}
