package ru.kiero.nomad.networking;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraftforge.network.NetworkEvent;
import ru.kiero.nomad.client.NomadClient;
import ru.kiero.nomad.client.screens.LevelUpScreen;

import java.util.function.Supplier;

public class LevelUpResponsePacket {

    private final String title;
    private final int level;
    private final int exp;
    private final int radius;

    private final int wood;
    private final int food;
    private final int stone;
    private final int leather;
    private final int rare;

    public LevelUpResponsePacket(int wood, int food, int stone, int leather, int rare, String title, int level, int exp, int radius) {
        this.title = title;
        this.level = level;
        this.exp = exp;
        this.radius = radius;

        this.food = food;
        this.wood = wood;
        this.stone = stone;
        this.leather = leather;
        this.rare = rare;
    }

    public LevelUpResponsePacket(FriendlyByteBuf buf) {
        this.title = buf.readUtf();
        this.level = buf.readInt();
        this.exp = buf.readInt();
        this.radius = buf.readInt();

        this.food = buf.readInt();
        this.wood = buf.readInt();
        this.stone = buf.readInt();
        this.leather = buf.readInt();
        this.rare = buf.readInt();
    }

    public void write(FriendlyByteBuf buf){
        buf.writeUtf(title);
        buf.writeInt(level);
        buf.writeInt(exp);
        buf.writeInt(radius);

        buf.writeInt(food);
        buf.writeInt(wood);
        buf.writeInt(stone);
        buf.writeInt(leather);
        buf.writeInt(rare);
    }

    public void handle(Supplier<NetworkEvent.Context> sup){
        NetworkEvent.Context ctx = sup.get();

        NomadClient.openScreen(sup, new LevelUpScreen(Component.literal(""), wood, food, stone, leather, rare, title, level, exp, radius));
        ctx.setPacketHandled(true);
    }
}
