package sumbet.models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import play.db.ebean.Model;

@Entity
public class UserAccount extends Model {
	@Id
	public long id;
	public long summonerId;
	public Date createdAt;
	public Date updatedAt;
	public String email;
	public String passhash;

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
