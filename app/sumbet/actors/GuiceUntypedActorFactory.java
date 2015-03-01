package sumbet.actors;

import play.libs.Akka;
import sumbet.SumBetGlobal;
import akka.actor.Actor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.japi.Creator;

public class GuiceUntypedActorFactory {
	public static <A extends Actor> Creator<A> getFactory(final Class<A> clazz){
		return new GuiceCreator<A>(SumBetGlobal.getInjector(), clazz);
	}
	
	public static ActorRef createActor(Class<? extends UntypedActor> clazz){
		return Akka.system().actorOf(Props.create(GuiceUntypedActorFactory.getFactory(clazz)));
	}
}
