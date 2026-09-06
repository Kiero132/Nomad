package ru.kiero.nomad.init;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import ru.kiero.nomad.Nomad;
import ru.kiero.nomad.entity.NomadEntity;

public class NomadEntities {

    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Nomad.MOD_ID);

    public static final RegistryObject<EntityType<NomadEntity>> NOMAD = ENTITIES.register("nomad",
            () -> EntityType.Builder.of(NomadEntity::new, MobCategory.CREATURE)
                    .sized(0.6f, 1.8f)
                    .build("nomad"));

    public static void reg(IEventBus bus){
        ENTITIES.register(bus);
    }
}
