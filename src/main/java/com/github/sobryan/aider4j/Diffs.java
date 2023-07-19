package com.github.sobryan.aider4j;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Diffs {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java Diffs file1 file2");
            System.exit(1);
        }

        String fileOrig = args[0];
        String fileUpdated = args[1];

        List<String> linesOrig = readFile(fileOrig);
        List<String> linesUpdated = readFile(fileUpdated);

        for (int i = 0; i < linesUpdated.size(); i++) {
            List<String> partialLinesUpdated = linesUpdated.subList(0, i);
            String res = diffPartialUpdate(linesOrig, partialLinesUpdated);
            System.out.println(res);
            System.console().readLine();
        }
    }

    public static List<String> readFile(String filename) {
        List<String> lines = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(filename))) {
            while (scanner.hasNextLine()) {
                lines.add(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error: File not found: " + filename);
            System.exit(1);
        }
        return lines;
    }

    public static String createProgressBar(int percentage) {
        String block = "█";
        String empty = "░";
        int totalBlocks = 30;
        int filledBlocks = (int) (totalBlocks * percentage / 100.0);
        int emptyBlocks = totalBlocks - filledBlocks;
        String bar = block.repeat(filledBlocks) + empty.repeat(emptyBlocks);
        return bar;
    }

    public static void assertNewlines(List<String> lines) {
        if (lines.isEmpty()) {
            return;
        }
        for (String line : lines.subList(0, lines.size() - 1)) {
            if (line.isEmpty() || line.charAt(line.length() - 1) != '\n') {
                throw new AssertionError(line);
            }
        }
    }

    public static String diffPartialUpdate(List<String> linesOrig, List<String> linesUpdated) {
        assertNewlines(linesOrig);
        assertNewlines(linesUpdated);

        int numOrigLines = linesOrig.size();
        int numUpdatedLines = linesUpdated.size();

        int i = 0;
        while (i < numOrigLines && i < numUpdatedLines && linesOrig.get(i).equals(linesUpdated.get(i))) {
            i++;
        }

        int j = 1;
        while (j <= numOrigLines - i && j <= numUpdatedLines - i && linesOrig.get(numOrigLines - j).equals(linesUpdated.get(numUpdatedLines - j))) {
            j++;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("".repeat(80)).append("\n");
        sb.append("".repeat(30)).append(" DIFF ").append("".repeat(30)).append("\n");
        sb.append("".repeat(80)).append("\n");

        for (int k = i; k < numOrigLines - j + 1; k++) {
            sb.append("- ").append(linesOrig.get(k));
        }

        sb.append(createProgressBar(100 * i / numOrigLines)).append("\n");

        for (int k = i; k < numUpdatedLines - j + 1; k++) {
            sb.append("+ ").append(linesUpdated.get(k));
        }

        sb.append(createProgressBar(100 * (numUpdatedLines - j + 1) / numOrigLines)).append("\n");

        return sb.toString();
    }
}