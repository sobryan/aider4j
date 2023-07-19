package com.github.sobryan.aider4j.coders;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.table.BeanListTableModel;
import org.springframework.shell.table.BorderStyle;
import org.springframework.shell.table.Table;
import org.springframework.shell.table.TableBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.theokanning.openai.model.Model;
import com.theokanning.openai.service.OpenAiService;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;


@ShellComponent
public class BaseCoder {
	
    private ObjectMapper objectMapper;
    private OpenAiService openAiService;

    @Autowired
    public BaseCoder(ObjectMapper objectMapper, OpenAiService openAiService) {
        this.objectMapper = objectMapper;
        this.openAiService = openAiService;
    }

    private Map<String, Object> partialResponseFunctionCall;

    public void setPartialResponseFunctionCall(Map<String, Object> partialResponseFunctionCall) {
        this.partialResponseFunctionCall = partialResponseFunctionCall;
    }

    public Map<String, Object> getPartialResponseFunctionCall() {
        return partialResponseFunctionCall;
    }

    @ShellMethod("Parse partial response arguments")
    public void parsePartialArgs() {
        Map<String, Object> data = (Map<String, Object>) partialResponseFunctionCall.get("arguments");
        if (data == null) {
            return;
        }

        try {
            String jsonStr = objectMapper.writeValueAsString(data);
            Map<String, Object> parsedData = objectMapper.readValue(jsonStr, Map.class);
            partialResponseFunctionCall.put("arguments", parsedData);
        } catch (JsonProcessingException e) {
        	// TODO: handle/log
            // ignore
        } catch (IOException e) {
            // ignore
        	// TODO: handle/log
        }
    }

    @ShellMethod("Check model availability")
    @Retryable(value = {Exception.class}, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    public boolean checkModelAvailability(String mainModel) throws IOException {
        List<Model> models = openAiService.listModels();
        for (Model model : models) {
            if (model.getId().equals(mainModel)) {
                return true;
            }
        }
        return false;
    }
   
    @ShellMethod("List available models")
    public void listModels() throws IOException {
        List<Model> models = openAiService.listModels();  
        List<ModelInfo> modelInfos = new ArrayList<>();
        for (Model model : models) {
            modelInfos.add(new ModelInfo(model.getId(), model.getRoot()));
        }
        
        BeanListTableModel<ModelInfo> model = new BeanListTableModel<>(modelInfos, "id", "displayName");
        
        TableBuilder tableBuilder = new TableBuilder(model);
        Table table = tableBuilder.addFullBorder(BorderStyle.fancy_light).build();
        
        System.out.println(table.render(80));
    }
    
    //get_rel_fname 
    public String getRelFname(String fname) {
    	
    	// TODO: define this from example
        // Assuming the base directory is stored in the baseDir variable
        String baseDir = "/path/to/your/base/directory";

        // Get the relative path
        Path filePath = Paths.get(fname);
        Path basePath = Paths.get(baseDir);
        Path relativePath = basePath.relativize(filePath);

        return relativePath.toString();
    }
    
    
    
    public FileTime getLastModified(String fileName) throws IOException {
        Path filePath = Paths.get(fileName);
        return Files.getLastModifiedTime(filePath);
    }
    
    public List<String> getAllAbsFiles(String directory) throws IOException {
        List<String> fileNames = new ArrayList<>();
        Files.walk(Paths.get(directory))
                .filter(Files::isRegularFile)
                .forEach(path -> fileNames.add(path.toAbsolutePath().toString()));
        return fileNames;
    }
    
    // fix errors
//    public void commit(String prefix, boolean ask, String message) throws IOException, GitAPIException {
//        Repository repository = git.getRepository();
//
//        // Check if the repository is dirty
//        if (repository.isBare()) {
//            return;
//        }
//
//        // Get the changes and prepare the commit message
//        String diffs = getDiffs(); // This function should return the diffs as a String
//        String context = getContextFromHistory(); // This function should return the context from the history as a String
//
//        if (message == null) {
//            message = getCommitMessage(diffs, context); // This function should return the commit message based on diffs and context
//        }
//        
//        if (!message) {
//            message = "work in progress";
//        }
//        
//        if (prefix != null) {
//            message = prefix + " " + message;
//        }
//
//        // Ask for confirmation before proceeding with the commit
//        if (ask) {
//            // Implement your own logic here to ask the user for confirmation
//            boolean confirmation = askUserForConfirmation(); // This function should return a boolean based on user's input
//
//            if (!confirmation) {
//                System.out.println("Skipped commit.");
//                return;
//            }
//        }
//
//        // Commit the changes to the repository
//        git.add().addFilepattern(".").call();
//        RevCommit commit = git.commit().setMessage(message).call();
//        System.out.println("Commit: " + commit.getId().getName() + " " + message);
//    }
    
// fix errors
//    public String getDiffs() throws IOException, GitAPIException {
//        String diffs = "";
//
//        try (Repository repository = git.getRepository()) {
//            String head = repository.resolve("HEAD^{tree}").name();
//
//            ByteArrayOutputStream out = new ByteArrayOutputStream();
//            try (Git git = new Git(repository)) {
//                List<DiffEntry> diffList = git.diff()
//                        .setOldTree(new CanonicalTreeParser(null, repository.newObjectReader(), head))
//                        .setNewTree(new CanonicalTreeParser(null, repository.newObjectReader(), "HEAD"))
//                        .setOutputStream(out)
//                        .call();
//
//                diffs = out.toString();
//            }
//        }
//
//        return diffs;
//    }
    
    
    // TODO: get the message object from other repo
    // TODO: fix errors
//    public String getContextFromHistory(List<Message> history) {
//        StringBuilder context = new StringBuilder();
//
//        for (Message message : history) {
//            context.append(message.getRole());
//            context.append(": ");
//            context.append(message.getContent());
//            context.append("\n");
//        }
//
//        return context.toString();
//    }
    
    public String getCommitMessage(String diffs, String context) {
        return context + "\n" + diffs;
    }
    
    private static class ModelInfo {
        private String id;
        private String displayName;

        public ModelInfo(String id, String displayName) {
            this.id = id;
            this.displayName = displayName;
        }

        public String getId() {
            return id;
        }

        public String getDisplayName() {
            return displayName;
        }
    }
}