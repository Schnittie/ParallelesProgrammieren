package de.dhbw.parprog;

import akka.actor.typed.ActorSystem;

import java.io.File;
import java.util.List;
import java.util.concurrent.CompletableFuture;


public class ActorDirSize {
	public DirStats dirStats(File dir) {
        final ActorSystem<DirSizeActor.DirSizeCommand> system = akka.actor.typed.ActorSystem.create(DirSizeActor.create(), "calc");

	}

	public static void main(String[] args) {
		if (args.length<1) {
			System.out.println("Benötigter Parameter: Startverzeichnis");
			System.exit(1);
		}
		File startDir = new File(args[0]);
		if (!startDir.isDirectory()) {
			System.out.println("Dies ist kein Verzeichnis!");
			System.exit(1);
		}
		
		ActorDirSize test = new ActorDirSize();
		DirStats result = test.dirStats(startDir);
		System.out.println(result.fileCount + " Dateien, " + result.totalSize + " Bytes.");
	}
}
