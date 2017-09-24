package com.mygdx.game;

import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class codeParser {
    private float currentLine;
    private String currentCode;
    private Scanner codeScan;
    private boolean isRunning,isStatement,error;
    private character character;

    public codeParser(character character){
        isStatement = false;
        error = false;
        isRunning = false;
        this.character = character;
        currentLine = 0;
    }

    public boolean error(){return error;}

    public boolean isRunning(){return  isRunning;}

    public boolean hasNextLine(){return codeScan.hasNextLine();}

    public void run(String code){
        isRunning = true;
        codeScan = new Scanner(code);
        character.run();
    }

    public void nextLine(){
        if (codeScan.hasNextLine()){
            currentCode = codeScan.nextLine();
            currentLine++;
            parseLine(currentCode);
        }
        else{
            isRunning = false;
        }
    }

    public void parseLine(String line){
        String s = line;
        Pattern p = Pattern.compile("[^.]+|(.)");
        Matcher firstMatch = p.matcher(s);
        if(firstMatch.find()) {
            String firstParse = firstMatch.group();
            p = Pattern.compile("[^ ]+");
            Matcher secondMatch = p.matcher(firstParse);
            if (secondMatch.find()){
                String statement = secondMatch.group();
                if (statement.equals("hero") && firstMatch.find()){
                    if (firstMatch.group().equals(".") && firstMatch.find()){
                        heroMovement(firstMatch.group());
                    }
                    else{
                        error = true;
                    }
                }
                else if (statement.equals("while")){
                    isStatement = true;
                }
                else if (secondMatch.find()){
                    String nextWord = secondMatch.group();
                    if (nextWord.equals("=")){
                        variable(secondMatch);
                    }
                }
                else{
                    error = true;
                }
            }
            else {
                error = true;
            }
        }
        else {
            error = true;
        }
    }

    public void heroMovement(String statement){
        Pattern p = Pattern.compile("[^(.]+|(\\(.+)");
        Matcher match = p.matcher(statement);
        if (match.find()){
            String method = match.group();
            if(match.find()){
                String parameter = match.group();
                if (parameter.length() != 2){
                    error = true;
                }
            }
            else{
                error = true;
            }
            p = Pattern.compile("[^ ]+");
            match = p.matcher(method);
            if (match.find()){
                method = match.group();
                if(match.find()){
                    error = true;
                }
            }
            else {
                error = true;
            }
            if (method.equals("moveUp")){
                character.moveUp();
            }
            else if (method.equals("moveDown")){
                character.moveDown();
            }
            else if (method.equals("moveLeft")){
                character.moveLeft();
            }
            else if (method.equals("moveRight")){
                character.moveRight();
            }
            else{
                error = true;
            }
        }
        else{
            error = true;
        }
    }


    public void statements(String line){

    }

    public void variable(Matcher line){

    }

}
