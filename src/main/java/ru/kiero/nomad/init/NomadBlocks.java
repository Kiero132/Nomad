package ru.kiero.nomad.init;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import ru.kiero.nomad.Nomad;
import ru.kiero.nomad.blocks.TotemBlock;

public class NomadBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Nomad.MOD_ID);

    public static final RegistryObject<Block> TOTEM = BLOCKS.register("totem", () -> new TotemBlock(BlockBehaviour.Properties.of().strength(3f).sound(SoundType.WOOD)));

    public static void reg(IEventBus bus){
        BLOCKS.register(bus);
    }
}
