package sumbet.models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import play.db.ebean.Model;

@Entity
public class Bet extends Model {
	@Id
    @GeneratedValue
    public long id;
	
	@Enumerated(EnumType.STRING)
	public BetType type;
	
	public Date createdAt;
	
	public Date deadline;
	
	public long targetSummonerId;
}
