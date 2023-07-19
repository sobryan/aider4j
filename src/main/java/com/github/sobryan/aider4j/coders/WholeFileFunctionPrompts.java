package com.github.sobryan.aider4j.coders;

public class WholeFileFunctionPrompts extends WholeFilePrompts {
    public static final String function_name_prompt = "What is the name of the function you want to modify?";
    public static final String function_args_prompt = "What are the arguments of the function?";
    public static final String function_body_prompt = "What is the new body of the function?";
    public static final String function_return_prompt = "What is the new return value of the function?";
    public static final String function_no_return = "This function does not have a return statement.";
    public static final String function_no_args = "This function does not have any arguments.";
    public static final String function_no_body = "This function does not have a body.";
    public static final String function_no_changes = "No changes are needed to this function.";
}