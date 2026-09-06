package ru.kiero.nomad.client.model;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.resources.ResourceLocation;
import ru.kiero.nomad.Nomad;
import ru.kiero.nomad.entity.NomadEntity;

public class NomadModel extends HumanoidModel<NomadEntity> {

    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(Nomad.MOD_ID, "nomad"), "main");

    public NomadModel(ModelPart pRoot) {
        super(pRoot);
    }

    public static LayerDefinition createBodyLayer(){
        MeshDefinition meshDefenition = HumanoidModel.createMesh(CubeDeformation.NONE, 0f);
        return LayerDefinition.create(meshDefenition, 128, 64);
    }
}
