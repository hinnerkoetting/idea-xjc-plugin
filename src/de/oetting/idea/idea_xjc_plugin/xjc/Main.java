package de.oetting.idea.idea_xjc_plugin.xjc;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by hinni on 17.04.16.
 */
public class Main {

    public static void main(String[] args ) throws Exception {
//        xjc, -d /home/hinni/develop/plilpa/foo, -p com.example.myschema, -npa, /home/hinni/develop/plilpa/src /foo.xsd, -b /home/hinni/develop/plilpa/src/foo.xj
        Process exec = Runtime.getRuntime().exec(new String[] {"xjc", "-d" ,"/home/hinni/develop/plilpa/foo" ,"-p", "com.example.myschema", "-npa", "/home/hinni/develop/plilpa/src/foo.xsd", "-b", "/home/hinni/develop/plilpa/src/foo.xjb" });
        logProgramOutput(exec);
        int exitValue = exec.waitFor();
        if (exitValue != 0)
            throw new RuntimeException("xjc failed. RC is " + exitValue);
    }


    private static void logProgramOutput(Process exec) throws IOException {
        String line;
        BufferedReader in = new BufferedReader(
                new InputStreamReader(exec.getInputStream()) );
        while ((line = in.readLine()) != null)
            System.out.println(line);
        in.close();
    }

}
