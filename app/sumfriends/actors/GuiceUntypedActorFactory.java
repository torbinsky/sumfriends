package sumfriends.actors;

import play.libs.Akka;
import sumfriends.SumFriendsGlobal;
import akka.actor.Actor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.japi.Creator;

public class GuiceUntypedActorFactory {
	public static <A extends Actor> Creator<A> getFactory(final Class<A> clazz){
		return new GuiceCreator<A>(SumFriendsGlobal.getInjector(), clazz);
	}
	
	public static ActorRef createActorRef(Class<? extends UntypedActor> clazz, String name){
		return Akka.system().actorOf(Props.create(GuiceUntypedActorFactory.getFactory(clazz)), name);
	}
}
