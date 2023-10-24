package de.dhbw.parprog;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ForkJoinPool;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.apache.commons.lang.NotImplementedException;


public class DirSize {
    /**
     * Hilsfmethode: Wandelt eine Liste von Futures (desselben Typs) in ein einzelnes
     * Future einer Liste der Ergebnisse
     *
     * @param futures Liste der Futures
     * @param <T>     Gemeinsamer Typ
     * @return Future der Ergebnisliste
     */
    public static <T> CompletableFuture<List<T>> sequence(List<CompletableFuture<T>> futures) {
        CompletableFuture<Void> allDoneFuture =
                CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()]));
        return allDoneFuture.thenApply(v ->
                futures.stream().
                        map(future -> future.join()).
                        collect(Collectors.<T>toList())
        );
    }


    public CompletableFuture<DirStats> dirStats(File dir) {
        CompletableFuture<File[]> filesFuture = CompletableFuture.supplyAsync(dir::listFiles);

        CompletableFuture<DirStats> localFilesFuture = filesFuture.thenApply(files -> {
            DirStats dirStats = new DirStats();
            for (File f : files) {
                if (f.isFile()) {
                    dirStats.fileCount++;
                    dirStats.totalSize += f.length();
                }
            }
            return dirStats;
        });

        CompletableFuture<List<DirStats>> subDirsFuture = filesFuture.thenCompose(files -> {
            List<CompletableFuture<DirStats>> subDirs = new ArrayList<>();
            for (File f : files) {
                if (f.isDirectory() && !f.getName().equals(".")) {
                    subDirs.add(dirStats(f));
                }
            }
            return sequence(subDirs);
        });

        CompletableFuture<DirStats> allstats = localFilesFuture.thenCombine(subDirsFuture, (thisStats, subStatsList) -> {
            for (DirStats subDirStat : subStatsList) {
                thisStats.fileCount += subDirStat.fileCount;
                thisStats.totalSize += subDirStat.totalSize;
            }
            return thisStats;
        });
        return allstats;
    }

    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            System.out.println("Benötigter Parameter: Startverzeichnis");
            System.exit(1);
        }
        File startDir = new File(args[0]);
        if (!startDir.isDirectory()) {
            System.out.println("Dies ist kein Verzeichnis!");
            System.exit(1);
        }

        DirSize test = new DirSize();
        DirStats result = test.dirStats(startDir).get();
        System.out.println(result.fileCount + " Dateien, " + result.totalSize + " Bytes.");
    }
}
