package ru.kiero.nomad.networking;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraftforge.network.NetworkEvent;
import ru.kiero.nomad.client.NomadClient;
import ru.kiero.nomad.client.TotemMainScreen;

import java.util.function.Supplier;

public class MainScreenPacket {

    private final String lable;
    private final int levelOf;
    private final int exp;
    private final int friendship;
    private final int radius;

    private final int food;
    private final int wood;
    private final int stone;
    private final int leather;
    private final int rare;

    public MainScreenPacket(String lable, int levelOf, int exp, int friendship, int radius, int food, int wood, int stone, int leather, int rare) {
        this.lable = lable;
        this.levelOf = levelOf;
        this.exp = exp;
        this.friendship = friendship;
        this.radius = radius;

        this.food = food;
        this.wood = wood;
        this.stone = stone;
        this.leather = leather;
        this.rare = rare;
    }

    public MainScreenPacket(FriendlyByteBuf buf){
        this.lable = buf.readUtf();
        this.levelOf = buf.readInt();
        this.exp = buf.readInt();
        this.friendship = buf.readInt();
        this.radius = buf.readInt();

        this.food = buf.readInt();
        this.wood = buf.readInt();
        this.stone = buf.readInt();
        this.leather = buf.readInt();
        this.rare = buf.readInt();
    }

    public void write(FriendlyByteBuf buf){
        buf.writeUtf(lable);
        buf.writeInt(levelOf);
        buf.writeInt(exp);
        buf.writeInt(friendship);
        buf.writeInt(radius);

        buf.writeInt(food);
        buf.writeInt(wood);
        buf.writeInt(stone);
        buf.writeInt(leather);
        buf.writeInt(rare);
    }

    public void handle(Supplier<NetworkEvent.Context> sup){
        NetworkEvent.Context ctx = sup.get();

        NomadClient.openScreen(sup, new TotemMainScreen(Component.literal(""), lable, levelOf, exp, friendship, radius, food, wood, stone, leather, rare));
        ctx.setPacketHandled(true);
    }
}
