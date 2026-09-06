package ru.kiero.nomad.init;

import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import ru.kiero.nomad.Nomad;

public class NomadMenuTypes {

    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, Nomad.MOD_ID);

    //public static final RegistryObject<MenuType<TotemMainMenu>> TOTEM_MAIN = MENUS.register("totem_main_menu", () -> IForgeMenuType.create((windowId, inv, buf) -> new TotemMainMenu(windowId, inv, buf)));

    public static void reg(IEventBus bus){
        MENUS.register(bus);
    }
}
