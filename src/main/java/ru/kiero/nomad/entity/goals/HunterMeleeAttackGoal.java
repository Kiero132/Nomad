package ru.kiero.nomad.entity.goals;

import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import ru.kiero.nomad.entity.NomadEntity;
import ru.kiero.nomad.entity.Profession;

public class HunterMeleeAttackGoal extends MeleeAttackGoal {

    private NomadEntity nomad;

    public HunterMeleeAttackGoal(PathfinderMob pMob, double pSpeedModifier, boolean pFollowingTargetEvenIfNotSeen) {
        super(pMob, pSpeedModifier, pFollowingTargetEvenIfNotSeen);
        if (pMob instanceof NomadEntity ne){
            this.nomad = ne;
        }
    }

    @Override
    public boolean canUse() {
        if (nomad.getProfession() == Profession.HUNTER) {
            return super.canUse();
        }else{
            return false;
        }
    }

    @Override
    public boolean canContinueToUse() {
        if (nomad.getProfession() == Profession.HUNTER) {
            return super.canContinueToUse();
        }else{
            return false;
        }
    }
}
