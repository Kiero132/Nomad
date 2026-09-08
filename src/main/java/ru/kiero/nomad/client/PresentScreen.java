package ru.kiero.nomad.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import ru.kiero.nomad.Nomad;
import ru.kiero.nomad.api.GuiAPI;
import ru.kiero.nomad.data.CampData;
import ru.kiero.nomad.menu.PresentMenu;

public class PresentScreen extends AbstractContainerScreen<PresentMenu> {

    public static final ResourceLocation RESOURCE_LOCATION = new ResourceLocation(Nomad.MOD_ID, "textures/gui/present.png");
    public static final ResourceLocation BARS = new ResourceLocation(Nomad.MOD_ID, "textures/gui/bars.png");

    public PresentScreen(PresentMenu pMenu, Inventory inv, Component pTitle) {
        super(pMenu, inv, pTitle);
        this.imageWidth = 256;
        this.imageHeight = 256;
    }

    @Override
    protected void init() {
        super.init();
        this.addRenderableWidget(Button.builder(Component.literal(""), this::giveGift)
                .bounds(this.leftPos+123, this.topPos+147, 73, 18)
                .build(button -> new Button(button){
                    @Override
                    protected void renderWidget(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {

                    }
                }));
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

        //Friendship progressbar
        float barWidthTemp = (float) (menu.getFriendship() + 100) /200;
        int barWidth = Math.round(142*barWidthTemp);
        pGuiGraphics.blit(BARS, leftPos+35, topPos+51, 0, 0, barWidth, 12);
        GuiAPI.drawSmallString(pGuiGraphics, this.font, "-100", this.leftPos+31, this.topPos+65, 0x431c10, false, 0.5f);
        GuiAPI.drawSmallString(pGuiGraphics, this.font, "100", this.leftPos+172, this.topPos+65, 0x431c10, false, 0.5f);

        //Level
        String text = "Уровень: " + String.valueOf(menu.getLevelOf());
        GuiAPI.drawSmallString(pGuiGraphics, this.font, text, this.leftPos+(this.imageWidth-this.font.width(text))/2-5, this.topPos+65,0x431c10, false, 0.6f);

        //Exp Progressbar
        barWidthTemp = (float) menu.getExp()/ CampData.expForLevel.get(menu.getLevelOf()-1);
        barWidth = Math.round(40*barWidthTemp);
        pGuiGraphics.blit(BARS, leftPos+22, topPos+52, 0, topPos+242, barWidth, 8);

        //Exp
        text = String.valueOf(menu.getExp()) + "/" + String.valueOf(CampData.expForLevel.get(menu.getLevelOf()-1));
        GuiAPI.drawSmallString(pGuiGraphics, this.font, text, this.leftPos+(this.imageWidth-this.font.width(text))/2-8, this.topPos+85,0x431c10, false, 0.6f);

        text = "Территория: "+menu.getRadius()+" блоков";
        GuiAPI.drawSmallString(pGuiGraphics, this.font, text, this.leftPos+(this.imageWidth-this.font.width(text))/2+3, this.topPos+97,0x431c10, false, 1f);

        //Resources
        GuiAPI.drawSmallString(pGuiGraphics, this.font, "Еда", this.leftPos+50, this.topPos+135,0x431c10, false, 0.5f);
        GuiAPI.drawSmallString(pGuiGraphics, this.font, GuiAPI.normalizeText(menu.getFood()), this.leftPos+59-this.font.width(GuiAPI.normalizeText(menu.getFood())), this.topPos+140,0x431c10, false, 0.5f);

        GuiAPI.drawSmallString(pGuiGraphics, this.font, "Дерево", this.leftPos+76, this.topPos+135,0x431c10, false, 0.5f);
        GuiAPI.drawSmallString(pGuiGraphics, this.font, GuiAPI.normalizeText(menu.getWood()), this.leftPos+90-this.font.width(GuiAPI.normalizeText(menu.getWood())), this.topPos+140,0x431c10, false, 0.5f);

        GuiAPI.drawSmallString(pGuiGraphics, this.font, "Камень", this.leftPos+107, this.topPos+135,0x431c10, false, 0.5f);
        GuiAPI.drawSmallString(pGuiGraphics, this.font, GuiAPI.normalizeText(menu.getStone()), this.leftPos+121-this.font.width(GuiAPI.normalizeText(menu.getStone())), this.topPos+140,0x431c10, false, 0.5f);

        GuiAPI.drawSmallString(pGuiGraphics, this.font, "Кожа", this.leftPos+139, this.topPos+135,0x431c10, false, 0.5f);
        GuiAPI.drawSmallString(pGuiGraphics, this.font, GuiAPI.normalizeText(menu.getLeather()), this.leftPos+150-this.font.width(GuiAPI.normalizeText(menu.getLeather())), this.topPos+140,0x431c10, false, 0.5f);

        GuiAPI.drawSmallString(pGuiGraphics, this.font, "Редкое", this.leftPos+165, this.topPos+135,0x431c10, false, 0.5f);
        GuiAPI.drawSmallString(pGuiGraphics, this.font, GuiAPI.normalizeText(menu.getRare()), this.leftPos+178-this.font.width(GuiAPI.normalizeText(menu.getRare())), this.topPos+140,0x431c10, false, 0.5f);
    }

    @Override
    protected void renderLabels(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY) {

    }

    private void giveGift(Button button){

    }
}
