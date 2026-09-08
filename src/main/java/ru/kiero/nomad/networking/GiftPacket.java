package ru.kiero.nomad.networking;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;
import ru.kiero.nomad.blocks.TotemBlockEntity;
import ru.kiero.nomad.data.CampData;
import ru.kiero.nomad.data.ResourceCategory;
import ru.kiero.nomad.menu.PresentMenu;

import java.util.UUID;
import java.util.function.Supplier;

public class GiftPacket {

    public GiftPacket(){

    }

    public GiftPacket(FriendlyByteBuf buf){

    }

    public void write(FriendlyByteBuf buf){

    }

    public void handle(Supplier<NetworkEvent.Context> sup){
        NetworkEvent.Context ctx = sup.get();

        ServerPlayer serverPlayer = ctx.getSender();
        if (serverPlayer == null) return;

        if (serverPlayer.containerMenu instanceof PresentMenu menu){
            TotemBlockEntity be = menu.getBlockEntity();
            Level level = be.getLevel();
            if (level.isClientSide()) return;
            ItemStack gift = be.getItems().getStackInSlot(0);
            CampData data = CampData.get((ServerLevel) level);

            UUID campUUID = data.getCampAt(be.getBlockPos());

            if (gift.isEmpty()){
                serverPlayer.sendSystemMessage(Component.literal("Слот для подарка пуст"));
                return;
            }

            if (ResourceCategory.fromStack(gift).equals(ResourceCategory.NONE)){
                serverPlayer.sendSystemMessage(Component.literal("Подарок не подходит"));
                return;
            }

            data.addResources(campUUID, ResourceCategory.fromStack(gift), gift.getCount());
            data.addFriendship(campUUID, serverPlayer.getUUID(), ResourceCategory.repOf(gift)*gift.getCount());
            gift.setCount(0);
        }
    }
}
