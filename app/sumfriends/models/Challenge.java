package sumfriends.models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import play.db.ebean.Model;
import play.libs.Json;

@Entity
public class Challenge extends Model {
	@Id
    @GeneratedValue
    public long id;
	
	@Enumerated(EnumType.STRING)
	public GoalType type;
	
	public Date createdAt;
	
	public Date deadline;
	
	public long targetSummonerId;
	
	@Override
	public String toString(){
		return Json.toJson(this).toString();
	}
}
