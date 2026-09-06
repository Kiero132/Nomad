package ru.kiero.nomad;

import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import ru.kiero.nomad.client.NomadRenderer;
import ru.kiero.nomad.client.model.NomadModel;
import ru.kiero.nomad.entity.NomadEntity;
import ru.kiero.nomad.init.*;
import ru.kiero.nomad.networking.NomadNetworking;

@Mod("nomad")
public class Nomad {

    public static final String MOD_ID = "nomad";

    public Nomad() {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        modBus.addListener(this::clientSetup);
        modBus.addListener(this::commonSetup);
        modBus.addListener(this::regLayerDefenitions);
        modBus.addListener(this::regRenderers);
        modBus.addListener(this::entityAttributes);

        NomadBlocks.reg(modBus);
        NomadItems.reg(modBus);
        NomadCreativeTab.reg(modBus);
        NomadBlockEntities.reg(modBus);
        NomadMenuTypes.reg(modBus);
        NomadEntities.reg(modBus);
    }

    private void clientSetup(FMLClientSetupEvent event){

    }

    private void commonSetup(FMLCommonSetupEvent event){
        NomadNetworking.reg();
    }

    private void regLayerDefenitions(EntityRenderersEvent.RegisterLayerDefinitions event){
        event.registerLayerDefinition(NomadModel.LAYER_LOCATION, NomadModel::createBodyLayer);
    }

    private void regRenderers(EntityRenderersEvent.RegisterRenderers event){
        event.registerEntityRenderer(NomadEntities.NOMAD.get(), NomadRenderer::new);
    }

    private void entityAttributes(EntityAttributeCreationEvent event){
        event.put(NomadEntities.NOMAD.get(), NomadEntity.createAttributes().build());
    }
}
