package de.dhbw.parprog;

import akka.actor.testkit.typed.javadsl.ActorTestKit;
import akka.actor.testkit.typed.javadsl.TestProbe;
import akka.actor.typed.ActorRef;
import org.junit.AfterClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.fest.assertions.Assertions.assertThat;


//@RunWith(JUnit4.class)
//public class CalcTest {
//    static final ActorTestKit testKit = ActorTestKit.create();
//
//    @AfterClass
//    public static void teardown() {
//        testKit.shutdownTestKit();
//    }
//
//    @Test
//    public void calculationReturnsCorrectResult() {
//        ActorCalculation calc = new ActorCalculation();
//        assertThat(calc.doCalculation()).isEqualTo(2310);
//    }
//
//    @Test
//    public void actorReturnsCorrectResult() {
//        ActorRef<CalcActor.Command> calc = testKit.spawn(CalcActor.create(), "calc");
//        TestProbe<CalcActor.???> probe = testKit.createTestProbe();
//        calc.tell(new ???(1, probe.ref()));
//        CalcActor.??? reply = probe.receiveMessage();
//        testKit.stop(calc);
//        assertThat(reply.???).isEqualTo(42);
//    }
//}
