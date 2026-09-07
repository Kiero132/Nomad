package ru.kiero.nomad.data;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.saveddata.SavedData;
import ru.kiero.nomad.Nomad;
import ru.kiero.nomad.entity.NomadEntity;
import ru.kiero.nomad.entity.Profession;

import java.util.*;

public class CampData extends SavedData {

    private Map<UUID, CompoundTag> CAMPS = new HashMap<>();
    private int maxLevel = 5;

    public static final String DATA_NAME = Nomad.MOD_ID + "_camp";
    public static final List<Integer> expForLevel = List.of(100, 200, 500, 1000, 2000);
    private ServerLevel serverLevel;

    @Override
    public CompoundTag save(CompoundTag pCompoundTag) {
        ListTag list = new ListTag();
        for(UUID uuid : CAMPS.keySet()){
            CompoundTag tag = new CompoundTag();
            tag.putUUID("uuid", uuid);
            tag.putLong("blockPos", CAMPS.get(uuid).getLong("blockPos"));

            //Friendship MAP<PlayerUUID, Integer>
            ListTag playerList = new ListTag();

            for (int i=0; i<CAMPS.get(uuid).getList("friendship", ListTag.TAG_COMPOUND).size(); i++){
                CompoundTag playerTag = new CompoundTag();
                playerTag.putUUID("playerUUID", CAMPS.get(uuid).getList("friendship", ListTag.TAG_COMPOUND).getCompound(i).getUUID("playerUUID"));
                playerTag.putInt("playerValue", CAMPS.get(uuid).getList("friendship", ListTag.TAG_COMPOUND).getCompound(i).getInt("playerValue"));
                playerList.add(playerTag);
            }
            tag.put("friendship", playerList);

            //Citizen
            ListTag citizenList = new ListTag();

            for (int i=0; i<CAMPS.get(uuid).getList("citizen", ListTag.TAG_COMPOUND).size(); i++){
                CompoundTag citizenTag = new CompoundTag();
                citizenTag.putUUID("citizenUUID", CAMPS.get(uuid).getList("citizen", ListTag.TAG_COMPOUND).getCompound(i).getUUID("citizenUUID"));
                citizenTag.putInt("citizenProfession", CAMPS.get(uuid).getList("citizen", ListTag.TAG_COMPOUND).getCompound(i).getInt("citizenProfession"));
                citizenList.add(citizenTag);
            }
            tag.put("citizen", citizenList);

            CompoundTag resources = new CompoundTag();
            resources.putInt("food", CAMPS.get(uuid).getCompound("resources").getInt("food"));
            resources.putInt("wood", CAMPS.get(uuid).getCompound("resources").getInt("wood"));
            resources.putInt("stone", CAMPS.get(uuid).getCompound("resources").getInt("stone"));
            resources.putInt("leather", CAMPS.get(uuid).getCompound("resources").getInt("leather"));
            resources.putInt("rare", CAMPS.get(uuid).getCompound("resources").getInt("rare"));
            tag.put("resources", resources);

            tag.putInt("radius", CAMPS.get(uuid).getInt("radius"));
            tag.putString("name", CAMPS.get(uuid).getString("name"));
            tag.putInt("level", CAMPS.get(uuid).getInt("level"));
            tag.putInt("exp", CAMPS.get(uuid).getInt("exp"));


            list.add(tag);
        }
        pCompoundTag.put("data", list);
        return pCompoundTag;
    }

