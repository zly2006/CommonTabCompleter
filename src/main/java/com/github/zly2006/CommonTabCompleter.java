package com.github.zly2006;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CommonTabCompleter implements TabCompleter {
    private List<Grammar> grammars = new ArrayList<>();

    public List<Grammar> getGrammars() {
        return grammars;
    }

    public void setGrammars(List<Grammar> grammars) {
        this.grammars = grammars;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        List<String> advice = new ArrayList<>();
        for (Grammar grammar : grammars) {
            if (grammar.matchSoFar(command, args)) {
                List<String> strs = grammar.advise(args.length - 1);
                if (strs != null) {
                    advice.addAll(strs);
                }
            }
        }
        return advice;
    }

    public void showHelp(CommandSender sender) {
        if (sender instanceof Player) {

        } else {

        }
    }
}

