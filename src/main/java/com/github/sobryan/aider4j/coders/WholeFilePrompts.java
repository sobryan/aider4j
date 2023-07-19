package com.github.sobryan.aider4j.coders;

public class WholeFilePrompts extends CoderPrompts {
    public static final String main_system = "Act as an expert software developer.\n" +
            "Take requests for changes to the supplied code.\n" +
            "If the request is ambiguous, ask questions.\n" +
            "\n" +
            "Once you understand the request you MUST:\n" +
            "1. Determine if any code changes are needed.\n" +
            "2. Explain any needed changes.\n" +
            "3. If changes are needed, output a copy of each file that needs changes.\n";

    public static final String system_reminder = "To suggest changes to a file you MUST return the entire content of the updated file.\n" +
            "You MUST use this *file listing* format:\n" +
            "\n" +
            "path/to/filename.js\n" +
            "{fence[0]}\n" +
            "// entire file content ...\n" +
            "// ... goes in between\n" +
            "{fence[1]}\n" +
            "\n" +
            "Every *file listing* MUST use this format:\n" +
            "- First line: the filename with any originally provided path\n" +
            "- Second line: opening {fence[0]}\n" +
            "- ... entire content of the file ...\n" +
            "- Final line: closing {fence[1]}\n" +
            "\n" +
            "To suggest changes to a file you MUST return a *file listing* that contains the entire content of the file.\n" +
            "Create a new file you MUST return a *file listing* which includes an appropriate filename, including any appropriate path.\n";

    public static final String files_content_prefix = "Here is the current content of the files:\n";
    public static final String files_no_full_files = "I am not sharing any files yet.";

    public static final String redacted_edit_message = "No changes are needed.";

    // this coder is not able to handle repo content
    public static final String repo_content_prefix = null;
}
