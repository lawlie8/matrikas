package com.persistent.matrikas;

import org.springframework.scheduling.annotation.Scheduled;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;


import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;

public class Scan {
    public static void main(String[] args) {
        try {
            // Dummy data
            String libraryName = "kubernetes-sigs/aws-ebs-csi-driver";
            String version = "v1.41.0";

            // Step 1: Create Dockerfile
                createDockerfile(libraryName, version);

            // Step 2: Build Docker image
            buildDockerImage(libraryName, version);

            // Step 3: Run Trivy scan
            runTrivyScan(libraryName, version);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Create Dockerfile
    private static void createDockerfile(String libraryName, String version) throws Exception {
        String dockerfileContent = """
            FROM public.ecr.aws/ebs-csi-driver/aws-ebs-csi-driver:v1.35.0
            """;

        File dockerfile = new File("Dockerfile");
        try (FileWriter writer = new FileWriter(dockerfile)) {
            writer.write(dockerfileContent);
        }
        System.out.println("Dockerfile created:\n" + dockerfileContent);
    }

    // Build Docker image
    private static void buildDockerImage(String libraryName, String version) throws Exception {
        System.out.println("Building dockerfile");
        String imageName = libraryName.replace("/", "-") + ":" + version;
        ProcessBuilder builder = new ProcessBuilder(
                "docker", "build", "-t", imageName, "."
        );
        builder.redirectErrorStream(true);
        Process process = builder.start();
        System.out.println("Building dockerfile2");
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(process.getInputStream())
        )) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        }
        System.out.println("Building dockerfile3");
        int exitCode = process.waitFor();
        if (exitCode == 0) {
            System.out.println("Docker image built: " + imageName);
        } else {
            throw new RuntimeException("Docker build failed with exit code: " + exitCode);
        }

    }

    // Run Trivy scan
    private static void runTrivyScan(String libraryName, String version) throws Exception {
        String imageName = libraryName.replace("/", "-") + ":" + version;
        System.out.println("running trivy scan");
        ProcessBuilder builder = new ProcessBuilder(
                "trivy", "image", "--format", "json", imageName
        );
        builder.redirectErrorStream(true);
        Process process = builder.start();

        StringBuilder output = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(process.getInputStream())
        )) {
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
        }

        int exitCode = process.waitFor();
        if (exitCode == 0) {
            System.out.println("Trivy scan successful. Output:\n" + output);
            parseTrivyOutput(output.toString());
        } else {
            throw new RuntimeException("Trivy scan failed with exit code: " + exitCode);
        }
        System.out.println("trivy scan completed.");
    }

    // Parse Trivy output
    private static void parseTrivyOutput(String output) {
        System.out.println("\n---- Parsing Trivy Output ----");
        if (output.contains("\"VulnerabilityID\":")) {
            String[] lines = output.split("\n");
            for (String line : lines) {
                if (line.trim().startsWith("\"VulnerabilityID\"")) {
                    System.out.println(line.trim());
                }
            }
        } else {
            System.out.println("No vulnerabilities found.");
        }
    }
}