package ru.kiero.nomad.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class NomadClient {

    public static void openScreen(Supplier<NetworkEvent.Context> sup, Screen screen){
        NetworkEvent.Context ctx = sup.get();
        ctx.enqueueWork(() -> Minecraft.getInstance().setScreen(screen));
    }
}
