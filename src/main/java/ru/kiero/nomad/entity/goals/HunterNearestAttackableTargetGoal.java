package ru.kiero.nomad.entity.goals;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import ru.kiero.nomad.entity.NomadEntity;
import ru.kiero.nomad.entity.Profession;

public class HunterNearestAttackableTargetGoal extends NearestAttackableTargetGoal<NomadEntity> {

    private NomadEntity nomad;

    public HunterNearestAttackableTargetGoal(Mob pMob, Class pTargetType, boolean pMustSee) {
        super(pMob, pTargetType, pMustSee);
        if (pMob instanceof NomadEntity ne){
            this.nomad = ne;
        }
    }

    @Override
    public boolean canUse() {
        if (nomad.getProfession() == Profession.HUNTER){
            return super.canUse();
        }else{
            return false;
        }
    }
}
