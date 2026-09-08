package ru.kiero.nomad.networking;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.PacketDistributor;
import ru.kiero.nomad.client.NomadClient;
import ru.kiero.nomad.data.CampData;
import ru.kiero.nomad.entity.Profession;
import ru.kiero.nomad.menu.PresentMenu;

import java.util.UUID;
import java.util.function.Supplier;

public class ReturnPacket {

    private final BlockPos blockPos;

    public ReturnPacket(BlockPos blockPos) {
        this.blockPos = blockPos;
    }

    public ReturnPacket(FriendlyByteBuf buf){
        this.blockPos = buf.readBlockPos();
    }

    public void write(FriendlyByteBuf buf){
        buf.writeBlockPos(blockPos);
    }

    public void handle(Supplier<NetworkEvent.Context> sup){
        NetworkEvent.Context ctx = sup.get();

        ServerPlayer serverPlayer = ctx.getSender();
        if (serverPlayer != null){
            Level level = serverPlayer.level();
            if (level.isClientSide()) return;
            CampData data = CampData.get((ServerLevel) level);
            UUID camp = data.getCampAt(blockPos);

            if (serverPlayer.containerMenu instanceof PresentMenu menu){
                serverPlayer.closeContainer();
                NomadNetworking.CHANNEL.send(PacketDistributor.PLAYER.with(() -> serverPlayer), new MainScreenPacket(
                        data.getName(camp), data.getLevelOf(camp), data.getExp(camp), data.getFriendship(camp, serverPlayer.getUUID()), data.getRadius(camp),
                        data.getFood(camp), data.getWood(camp), data.getStone(camp), data.getLeather(camp), data.getRare(camp), data.hasProfession(camp, Profession.SHAMAN), blockPos));
                return;
            }
        }


    }
}
