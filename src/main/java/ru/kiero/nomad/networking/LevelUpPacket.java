package ru.kiero.nomad.networking;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import ru.kiero.nomad.data.CampData;
import ru.kiero.nomad.data.ResourceCategory;

import java.util.UUID;
import java.util.function.Supplier;

public class LevelUpPacket {

    private final int level;
    private final int exp;
    private final BlockPos blockPos;

    private final int wood;
    private final int food;
    private final int stone;
    private final int leather;
    private final int rare;

    public LevelUpPacket(BlockPos blockPos, int wood, int food, int stone, int leather, int rare, int level, int exp) {
        this.level = level;
        this.exp = exp;
        this.blockPos = blockPos;

        this.food = food;
        this.wood = wood;
        this.stone = stone;
        this.leather = leather;
        this.rare = rare;
    }

    public LevelUpPacket(FriendlyByteBuf buf) {
        this.level = buf.readInt();
        this.exp = buf.readInt();
        this.blockPos = buf.readBlockPos();

        this.food = buf.readInt();
        this.wood = buf.readInt();
        this.stone = buf.readInt();
        this.leather = buf.readInt();
        this.rare = buf.readInt();
    }

    public void write(FriendlyByteBuf buf){
        buf.writeInt(level);
        buf.writeInt(exp);
        buf.writeBlockPos(blockPos);

        buf.writeInt(food);
        buf.writeInt(wood);
        buf.writeInt(stone);
        buf.writeInt(leather);
        buf.writeInt(rare);
    }

    public void handle(Supplier<NetworkEvent.Context> sup){
        NetworkEvent.Context ctx = sup.get();
        ServerPlayer player = ctx.getSender();

        CampData data = CampData.get(player.serverLevel());
        UUID camp = data.getCampAt(blockPos);
        if (camp == null) return;

        if (wood >= CampData.woodForLevel.get(level-1) && food >= CampData.foodForLevel.get(level-1) &&
                stone >= CampData.stoneForLevel.get(level-1) && leather >= CampData.leatherForLevel.get(level-1) &&
                rare >= CampData.rareForLevel.get(level-1) && exp == CampData.expForLevel.get(level-1)){
            data.levelUp(camp);

            data.addResources(camp, ResourceCategory.WOOD, -CampData.woodForLevel.get(level-1));
            data.addResources(camp, ResourceCategory.FOOD, -CampData.foodForLevel.get(level-1));
            data.addResources(camp, ResourceCategory.STONE, -CampData.stoneForLevel.get(level-1));
            data.addResources(camp, ResourceCategory.LEATHER, -CampData.leatherForLevel.get(level-1));
            data.addResources(camp, ResourceCategory.RARE, -CampData.rareForLevel.get(level-1));

            player.sendSystemMessage(Component.literal("Уровень поселения повышен"));
        }else{
            player.sendSystemMessage(Component.literal("Не хватает ресурсов или опыта"));
        }
    }
}
