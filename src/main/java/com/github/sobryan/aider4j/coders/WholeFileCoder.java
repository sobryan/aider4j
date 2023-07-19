package com.github.sobryan.aider4j.coders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.theokanning.openai.service.OpenAiService;

public class WholeFileCoder extends BaseCoder {
    public WholeFileCoder(ObjectMapper objectMapper, OpenAiService openAiService) {
		super(objectMapper, openAiService);
		// TODO Auto-generated constructor stub
	}

	private WholeFilePrompts gpt_prompts;

//    public WholeFileCoder() {
//        this.gpt_prompts = new WholeFilePrompts();
//    }

	// fix errors
//    @Override
//    public void updateCurMessages(String content, boolean edited) {
//        if (edited) {
//            Map<String, String> message = new HashMap<>();
//            message.put("role", "assistant");
//            message.put("content", gpt_prompts.redacted_edit_message);
//            this.curMessages.add(message);
//        } else {
//            Map<String, String> message = new HashMap<>();
//            message.put("role", "assistant");
//            message.put("content", content);
//            this.curMessages.add(message);
//        }
//    }

	//Fix errors
//    @Override
//    public String getContextFromHistory(List<Map<String, String>> history) {
//        StringBuilder context = new StringBuilder();
//        if (history != null) {
//            context.append("# Context:\n");
//            for (Map<String, String> msg : history) {
//                if (msg.get("role").equals("user")) {
//                    context.append(msg.get("role").toUpperCase()).append(": ").append(msg.get("content")).append("\n");
//                }
//            }
//        }
//        return context.toString();
//    }

	// TODO: Fix errors
//    @Override
//    public String renderIncrementalResponse(boolean finalResponse) {
//        try {
//            return this.updateFiles("diff");
//        } catch (IllegalArgumentException e) {
//            return this.partialResponseContent;
//        }
//    }

	
	// TODO: Fix errors
//    public String updateFiles(String mode) {
//        String content = this.partialResponseContent;
//
//        List<String> chatFiles = this.getInchatRelativeFiles();
//
//        List<String> output = new ArrayList<>();
//        String[] lines = content.split("\\R", -1);
//
//        List<String> edits = new ArrayList<>();
//
//        String sawFname = null;
//
//        // TODO: Implement the rest of the method
//        return "";
//    }
}
