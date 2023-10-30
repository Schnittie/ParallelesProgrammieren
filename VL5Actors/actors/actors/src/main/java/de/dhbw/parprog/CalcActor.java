package de.dhbw.parprog;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.*;
import lombok.Value;


public class CalcActor extends AbstractBehavior<CalcActor.Command> {
    public interface Command { }

    @Value
    public static class RequestCalculation implements Command {
        final int n;
        final ActorRef<CalcResult> resultRecipient;
    }
    private Behavior<Command> doCalculation(RequestCalculation req) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        CalcResult result = new CalcResult(req.n * 42);
        req.resultRecipient.tell(result);
        return Behaviors.same();
    }
    public static Behavior<Command> create() {
        return Behaviors.setup(CalcActor::new);
    }

    private CalcActor(ActorContext<Command> context) {
        super(context);
    }
    @Override
    public Receive<Command> createReceive() {
        return newReceiveBuilder()
                .onMessage(RequestCalculation.class, this::doCalculation)
                .build();
    }
    public record CalcResult(int result) {
    }
}