    private static CampData load(CompoundTag tag){
        CampData data = new CampData();
        ListTag list = tag.getList("data", ListTag.TAG_COMPOUND);

        for (int i=0; i<list.size(); i++){
            CompoundTag uuidTag = list.getCompound(i);
            CompoundTag tagP = new CompoundTag();

            tagP.putLong("blockPos", BlockPos.of(uuidTag.getLong("blockPos")).asLong());

            ListTag playerList = uuidTag.getList("friendship", ListTag.TAG_COMPOUND);
            ListTag newPlayerList = new ListTag();

            for (int j=0; j<playerList.size(); j++){
                CompoundTag playerTag = playerList.getCompound(j);
                CompoundTag newTag = new CompoundTag();

                newTag.putUUID("playerUUID", playerTag.getUUID("playerUUID"));
                newTag.putInt("playerValue", playerTag.getInt("playerValue"));
                newPlayerList.add(newTag);
            }
            tagP.put("friendship", newPlayerList);

            //Citizen
            ListTag citizenList = uuidTag.getList("citizen", ListTag.TAG_COMPOUND);
            ListTag newCitizenList = new ListTag();

            for (int j=0; j<citizenList.size(); j++){
                CompoundTag citizenTag = citizenList.getCompound(j);
                CompoundTag newTag = new CompoundTag();

                newTag.putUUID("citizenUUID", citizenTag.getUUID("citizenUUID"));
                newTag.putInt("citizenProfession", citizenTag.getInt("citizenProfession"));
                newCitizenList.add(newTag);
            }
            tagP.put("citizen", newCitizenList);

            CompoundTag resources = new CompoundTag();
            resources.putInt("food", uuidTag.getCompound("resources").getInt("food"));
            resources.putInt("wood", uuidTag.getCompound("resources").getInt("wood"));
            resources.putInt("stone", uuidTag.getCompound("resources").getInt("stone"));
            resources.putInt("leather", uuidTag.getCompound("resources").getInt("leather"));
            resources.putInt("rare", uuidTag.getCompound("resources").getInt("rare"));
            tagP.put("resources", resources);

            tagP.putInt("radius", uuidTag.getInt("radius"));
            tagP.putString("name", uuidTag.getString("name"));
            tagP.putInt("level", uuidTag.getInt("level"));
            tagP.putInt("exp", uuidTag.getInt("exp"));

            data.CAMPS.put(uuidTag.getUUID("uuid"), tagP);
        }
        return data;
    }

    public static CampData get(ServerLevel serverLevel){
        CampData data = serverLevel.getDataStorage().computeIfAbsent(CampData::load, CampData::new, DATA_NAME);
        data.serverLevel = serverLevel;
        return data;
    }

    public UUID createCamp(BlockPos blockPos){
        UUID uuid = UUID.randomUUID();
        CompoundTag tag = new CompoundTag();
        tag.putLong("blockPos", blockPos.asLong());
        tag.putInt("radius", getRadiusOf(1)); //Test radius
        tag.putString("name", nameGenerate());
        tag.putInt("level", 1);
        tag.putInt("exp", 0);

        CompoundTag resources = new CompoundTag();
        resources.putInt("food", 0);
        resources.putInt("wood", 0);
        resources.putInt("stone", 0);
        resources.putInt("leather", 0);
        resources.putInt("rare", 0);
        tag.put("resources", resources);

        CAMPS.put(uuid, tag);
        setDirty();

        return uuid;
    }
    public void removeCamp(UUID uuid){
        if(CAMPS.containsKey(uuid)) CAMPS.remove(uuid);
        setDirty();
    }

    public UUID getCampAt(BlockPos pos){
        for (UUID uuid : CAMPS.keySet()){
            if(BlockPos.of(CAMPS.get(uuid).getLong("blockPos")).equals(pos)){
                return uuid;
            }
        }
        return null;
    }

    //Геттеры
    public BlockPos getBlockPos(UUID uuid)      {return BlockPos.of(CAMPS.get(uuid).getLong("blockPos"));}
    public int getRadius(UUID uuid)             {return CAMPS.get(uuid).getInt("radius");}
    public String getName(UUID uuid)            {return CAMPS.get(uuid).getString("name");}
    public int getLevelOf(UUID uuid)            {return CAMPS.get(uuid).getInt("level");}
    public int getExp(UUID uuid)                {return CAMPS.get(uuid).getInt("exp");}

