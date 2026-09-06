package ru.kiero.nomad.entity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import ru.kiero.nomad.data.CampData;
import ru.kiero.nomad.entity.goals.ReturnToCampGoal;

import java.util.UUID;

public class NomadEntity extends PathfinderMob {

    private static final EntityDataAccessor<Integer> DATA_PROFESSION_ID = SynchedEntityData.defineId(NomadEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<String> CAMP_UUID = SynchedEntityData.defineId(NomadEntity.class, EntityDataSerializers.STRING);

    private CampData data;

    public NomadEntity(EntityType<? extends PathfinderMob> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        if(pLevel instanceof ServerLevel serverLevel) data = CampData.get(serverLevel);
    }

    public static AttributeSupplier.Builder createAttributes(){
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 20D).add(Attributes.MOVEMENT_SPEED, 0.25D);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        goalSelector.addGoal(0, new FloatGoal(this));
        if (this.getProfession() == Profession.HUNTER) {
            this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Monster.class, true));
            goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.0, true));
        }
        goalSelector.addGoal(1, new ReturnToCampGoal(this, 0.5));
        goalSelector.addGoal(2, new LookAtPlayerGoal(this, Player.class, 8F));
        goalSelector.addGoal(3, new RandomStrollGoal(this, 1.0));
        goalSelector.addGoal(4, new RandomLookAroundGoal(this));
    }

    //Save Data
    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_PROFESSION_ID, Profession.NONE.getId());
        this.entityData.define(CAMP_UUID, "");
    }

    //Getter
    public Profession getProfession(){
        return Profession.fromId(this.entityData.get(DATA_PROFESSION_ID));
    }
    public UUID getCampUUID(){
        if (!this.entityData.get(CAMP_UUID).isEmpty()) return UUID.fromString(this.entityData.get(CAMP_UUID));
        return null;
    }

    //Setter
    public void setProfession(Profession profession){
        this.entityData.set(DATA_PROFESSION_ID, profession.getId());
    }
    public void setCampUUID(UUID uuid){
        this.entityData.set(CAMP_UUID, uuid.toString());
    }

    //NBT
    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putInt("profession", this.getProfession().getId());
        if (this.getCampUUID() != null) pCompound.putUUID("campUUID", this.getCampUUID());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        if (pCompound.contains("profession")){
            this.setProfession(Profession.fromId(pCompound.getInt("profession")));
        }
        if (pCompound.contains("campUUID")){
            this.setCampUUID(pCompound.getUUID("campUUID"));
        }
    }
}
