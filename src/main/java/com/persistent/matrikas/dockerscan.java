package com.persistent.matrikas;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.*;

@Component
public class dockerscan {
    public static void main(String[] args) {
//        scanImage("public.ecr.aws/ebs-csi-driver/aws-ebs-csi-driver:v1.32.0");

        }

    public Pair<List<String>, List<String>> scanImage(String image) {
        try {
            // Step 1: Create Dockerfile
            createDockerfile(image);

            // Step 2: Build Docker image
            buildDockerImage(image);

            // Step 3: Run Trivy scan
            // Step 4: Parse Trivy output
            return runTrivyScan(image);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
        // Create Dockerfile
        private static void createDockerfile(String image) throws Exception {
            String dockerfileContent = """
            FROM %s""".formatted(image);

            File dockerfile = new File("Dockerfile");
            try (FileWriter writer = new FileWriter(dockerfile)) {
                writer.write(dockerfileContent);
            }
            System.out.println("Dockerfile created:\n" + dockerfileContent);
        }

        // Build Docker image
        private static void buildDockerImage(String image) throws Exception {
            System.out.println("Building dockerfile");
            String imageName = "image_" + image;
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
        private static Pair<List<String>, List<String>> runTrivyScan(String image) throws Exception {
            String imageName =  "image_" + image;;
            System.out.println("running trivy scan");
            ProcessBuilder builder = new ProcessBuilder(
                    "trivy", "image", "--format", "json", "-q", imageName
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
             return    parseTrivyOutput(output.toString());
            } else {
                throw new RuntimeException("Trivy scan failed with exit code: " + exitCode);
            }

        }

        // Parse Trivy output

    private static Pair<List<String>, List<String>> parseTrivyOutput(String output) {
        List<String> cveList = new ArrayList<>();
        List<String> severityList = new ArrayList<>();

        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(output);

            JsonNode results = rootNode.get("Results");
            if (results != null) {
                for (JsonNode result : results) {
                    JsonNode vulnerabilities = result.get("Vulnerabilities");
                    if (vulnerabilities != null) {
                        for (JsonNode vulnerability : vulnerabilities) {
                            String id = vulnerability.get("VulnerabilityID").asText();
                            String severity = vulnerability.get("Severity").asText();

                            cveList.add(id);
                            severityList.add(severity);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error parsing Trivy output");
        }

        return new Pair<>(cveList, severityList);
    }


}


