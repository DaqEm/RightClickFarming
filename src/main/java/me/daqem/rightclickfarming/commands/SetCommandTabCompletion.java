package me.daqem.rightclickfarming.commands;

import me.daqem.rightclickfarming.RightClickFarming;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.*;

public class SetCommandTabCompletion implements TabCompleter {

    private final RightClickFarming plugin;

    public SetCommandTabCompletion(RightClickFarming pl) {
        this.plugin = pl;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {

        List<String> booleans = new ArrayList<>();
        booleans.add("true");
        booleans.add("false");

        List<String> integers;
        integers = Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "etc.");

        Set<String> setOfArgs1 = Objects.requireNonNull(plugin.getConfig().getConfigurationSection("crops")).getKeys(false);

        if (args.length == 1) {
            List<String> subCommands = new ArrayList<>();
            subCommands.add("help");
            subCommands.add("set");
            subCommands.add("reload");
            subCommands.add("settings");
            return subCommands;
        }
        if (args.length == 2) {
            if (args[0].equalsIgnoreCase("set")) {
                return new ArrayList<>(setOfArgs1);
            }
            if (args[0].equalsIgnoreCase("help")) {
                setOfArgs1.add("poisonous-potato");
                return new ArrayList<>(setOfArgs1);
            }
            if (args[0].equalsIgnoreCase("settings")) {
                List<String> settingArray = new ArrayList<>();
                settingArray.add("hoe-required");
                return settingArray;
            }
        }
        if (args.length == 3) {
            if (args[0].equalsIgnoreCase("set")) {
                for (String string : setOfArgs1) {
                    if (args[1].equalsIgnoreCase(string)) {
                        Set<String> setOfArgs2 = Objects.requireNonNull(plugin.getConfig().getConfigurationSection("crops." + args[1])).getKeys(false);
                        return new ArrayList<>(setOfArgs2);
                    }
                }
            }
            if (args[0].equalsIgnoreCase("settings") && args[1].equalsIgnoreCase("hoe-required")) {
                return booleans;
            }
        }
        if (args.length == 4) {
            if (args[0].equalsIgnoreCase("set")) {
                if (args[2].equalsIgnoreCase("enabled") || args[2].equalsIgnoreCase("seed-drops")) {
                    return booleans;
                } else if (args[2].equalsIgnoreCase("poisonous-potato")) {
                    Set<String> setOfArgsPotato = Objects.requireNonNull(plugin.getConfig().getConfigurationSection("crops.potato.poisonous-potato")).getKeys(false);
                    return new ArrayList<>(setOfArgsPotato);
                } else if (args[1].equalsIgnoreCase("sweetberries")) {
                    Set<String> setOfArgsSweetBerries = Objects.requireNonNull(plugin.getConfig().getConfigurationSection("crops.sweetberries." + args[2])).getKeys(false);
                    return new ArrayList<>(setOfArgsSweetBerries);
                } else {
                    return integers;
                }
            }

        }
        if (args.length == 5) {
            if (args[0].equalsIgnoreCase("set")) {
                if (args[1].equalsIgnoreCase("potato") && args[2].equalsIgnoreCase("poisonous-potato")) {
                    if (args[3].equalsIgnoreCase("enabled")) {
                        return booleans;
                    }
                    else if (args[3].equalsIgnoreCase("drop-percentage") || args[3].equalsIgnoreCase("min-drops") || args[3].equalsIgnoreCase("max-drops")) {
                        return integers;
                    }
                }
                else if (args[1].equalsIgnoreCase("sweetberries") && (args[2].equalsIgnoreCase("fully-grown") || args[2].equalsIgnoreCase("half-grown"))) {
                    return integers;
                }
            }
        }
        return null;
    }
}
