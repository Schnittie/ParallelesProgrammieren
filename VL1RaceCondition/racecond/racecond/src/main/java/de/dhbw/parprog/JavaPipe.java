package de.dhbw.parprog;

import de.dhbw.parprog.processemu.Pipe;
import de.dhbw.parprog.processemu.ProcessEmu;
import org.apache.commons.lang.NotImplementedException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class JavaPipe {
    /* Hinweis: BufferedReader besitzt eine readLine-Methode.
       Von InputStream zu Buffered Reader:
       BufferedReader in = new BufferedReader(new InputStreamReader(inputstream));
     */

    public static void main(String[] args) throws IOException {
        // Skizze: Pipe erzeugen
        // Skizze: "Prozess" erzeugen
        int sum = 0;
        ArrayList<Pipe> pipes = new ArrayList<>(50);
        for (int i = 0; i < 50; i++) {
            Pipe pipe = new Pipe();
            ProcessEmu.fork(pipe, new CalcTask());
            pipes.add(pipe);
        }
        for (Pipe pipe : pipes) {
            BufferedReader in = new BufferedReader(new InputStreamReader(pipe.getIn()));
            int out = Integer.parseInt(in.readLine());
            System.out.println(out);
            sum += out;
        }
        System.out.println(sum);

    }
}
