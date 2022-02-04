package com.github.zly2006;

import org.bukkit.command.Command;

import java.util.ArrayList;
import java.util.List;

public class Grammar {
    String command;
    List<Token> tokens = new ArrayList<>();

    public Grammar() {

    }

    public Grammar(List<Token> tokens) {
        this.tokens = tokens;
    }

    public Grammar addToken(Token token) {
        tokens.add(token);
        return this;
    }

    public boolean matchSoFar(Command command, String[] args) {
        if (!command.getName().equalsIgnoreCase(this.command)) return false;
        if (args.length > tokens.size()) return false;
        for (int i = 0; i < args.length - 1; i++) {
            if (!tokens.get(i).match(args[i])) {
                return false;
            }
        }
        return true;
    }

    public List<String> advise(int index) {
        return tokens.get(index).advise();
    }

    public boolean match(Command command, String[] args) {
        if (!command.getName().equalsIgnoreCase(this.command)) return false;
        if (args.length != tokens.size()) return false;
        for (int i = 0; i < args.length - 1; i++) {
            if (!tokens.get(i).match(args[i])) {
                return false;
            }
        }
        return true;
    }
}
