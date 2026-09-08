package ru.kiero.nomad.networking;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import ru.kiero.nomad.Nomad;

public class NomadNetworking {
    public static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(new ResourceLocation(Nomad.MOD_ID, "main"), () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);

    public static void reg(){
        CHANNEL.messageBuilder(MainScreenPacket.class, 1).encoder(MainScreenPacket::write).decoder(MainScreenPacket::new).consumerMainThread(MainScreenPacket::handle).add();
        CHANNEL.messageBuilder(PresentPacket.class, 2).encoder(PresentPacket::write).decoder(PresentPacket::new).consumerMainThread(PresentPacket::handle).add();;
        CHANNEL.messageBuilder(GiftPacket.class, 3).encoder(GiftPacket::write).decoder(GiftPacket::new).consumerMainThread(GiftPacket::handle).add();
    }
}
