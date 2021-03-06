package me.ankur.rosalind.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sundara on 12/19/14.
 */
public class Util {

    public static List<FastaSection> readFasta(String name) {
        List<FastaSection> sections = new ArrayList<FastaSection>();
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(Util.class.getResourceAsStream("/resources/" + name + ".txt")));

            String label = null;
            StringBuilder seq = new StringBuilder();
            String line;
            while((line=in.readLine())!=null) {
                if(line.startsWith(">")) {
                    if(label!=null) {
                        sections.add(new FastaSection(label, new Sequence(seq.toString())));
                    }
                    label = line.substring(1);
                    seq = new StringBuilder();
                }
                else {
                    seq.append(line);
                }
            }
            if(label!=null) {
                sections.add(new FastaSection(label, new Sequence(seq.toString())));
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sections;
    }

    public static String readStringFile(String name) {
        StringBuilder s = new StringBuilder();
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(Util.class.getResourceAsStream("/resources/" + name + ".txt")));
            String line;
            while ((line = in.readLine()) != null) {
                s.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return s.toString();
    }

    public static FastaSection getProteinSequence(String name) {
        try {
            URL uniprot = new URL("http://www.uniprot.org/uniprot/" + name + ".fasta");
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(uniprot.openStream()));

            String label = in.readLine();
            StringBuilder seq = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null)
                seq.append(inputLine);
            in.close();
            return new FastaSection(label, new Protein(seq.toString()));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
