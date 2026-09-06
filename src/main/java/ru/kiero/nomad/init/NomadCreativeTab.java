package ru.kiero.nomad.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import ru.kiero.nomad.Nomad;

public class NomadCreativeTab {

    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Nomad.MOD_ID);

    public static final RegistryObject<CreativeModeTab> NOMAD_TAB = TABS.register("nomad_tab", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.nomad_tab"))
            .icon(() -> new ItemStack(NomadItems.TOTEM.get()))
            .displayItems((pParameters, pOutput) -> pOutput.accept(NomadItems.TOTEM.get())).build());

    public static void reg(IEventBus bus){
        TABS.register(bus);
    }
}
