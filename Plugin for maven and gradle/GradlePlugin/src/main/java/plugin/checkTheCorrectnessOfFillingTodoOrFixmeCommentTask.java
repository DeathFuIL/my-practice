package plugin;

import org.gradle.api.DefaultTask;
import org.gradle.api.GradleException;
import org.gradle.api.tasks.TaskAction;

import java.io.*;
import java.util.*;

public class checkTheCorrectnessOfFillingTodoOrFixmeCommentTask extends DefaultTask {
    @TaskAction
    public void apply() {
        List<File> javaFiles = getFilesWithJavaExtension();
        System.out.println("Checking files:");
        javaFiles.forEach(file -> System.out.println(file.getName()));
        Map<String, String> modifiedStrings = getModifiedStrings();
        for (String key : modifiedStrings.keySet()) {
            String modifiedString = modifiedStrings.get(key);
            if (!isStringHasCorrectFillingTodoOrFixmeComment(modifiedString)) {
                throw new GradleException(String.format("File %s has incorrect comment's: \"%s\"", key, modifiedString));
            }
        }

    }

    private Map<String, String> getModifiedStrings() {
        Map<String, String> modifiedStringsByClassName = new HashMap<>();
        try {
            Process process = Runtime.getRuntime().exec("git diff");
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            String cl = "";
            String clModifiedString;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("+++")) {
                    if (line.endsWith(".java")) {
                        String[] splitLine = line.split("/");
                        cl = splitLine[splitLine.length - 1];
                    } else {
                        cl = "";
                    }
                    continue;
                }
                if (line.startsWith("+") && !cl.isEmpty()) {
                    clModifiedString = line.substring(1).strip();
                    modifiedStringsByClassName.put(cl, clModifiedString);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return modifiedStringsByClassName;
    }

    private String getBranchName() {
        String branchName = "";
        try {
            Process process = Runtime.getRuntime().exec("git branch");
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("*")) {
                    branchName = line.substring(2);
                }
            }
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }

        return branchName;
    }

    private List<File> getFilesWithJavaExtension() {
        String sourceRootDirectoryPath = "src/main/java";
        List<File> filesWithJavaExtensions = new ArrayList<>();
        File sourceRootDirectory = new File(sourceRootDirectoryPath);

        if (sourceRootDirectory.exists() && sourceRootDirectory.isDirectory()) {
            getFilesWithJavaExtensionHelper(sourceRootDirectory, filesWithJavaExtensions);
        }

        return filesWithJavaExtensions;
    }

    private void getFilesWithJavaExtensionHelper(File directory, List<File> filesWithJavaExtensions) {
        File[] filesInDirectory = directory.listFiles();
        if (filesInDirectory == null) {
            return;
        }
        for (File file : filesInDirectory) {
            if (file.isDirectory()) {
                if (file.getName().equals("plugin")) continue;
                getFilesWithJavaExtensionHelper(file, filesWithJavaExtensions);
            } else if (file.isFile() && file.getName().endsWith(".java")) {
                filesWithJavaExtensions.add(file);
            }
        }
    }

    private boolean isStringHasCorrectFillingTodoOrFixmeComment(String modifiedString) {
        String taskName = getBranchName().split("/")[1];
        if (modifiedString.contains("//") && (modifiedString.contains("TODO") || modifiedString.contains("FIXME"))) {
            if (!modifiedString.contains(taskName)) {
                return false;
            }
        }
        return true;
    }


    //Если нужно проверить целый файл на соотвествие
    private int isFileHasCorrectFillingTodoOrFixmeComment(File file) {
        int isCorrect = 0;
        int i = 1;
        String taskName = getBranchName().split("/")[1];

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.contains("//") && line.contains("TODO")) {
                    if (!line.contains(taskName)) {
                        isCorrect = i;
                        break;
                    }
                }
                i++;
            }
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }

        return isCorrect;
    }
}
