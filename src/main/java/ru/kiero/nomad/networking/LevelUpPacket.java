package ru.kiero.nomad.networking;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraftforge.network.NetworkEvent;
import ru.kiero.nomad.client.NomadClient;
import ru.kiero.nomad.client.screens.LevelUpScreen;

import java.util.function.Supplier;

public class LevelUpPacket {

    public LevelUpPacket(){

    }
    public LevelUpPacket(FriendlyByteBuf buf) {
    }

    public void write(FriendlyByteBuf buf){

    }

    public void handle(Supplier<NetworkEvent.Context> sup){
        NetworkEvent.Context ctx = sup.get();

        NomadClient.openScreen(sup, new LevelUpScreen(Component.literal("")));
        ctx.setPacketHandled(true);
    }
}
