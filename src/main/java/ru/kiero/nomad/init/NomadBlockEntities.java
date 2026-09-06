package ru.kiero.nomad.init;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import ru.kiero.nomad.Nomad;
import ru.kiero.nomad.blocks.TotemBlockEntity;

public class NomadBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Nomad.MOD_ID);

    public static final RegistryObject<BlockEntityType<TotemBlockEntity>> TOTEM = BLOCK_ENTITIES.register("totem", () -> BlockEntityType.Builder.of(TotemBlockEntity::new, NomadBlocks.TOTEM.get()).build(null));

    public static void reg(IEventBus bus){
        BLOCK_ENTITIES.register(bus);
    }
}
