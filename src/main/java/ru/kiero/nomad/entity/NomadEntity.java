package ru.kiero.nomad.entity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class NomadEntity extends PathfinderMob {

    public static EntityDataAccessor<Integer> DATA_PROFESSION_ID = SynchedEntityData.defineId(NomadEntity.class, EntityDataSerializers.INT);

    public NomadEntity(EntityType<? extends PathfinderMob> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public static AttributeSupplier.Builder createAttributes(){
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 20D).add(Attributes.MOVEMENT_SPEED, 0.25D);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        goalSelector.addGoal(0, new FloatGoal(this));
        goalSelector.addGoal(1, new LookAtPlayerGoal(this, Player.class, 8F));
        goalSelector.addGoal(2, new RandomStrollGoal(this, 1.0));
        goalSelector.addGoal(3, new RandomLookAroundGoal(this));
    }

    //Save Data
    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_PROFESSION_ID, Profession.NONE.getId());
    }

    //Getter
    public Profession getProfession(){
        return Profession.fromId(this.entityData.get(DATA_PROFESSION_ID));
    }

    //Setter
    public void setProfession(Profession profession){
        this.entityData.set(DATA_PROFESSION_ID, profession.getId());
    }

    //NBT
    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putInt("profession", this.getProfession().getId());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        if (pCompound.contains("profession")){
            this.setProfession(Profession.fromId(pCompound.getInt("profession")));
        }
    }
}
