package ru.kiero.nomad.init;

import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import ru.kiero.nomad.Nomad;
import ru.kiero.nomad.menu.PresentMenu;

public class NomadMenuTypes {

    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, Nomad.MOD_ID);

    public static final RegistryObject<MenuType<PresentMenu>> PRESENT_MENU = MENUS.register("present_menu", () -> IForgeMenuType.create((windowId, inv, buf) -> new PresentMenu(windowId, inv, buf)));

    public static void reg(IEventBus bus){
        MENUS.register(bus);
    }
}
