package de.dhbw.parprog;

import de.dhbw.parprog.processemu.Pipe;
import de.dhbw.parprog.processemu.ProcessWithPipe;
import org.apache.commons.lang.NotImplementedException;

import java.io.*;


public class CalcTask implements ProcessWithPipe {
    // PrintStream besitzt eine println-Methode
    private static PrintStream printStreamForPipe(Pipe pipe) {
        return new PrintStream(pipe.getOut());
    }

    // BufferedReader besitzt eine readLine-Methode
    private static BufferedReader readerForPipe(Pipe pipe) {
        return new BufferedReader(new InputStreamReader(pipe.getIn()));
    }
    
    @Override
    public void main(final Pipe pipe) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        printStreamForPipe(pipe).println(42);
    }
}
