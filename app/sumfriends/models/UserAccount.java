package sumfriends.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import play.db.ebean.Model;

import com.avaje.ebean.annotation.CreatedTimestamp;
import com.avaje.ebean.annotation.UpdatedTimestamp;

@Entity
public class UserAccount extends Model {
	@Id
	public long id;
	@Column(unique=true)
	public long summonerId;
	@Column(unique=true)
	public String email;
	@CreatedTimestamp
	public Date createdAt;
	@UpdatedTimestamp
	public Date updatedAt;
	public String passhash;	
	@Column(unique=true)
	public String sessionToken;

	public UserAccount(long summonerId, String email, String passhash) {
		super();
		this.summonerId = summonerId;
		this.email = email;
		this.passhash = passhash;
	}
}
