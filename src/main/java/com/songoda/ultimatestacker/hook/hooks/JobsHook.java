package com.songoda.ultimatestacker.hook.hooks;

import com.gamingmesh.jobs.Jobs;
import com.gamingmesh.jobs.actions.EntityActionInfo;
import com.gamingmesh.jobs.container.ActionType;
import com.gamingmesh.jobs.container.JobsPlayer;
import com.songoda.ultimatestacker.hook.StackerHook;
import com.songoda.ultimatestacker.stackable.entity.EntityStack;
import org.bukkit.GameMode;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class JobsHook implements StackerHook {

    @Override
    public void applyExperience(Player player, EntityStack entityStack) {
        if (player.getGameMode().equals(GameMode.CREATIVE))
            return;

        JobsPlayer jPlayer = Jobs.getPlayerManager().getJobsPlayer(player);
        if (jPlayer == null)
            return;

        for (int i = 1; i < entityStack.getAmount(); i++) {
            Entity entity = entityStack.getHostEntity();
            EntityActionInfo eInfo = new EntityActionInfo(entity, ActionType.KILL);
            Jobs.action(jPlayer, eInfo, entity);
        }
    }
}
