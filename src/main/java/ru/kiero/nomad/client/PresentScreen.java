package ru.kiero.nomad.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import ru.kiero.nomad.Nomad;
import ru.kiero.nomad.menu.PresentMenu;

public class PresentScreen extends AbstractContainerScreen<PresentMenu> {

    public static final ResourceLocation RESOURCE_LOCATION = new ResourceLocation(Nomad.MOD_ID, "textures/gui/present.png");

    public PresentScreen(PresentMenu pMenu, Inventory inv, Component pTitle) {
        super(pMenu, inv, pTitle);
        this.imageWidth = 256;
        this.imageHeight = 256;
    }

    @Override
    protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {

    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        renderBackground(pGuiGraphics);
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
    }

    @Override
    public void renderBackground(GuiGraphics pGuiGraphics) {
        super.renderBackground(pGuiGraphics);
        RenderSystem.setShaderTexture(0, RESOURCE_LOCATION);
        pGuiGraphics.blit(RESOURCE_LOCATION, leftPos, topPos, 0, 0, this.imageWidth, this.imageHeight);
    }

    @Override
    protected void renderLabels(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY) {

    }
}