    public int getFood(UUID uuid)               {return CAMPS.get(uuid).getCompound("resources").getInt("food");}
    public int getWood(UUID uuid)               {return CAMPS.get(uuid).getCompound("resources").getInt("wood");}
    public int getStone(UUID uuid)              {return CAMPS.get(uuid).getCompound("resources").getInt("stone");}
    public int getLeather(UUID uuid)            {return CAMPS.get(uuid).getCompound("resources").getInt("leather");}
    public int getRare(UUID uuid)               {return CAMPS.get(uuid).getCompound("resources").getInt("rare");}

    public int getFriendship(UUID uuid, UUID player) {
        int friendship = 0;
        for (int i=0; i<CAMPS.get(uuid).getList("friendship", ListTag.TAG_COMPOUND).size(); i++){
            if(CAMPS.get(uuid).getList("friendship", ListTag.TAG_COMPOUND).getCompound(i).getUUID("playerUUID").equals(player)){
                friendship = CAMPS.get(uuid).getList("friendship", ListTag.TAG_COMPOUND).getCompound(i).getInt("playerValue");
                return friendship;
            }
        }
        return 0;
    }
    public List<UUID> getCitizens(UUID uuid){
        ListTag citizenList = CAMPS.get(uuid).getList("citizen", ListTag.TAG_COMPOUND);
        if (citizenList.isEmpty()) return null;
        List<UUID> citizens = new ArrayList<>();
        for (int i=0; i<citizenList.size(); i++){
            CompoundTag citizenTag = citizenList.getCompound(i);
            citizens.add(citizenTag.getUUID("citizenUUID"));
        }
        return citizens;
    }

    public void addCitizen(UUID campUUID, UUID citizenUUID){
        ListTag citizenList = CAMPS.get(campUUID).getList("citizen", ListTag.TAG_COMPOUND);
        if (citizenList.isEmpty()){
            CAMPS.get(campUUID).put("citizen", new ListTag());
        }
        CompoundTag tag = new CompoundTag();
        tag.putUUID("citizenUUID", citizenUUID);
        tag.putInt("citizenProfession", Profession.NONE.getId());
        citizenList.add(tag);

        setDirty();
    }
    public void addCitizen(UUID campUUID, UUID citizenUUID, Profession p){
        ListTag citizenList = CAMPS.get(campUUID).getList("citizen", ListTag.TAG_COMPOUND);
        if (citizenList.isEmpty()){
            CAMPS.get(campUUID).put("citizen", new ListTag());
        }
        CompoundTag tag = new CompoundTag();
        tag.putUUID("citizenUUID", citizenUUID);
        tag.putInt("citizenProfession", p.getId());
        citizenList.add(tag);

        setDirty();
    }
    public boolean hasCitizen(UUID campUUID, UUID citizenUUID){
        ListTag citizenList = CAMPS.get(campUUID).getList("citizen", ListTag.TAG_COMPOUND);
        if (citizenList.isEmpty()) return false;
        for (int i=0; i<citizenList.size(); i++){
            CompoundTag tag = citizenList.getCompound(i);
            if (citizenUUID.equals(tag.getUUID("citizenUUID"))){
                return true;
            }
        }
        return false;
    }
    public int hasProfession(UUID campUUID, Profession p){
        ListTag citizenList = CAMPS.get(campUUID).getList("citizen", ListTag.TAG_COMPOUND);
        if (citizenList.isEmpty()) return 0;
        for (int i=0; i<citizenList.size(); i++){
            CompoundTag tag = citizenList.getCompound(i);

            if (tag.getInt("citizenProfession") == p.getId()){
                return 1;
            }
        }
        return 0;
    }
    public void changeProfession(UUID campUUID, UUID citizenUUID, Profession p){
        if (campUUID == null) return;
        if (CAMPS.get(campUUID).getList("citizen", ListTag.TAG_COMPOUND).isEmpty()) return;
        ListTag citizenList = CAMPS.get(campUUID).getList("citizen", ListTag.TAG_COMPOUND);
        for (int i=0; i<citizenList.size(); i++){
            CompoundTag tag = citizenList.getCompound(i);

            if (tag.getUUID("citizenUUID").equals(citizenUUID)){
                tag.remove("citizenProfession");
                tag.putInt("citizenProfession", p.getId());
            }
        }
        setDirty();
    }

