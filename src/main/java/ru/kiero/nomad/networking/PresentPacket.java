package ru.kiero.nomad.networking;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkHooks;
import ru.kiero.nomad.blocks.TotemBlockEntity;
import ru.kiero.nomad.menu.PresentMenu;

import java.util.function.Supplier;

public class PresentPacket {

    private final BlockPos blockPos;

    public PresentPacket(BlockPos blockPos) {
        this.blockPos = blockPos;
    }

    public PresentPacket(FriendlyByteBuf buf){
        this.blockPos = buf.readBlockPos();
    }

    public void write(FriendlyByteBuf buf){
        buf.writeBlockPos(blockPos);
    }

    public void handle(Supplier<NetworkEvent.Context> sup){
        NetworkEvent.Context ctx = sup.get();
        ServerPlayer serverPlayer = ctx.getSender();
        if (serverPlayer != null){
            if (serverPlayer.level().getBlockEntity(blockPos) instanceof TotemBlockEntity be) {
                NetworkHooks.openScreen(serverPlayer, be, buf -> buf.writeBlockPos(blockPos));
            }
        }
    }
}
