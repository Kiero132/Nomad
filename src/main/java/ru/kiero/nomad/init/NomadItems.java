package ru.kiero.nomad.init;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import ru.kiero.nomad.Nomad;

public class NomadItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Nomad.MOD_ID);

    public static final RegistryObject<Item> TOTEM = ITEMS.register("totem", () -> new BlockItem(NomadBlocks.TOTEM.get(), new Item.Properties()));

    public static void reg(IEventBus bus){
        ITEMS.register(bus);
    }
}
