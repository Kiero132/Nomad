package ru.kiero.nomad.events;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import ru.kiero.nomad.Nomad;
import ru.kiero.nomad.data.CampData;
import ru.kiero.nomad.entity.NomadEntity;
import ru.kiero.nomad.entity.Profession;

import java.util.UUID;

@Mod.EventBusSubscriber(modid = Nomad.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class NomadEvents {

    @SubscribeEvent
    public static void onCommands(RegisterCommandsEvent event){
        event.getDispatcher().register(Commands.literal("nomad")
                .then(Commands.literal("create")
                        .executes(ctx -> create(ctx.getSource())))
                .then(Commands.literal("info")
                        .executes(ctx -> info(ctx.getSource())))
                .then(Commands.literal("remove")
                        .executes(ctx -> remove(ctx.getSource())))
                .then(Commands.literal("friendship")
                    .then(Commands.literal("add")
                            .then(Commands.argument("value", IntegerArgumentType.integer(0, 100))
                                    .executes(ctx -> addFriendship(ctx.getSource(), IntegerArgumentType.getInteger(ctx, "value")))))
                .then(Commands.literal("remove")
                        .then(Commands.argument("value", IntegerArgumentType.integer(-100, 0))
                                .executes(ctx -> addFriendship(ctx.getSource(), IntegerArgumentType.getInteger(ctx, "value"))))))
                .then(Commands.literal("profession")
                    .then(Commands.argument("id", IntegerArgumentType.integer(0, 3))
                            .executes(ctx -> setProfession(ctx.getSource(), IntegerArgumentType.getInteger(ctx, "id")))))
                .then(Commands.literal("nomadinfo")
                        .executes(ctx -> nomadInfo(ctx.getSource())))
                .then(Commands.literal("bind")
                        .executes(ctx -> setBind(ctx.getSource()))));
    }

    private static int create(CommandSourceStack source) {
        ServerPlayer player = source.getPlayer();
        if (player == null) return 0;
        CampData data = CampData.get(source.getLevel());

        UUID uuid = data.createCamp(player.blockPosition());
        source.sendSuccess(() -> Component.literal(
                "Лагерь создан: " + uuid + " «" + data.getName(uuid) + "»"), false);
        return 1;
    }

    private static int info(CommandSourceStack source) {
        ServerPlayer player = source.getPlayer();
        if (player == null) return 0;
        CampData data = CampData.get(source.getLevel());

        BlockPos underPlayer = new BlockPos((int) Math.round(player.getX()), (int) player.getY()-1, (int) player.getZ());
        UUID uuid = data.getCampAt(underPlayer);
        if (uuid == null) {
            source.sendSuccess(() -> Component.literal("Здесь нет лагеря."), false);
            return 0;
        }
        source.sendSuccess(() -> Component.literal(
                "«" + data.getName(uuid) + "» ур." + data.getLevelOf(uuid)
                        + ", радиус " + data.getRadius(uuid)
                        + ", pos " + data.getBlockPos(uuid).toShortString()
                        + ", дружба " + data.getFriendship(uuid, player.getUUID())), false);
        return 1;
    }

    private static int remove(CommandSourceStack source) {
        ServerPlayer player = source.getPlayer();
        if (player == null) return 0;
        CampData data = CampData.get(source.getLevel());

        UUID uuid = data.getCampAt(player.blockPosition());
        if (uuid == null) return 0;
        data.removeCamp(uuid);
        source.sendSuccess(() -> Component.literal("Лагерь удалён: " + uuid), false);
        return 1;
    }

    private static int addFriendship(CommandSourceStack source, int value){
        ServerPlayer serverPlayer =source.getPlayer();
        if (serverPlayer == null) return 0;
        CampData data = CampData.get(source.getLevel());

        BlockPos underPlayer = new BlockPos((int) Math.round(serverPlayer.getX()), (int) serverPlayer.getY()-1, (int) serverPlayer.getZ());
        UUID uuid = data.getCampAt(underPlayer);
        if (uuid == null) {
            source.sendFailure(Component.literal("Рядом нет лагеря"));
            return 0;
        }
        data.addFriendship(uuid, serverPlayer.getUUID(), value);
        source.sendSuccess(() -> Component.literal(((value >= 0) ? "Добавлено: " : "Убрано: ") + value + " очков дружбы"), false);
        return 1;
    }

    private static int setProfession(CommandSourceStack source, int id){
        ServerPlayer serverPlayer = source.getPlayer();
        if (serverPlayer == null) return 0;

        NomadEntity nomad = serverPlayer.level().getNearestEntity(
                NomadEntity.class,
                TargetingConditions.DEFAULT,
                serverPlayer,
                serverPlayer.getX(), serverPlayer.getY(), serverPlayer.getZ(),
                serverPlayer.getBoundingBox().inflate(5.0)
        );

        if (nomad != null){
            nomad.setProfession(Profession.fromId(id));
            source.sendSuccess(() -> Component.literal("Профессия установлена: " + nomad.getProfession()), false);
            return 1;
        }
        source.sendFailure(Component.literal("Рядом нет номадов"));
        return 0;
    }

    private static int nomadInfo(CommandSourceStack source){
        ServerPlayer serverPlayer = source.getPlayer();
        if (serverPlayer == null) return 0;

        NomadEntity nomad = serverPlayer.level().getNearestEntity(
                NomadEntity.class,
                TargetingConditions.DEFAULT,
                serverPlayer,
                serverPlayer.getX(), serverPlayer.getY(), serverPlayer.getZ(),
                serverPlayer.getBoundingBox().inflate(5.0)
        );

        if (nomad != null){
            source.sendSuccess(() -> Component.literal("Номад: " + nomad.getUUID()), false);
            source.sendSuccess(() -> Component.literal("Профессия: " + nomad.getProfession().getId()), false);
            source.sendSuccess(() -> Component.literal("UUID: " + nomad.getCampUUID()), false);
            return 1;
        }
        source.sendFailure(Component.literal("Рядом нет номадов"));
        return 0;
    }

    private static int setBind(CommandSourceStack source){
        ServerPlayer serverPlayer = source.getPlayer();
        if (serverPlayer == null) return 0;
        CampData data = CampData.get(serverPlayer.serverLevel());

        NomadEntity nomad = serverPlayer.level().getNearestEntity(
                NomadEntity.class,
                TargetingConditions.DEFAULT,
                serverPlayer,
                serverPlayer.getX(), serverPlayer.getY(), serverPlayer.getZ(),
                serverPlayer.getBoundingBox().inflate(5.0)
        );

        BlockPos underPlayer = new BlockPos((int) Math.round(serverPlayer.getX()), (int) serverPlayer.getY()-1, (int) serverPlayer.getZ());
        UUID uuid = data.getCampAt(underPlayer);

        if (nomad != null && uuid != null){
            nomad.setCampUUID(uuid);
            source.sendSuccess(() -> Component.literal("Номад привязан: " + nomad.getCampUUID()), false);
            return 1;
        }
        source.sendFailure(Component.literal("Рядом нет номадов"));
        return 0;
    }
}
