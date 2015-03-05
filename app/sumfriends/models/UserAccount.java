package sumfriends.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import play.db.ebean.Model;

@Entity
public class UserAccount extends Model {
	@Id
	public long id;
	@Column(unique=true)
	public long summonerId;
	@Column(unique=true)
	public String email;
	
	@JsonIgnore
	public Date createdAt;
	@JsonIgnore
	public Date updatedAt;
	@JsonIgnore
	public String passhash;	
	@JsonIgnore
	@Column(unique=true)
	public String sessionToken;

	public UserAccount(long summonerId, String email, String passhash) {
		super();
		this.summonerId = summonerId;
		this.email = email;
		this.passhash = passhash;
	}

	@PrePersist
	void createdAt() {
		this.createdAt = new Date();
		this.updatedAt = new Date();
	}
	
	@PreUpdate
	void updatedAt() {
		this.updatedAt = new Date();
	}
}
