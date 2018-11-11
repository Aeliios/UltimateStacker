package com.songoda.ultimatestacker.command.commands;

import com.songoda.arconix.api.methods.formatting.TextComponent;
import com.songoda.ultimatestacker.UltimateStacker;
import com.songoda.ultimatestacker.command.AbstractCommand;
import com.songoda.ultimatestacker.entity.EntityStackManager;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CommandRemoveAll extends AbstractCommand {

    public CommandRemoveAll(AbstractCommand parent) {
        super("removeall", parent, false);
    }

    @Override
    protected ReturnType runCommand(UltimateStacker instance, CommandSender sender, String... args) {
        if (args.length != 2) return ReturnType.SYNTAX_ERROR;

        String type = args[1];

        if (!type.equalsIgnoreCase("entities")
                && !type.equalsIgnoreCase("items")) {
            return ReturnType.SYNTAX_ERROR;
        }

        int amountRemoved = 0;
        EntityStackManager stackManager = instance.getEntityStackManager();
        for (World world : Bukkit.getWorlds()) {

            for (Entity entityO : world.getEntities()) {
                if (entityO instanceof Player) continue;

                    if (entityO.getType() != EntityType.DROPPED_ITEM && stackManager.isStacked(entityO) && type.equalsIgnoreCase("entities")) {
                        entityO.remove();
                        amountRemoved ++;
                    } else if (entityO.getType() == EntityType.DROPPED_ITEM && type.equalsIgnoreCase("items")) {
                        ItemStack item = ((Item) entityO).getItemStack();
                        if (entityO.isCustomNameVisible() && !entityO.getCustomName().contains(TextComponent.convertToInvisibleString("IS")) || item.hasItemMeta() && item.getItemMeta().hasDisplayName())
                        continue;
                        entityO.remove();
                        amountRemoved ++;
                    }

            }
        }

        if (type.equalsIgnoreCase("entities") && amountRemoved == 1) type = "Entity";
        if (type.equalsIgnoreCase("items") && amountRemoved == 1) type = "Item";

        if (amountRemoved == 0) {
            sender.sendMessage(TextComponent.formatText(instance.getReferences().getPrefix() + "&7No stacked " + type + " exist that could be removed."));
        } else {
            sender.sendMessage(TextComponent.formatText(instance.getReferences().getPrefix() + "&7Removed &6" + amountRemoved + " stacked " + TextComponent.formatText(type.toLowerCase(), true) + " &7Successfully."));
        }
        return ReturnType.SUCCESS;
    }

    @Override
    public String getPermissionNode() {
        return "ultimatestacker.admin";
    }

    @Override
    public String getSyntax() {
        return "/us removeall <entities/items>";
    }

    @Override
    public String getDescription() {
        return "Remove all stacked entites or items from the world.";
    }
}