    public void addFriendship(UUID uuid, UUID player, int value){
        if (CAMPS.get(uuid).getList("friendship", ListTag.TAG_COMPOUND).isEmpty()) {
            CAMPS.get(uuid).put("friendship", new ListTag());
            addPlayer(uuid, player, value);
            return;
        }
        boolean isContains = false;
        for (int i=0; i<CAMPS.get(uuid).getList("friendship", ListTag.TAG_COMPOUND).size(); i++) {
            CompoundTag tag = CAMPS.get(uuid).getList("friendship", ListTag.TAG_COMPOUND).getCompound(i);
            if (tag.getUUID("playerUUID").equals(player)) {
                    int res = tag.getInt("playerValue") + value;
                    if (res > 100) {
                        res = 100;
                    } else if (res < -100) {
                        res = -100;
                    }
                    tag.remove("playerValue");
                    tag.putInt("playerValue", res);
                    isContains = true;
                }
        }
        if(!isContains){
            addPlayer(uuid, player, value);
            return;
        }
        setDirty();
    }
    public void addExp(UUID uuid, int value){
        int expNow = CAMPS.get(uuid).getInt("exp");
        int res;
        if(getLevelOf(uuid) != maxLevel) {
            if (expNow + value >= expForLevel.get(getLevelOf(uuid) - 1)) {
                res = expNow + value - expForLevel.get(getLevelOf(uuid) - 1);
                levelUp(uuid);
            } else {
                res = expNow + value;
            }
        }else{
            if(expNow+value >= expForLevel.get(maxLevel-1)){
                res = expForLevel.get(maxLevel-1);
            }else{
                res = expNow + value;
            }
        }
        CAMPS.get(uuid).remove("exp");
        CAMPS.get(uuid).putInt("exp", res);
        setDirty();
    }
    public void levelUp(UUID uuid){
        int newLevel = getLevelOf(uuid) + 1;
        //TODO Citizen count

        CAMPS.get(uuid).remove("level");
        CAMPS.get(uuid).putInt("level", newLevel);
        CAMPS.get(uuid).remove("radius");
        CAMPS.get(uuid).putInt("radius", getRadiusOf(newLevel));
        setDirty();
    }
    public void addResources(UUID uuid, String resourcesType, int value){
        CompoundTag resources = CAMPS.get(uuid).getCompound("resources");

        int countCopy = resources.getInt(resourcesType);
        resources.remove(resourcesType);
        resources.putInt(resourcesType, countCopy+value);
        setDirty();
    }

    private void addPlayer(UUID uuid, UUID player, int friendship){
        CompoundTag tag = new CompoundTag();
        tag.putUUID("playerUUID", player);
        if (friendship > 100) {
            friendship = 100;
        } else if (friendship < -100) {
            friendship = -100;
        }
        tag.putInt("playerValue", friendship);
        CAMPS.get(uuid).getList("friendship", ListTag.TAG_COMPOUND).add(tag);
        setDirty();
    }

    public int getRadiusOf(int level){
        return switch (level){
            case 1 -> 15;
            case 2 -> 25;
            case 3 -> 35;
            case 4 -> 45;
            case 5 -> 60;
            default -> 0;
        };
    }

    private String nameGenerate(){
        List<String> first = List.of("Серый", "Красный", "Северный", "Тихий", "Дикий", "Железный");
        List<String> second = List.of("Ветер", "Камень", "Волк", "Огонь", "Песок", "Орёл");

        Random rand = new Random();

        String res = first.get(rand.nextInt(first.size())) + " " + second.get(rand.nextInt(second.size()));
        return res;
    }
}