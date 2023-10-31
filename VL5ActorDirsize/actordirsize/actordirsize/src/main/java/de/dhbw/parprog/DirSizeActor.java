package de.dhbw.parprog;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.*;
import lombok.Value;

import java.io.File;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class DirSizeActor extends AbstractBehavior<DirSizeActor.DirSizeCommand , DirSizeActor.Answer> {

    akka.actor.ActorRef actorRef;
    public DirSizeActor(ActorContext<DirSizeCommand> context) {
        super(context);
    }


    public static Behavior<DirSizeCommand> create() {
        return Behaviors.setup(DirSizeActor::new);
    }

    public void doDirsizing(File dir) {
        CompletableFuture<File[]> filesFuture = CompletableFuture.supplyAsync(dir::listFiles);

        CompletableFuture<DirStats> localFilesFuture = filesFuture.thenApply(files -> {
            for (File f : files) {
                if (f.isFile()) {
                    CompletionStage<DirSizeActor.Answer> answer = AskPattern.ask(DirSizeActor.Answer.class,actorRef, Duration.ofSeconds(10),
                            (replyTo) -> new DirSizeActor.FileCommand(f,replyTo));
//
                }
            }
        });

        CompletableFuture<List<DirStats>> subDirsFuture = filesFuture.thenCompose(files -> {
            for (File f : files) {
                if (f.isDirectory() && !f.getName().equals(".")) {
                    ActorRef<DirSizeCommand> dirSizeActor = getContext().spawn(DirSizeActor.create(), "name");
                    dirSizeActor.tell(new DirCommand(f,getContext().getSelf()));
                }
            }
        });

//        CompletableFuture<DirStats> allstats = localFilesFuture.thenCombine(subDirsFuture, (thisStats, subStatsList) -> {
//            for (DirStats subDirStat : subStatsList) {
//                thisStats.fileCount += subDirStat.fileCount;
//                thisStats.totalSize += subDirStat.totalSize;
//            }
//            return thisStats;
//        });
//        return allstats;
    }

    public DirStats doFilesizing(File file) {
        if (!file.isFile()) throw new RuntimeException("Du hast die logik nicht fertig gebaut");
        return new DirStats(1, file.length());
    }


    public interface DirSizeCommand {
    }

    @Value
    public class DirCommand implements DirSizeCommand {
        File file;
        ActorRef sender;
    }

    @Value
    public class FileCommand implements DirSizeCommand {
        File file;
        ActorRef sender;
    }

    @Override
    public Receive<DirSizeCommand> createReceive() {
        return null;
    }

    @Value
    public class Answer {
        DirStats dirStats;
    }
}
