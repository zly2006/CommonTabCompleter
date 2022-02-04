package com.github.zly2006;

import com.sun.javaws.exceptions.InvalidArgumentException;
import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Token {
    TokenType tokenType = TokenType.NONE;
    Pattern pattern;
    Server server;
    Class<?> Enum;

    public Token() {

    }

    public TokenType getTokenType() {
        return tokenType;
    }

    public Pattern getPattern() {
        return pattern;
    }

    public Server getServer() {
        return server;
    }

    public Token setTokenType(TokenType tokenType) {
        this.tokenType = tokenType;
        if (tokenType == TokenType.INTEGER) {
            setRegex("^[-\\+]?\\d+$");
        }
        if (tokenType == TokenType.FLOATING) {
            setRegex("^[-\\+]?\\d*\\.\\d*$");
        }
        return this;
    }

    public Token setPattern(Pattern pattern) {
        this.pattern = pattern;
        return this;
    }

    public Token setRegex(String regex) {
        this.pattern = Pattern.compile(regex);
        return this;
    }

    public Token setServer(Server server) {
        this.server = server;
        return this;
    }

    public Class<?> getEnum() {
        return Enum;
    }

    public Token setEnum(Class<?> anEnum) throws InvalidArgumentException {
        if (anEnum.isEnum())
            this.Enum = anEnum;
        else
            throw new InvalidArgumentException(new String[]{anEnum.getCanonicalName()});
        return this;
    }

    public boolean match(String arg) {
        switch (tokenType) {
            case NONE:
                return true;
            case REGEX:
            case INTEGER:
            case FLOATING:
                return pattern.matcher(arg).matches();
            case ONLINE_PLAYERS: {
                boolean found = false;
                for (Player player : server.getOnlinePlayers()) {
                    if (player.getName() == arg) {
                        found = true;
                        break;
                    }
                }
                return found;
            }
            case PLAYERS: {
                boolean found = false;
                for (OfflinePlayer player : server.getOfflinePlayers()) {
                    if (player.getName() == arg) {
                        found = true;
                        break;
                    }
                }
                return found;
            }
            case ENUM: {
                boolean found = false;
                for (Object obj : Enum.getEnumConstants()) {
                    if (obj.toString() == arg) {
                        found = true;
                        break;
                    }
                }
                return found;
            }
            default:
                return false;
        }
    }

    public List<String> advise() {
        if (tokenType == TokenType.PLAYERS) {
            List<String> strings = new ArrayList<>();
            for (OfflinePlayer player : server.getOfflinePlayers()) {
                strings.add(player.getName());
            }
            return strings;
        } else if (tokenType == TokenType.ONLINE_PLAYERS) {
            List<String> strings = new ArrayList<>();
            for (OfflinePlayer player : server.getOnlinePlayers()) {
                strings.add(player.getName());
            }
            return strings;
        } else if (tokenType == TokenType.ENUM) {
            List<String> strings = new ArrayList<>();
            for (Object obj : Enum.getEnumConstants()) {
                strings.add(obj.toString());
            }
            return strings;
        } else return null;
    }
}
