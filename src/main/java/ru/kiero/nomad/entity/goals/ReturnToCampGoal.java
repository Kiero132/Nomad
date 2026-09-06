package ru.kiero.nomad.entity.goals;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import ru.kiero.nomad.data.CampData;
import ru.kiero.nomad.entity.NomadEntity;

import java.util.EnumSet;
import java.util.UUID;

public class ReturnToCampGoal extends Goal {

    private UUID campUUID;
    private NomadEntity nomad;
    private BlockPos campPos;
    private final double speed;
    private CampData data;
    private int radius;
    int counter;

    public ReturnToCampGoal(Mob mob, double speed) {
        super();
        this.speed = speed;

        if (mob instanceof NomadEntity nomad){
            this.nomad = nomad;
            if (nomad.level() instanceof ServerLevel serverLevel){
                CampData data = CampData.get(serverLevel);
                this.data = data;
                if (nomad.getCampUUID() != null) {
                    this.campUUID = nomad.getCampUUID();
                    this.radius = data.getRadius(campUUID);
                    this.campPos = data.getBlockPos(campUUID);
                }
            }
        }
    }

    @Override
    public boolean canUse() {
        if (nomad.getCampUUID() != null){
            if (nomad.level() instanceof ServerLevel serverLevel){
                if (nomad.getCampUUID() != null) {
                    this.campUUID = nomad.getCampUUID();
                    this.radius = data.getRadius(campUUID);
                    this.campPos = data.getBlockPos(campUUID);
                }
                if (campPos != null) {
                    if (data.getCampAt(campPos) != null) {
                        double distanceSqr = nomad.distanceToSqr(campPos.getX(), campPos.getY(), campPos.getZ());
                        double radiusSqr = radius * radius;
                        if (distanceSqr > radiusSqr) return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public void start() {
        super.start();
        nomad.getNavigation().moveTo(campPos.getX(), campPos.getY(), campPos.getZ(), speed);
        if (!nomad.getNavigation().isDone()){
            return;
        }
    }

    @Override
    public void stop() {
        super.stop();
        nomad.getNavigation().stop();
    }

    @Override
    public boolean canContinueToUse() {
        if (campPos != null){
            if (nomad.getNavigation().isInProgress()){
                double distanceSqr = nomad.distanceToSqr(campPos.getX(), campPos.getY(), campPos.getZ());
                double radiusSqr = radius * radius;
                if(distanceSqr > radiusSqr) return true;
            }
        }
        return false;
    }

    @Override
    public EnumSet<Flag> getFlags() {
        return EnumSet.of(Goal.Flag.MOVE);
    }

    @Override
    public void tick() {
        counter++;
        if (counter >= 100){
            this.campUUID = nomad.getCampUUID();
            this.radius = data.getRadius(campUUID);
            this.campPos = data.getBlockPos(campUUID);
            counter = 0;
        }
        super.tick();
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }
}
