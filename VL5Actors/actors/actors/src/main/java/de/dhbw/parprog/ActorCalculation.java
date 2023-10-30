package de.dhbw.parprog;

import akka.actor.ActorRef;
import akka.actor.typed.ActorSystem;
import akka.actor.typed.SupervisorStrategy;
import akka.actor.typed.javadsl.AskPattern;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Routers;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.CompletionStage;
import java.util.stream.IntStream;


public class ActorCalculation {
    public int doCalculation() {
        final ActorSystem<CalcActor.Command> system = ActorSystem.create(Routers.pool(5, Behaviors.supervise(CalcActor.create()).onFailure(SupervisorStrategy.restart())), "calc");


        List<CompletionStage<CalcActor.CalcResult>> results = IntStream.range(1, 11)
                .mapToObj(i -> AskPattern.<CalcActor.Command, CalcActor.CalcResult>ask(system,
                        (replyTo) -> new CalcActor.RequestCalculation(i, replyTo),
                        Duration.ofSeconds(15), system.scheduler()))
                .toList();

        try {
            return results.stream()
                    .mapToInt(res -> res.toCompletableFuture().join().result())
                    .sum();
        } finally {
            system.terminate();
            system.getWhenTerminated()
                    .thenAccept((done) -> System.out.println("Bye bye!"));
        }
    }

    public static void main(String[] args) {
        ActorCalculation calc = new ActorCalculation();
        System.out.println("Important calculation - with actors");
        System.out.println("The result is " + calc.doCalculation());
    }
}
