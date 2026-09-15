package ru.kiero.nomad.networking;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.PacketDistributor;
import ru.kiero.nomad.data.CampData;

import java.util.UUID;
import java.util.function.Supplier;

public class LevelUpRequestPacket {

    private final BlockPos blockPos;

    public LevelUpRequestPacket(BlockPos blockPos){
        this.blockPos = blockPos;
    }
    public LevelUpRequestPacket(FriendlyByteBuf buf) {
        this.blockPos = buf.readBlockPos();
    }

    public void write(FriendlyByteBuf buf){
        buf.writeBlockPos(blockPos);
    }

    public void handle(Supplier<NetworkEvent.Context> sup){
        NetworkEvent.Context ctx = sup.get();
        ServerPlayer player = ctx.getSender();

        CampData data = CampData.get(player.serverLevel());
        UUID camp = data.getCampAt(blockPos);
        if (camp == null) return;

        NomadNetworking.CHANNEL.send(PacketDistributor.PLAYER.with(() -> player),
                new LevelUpResponsePacket(data.getWood(camp), data.getFood(camp), data.getStone(camp), data.getLeather(camp), data.getRare(camp), data.getName(camp), data.getLevelOf(camp), data.getExp(camp), data.getRadius(camp), blockPos));
    }
}
