package ru.kiero.nomad.client;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import ru.kiero.nomad.Nomad;
import ru.kiero.nomad.client.model.NomadModel;
import ru.kiero.nomad.entity.NomadEntity;
import ru.kiero.nomad.entity.Profession;

public class NomadRenderer extends MobRenderer<NomadEntity, NomadModel> {

    public NomadRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new NomadModel(pContext.bakeLayer(NomadModel.LAYER_LOCATION)), 0.5f);
        this.addLayer(new ItemInHandLayer<>(this, pContext.getItemInHandRenderer()));
    }
    @Override
    public ResourceLocation getTextureLocation(NomadEntity pEntity) {
        if (pEntity.getProfession() == Profession.HUNTER) return new ResourceLocation(Nomad.MOD_ID, "textures/entity/hunter.png");
        if (pEntity.getProfession() == Profession.SHAMAN) return new ResourceLocation(Nomad.MOD_ID, "textures/entity/shaman.png");
        if (pEntity.getProfession() == Profession.TRADER) return new ResourceLocation(Nomad.MOD_ID, "textures/entity/trader.png");
        return new ResourceLocation(Nomad.MOD_ID, "textures/entity/nomad.png");
    }
}
