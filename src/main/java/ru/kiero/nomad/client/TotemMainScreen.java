package ru.kiero.nomad.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import ru.kiero.nomad.Nomad;
import ru.kiero.nomad.data.CampData;
import ru.kiero.nomad.entity.RelationStage;

public class TotemMainScreen extends Screen {

    private final int imageWidth;
    private final int imageHeight;
    private int leftPos;
    private int topPos;

    private final String lable;
    private final int levelOf;
    private final int exp;
    private final int friendship;
    private final int radius;
    private final int hasShaman;

    private final int food;
    private final int wood;
    private final int stone;
    private final int leather;
    private final int rare;

    public static final ResourceLocation BG = new ResourceLocation(Nomad.MOD_ID, "textures/gui/totem.png");

    public TotemMainScreen(Component pTitle, String lable, int levelOf, int exp, int friendship, int radius, int food, int wood, int stone, int leather, int rare, int hasShaman) {
        super(pTitle);
        this.imageWidth = 132;
        this.imageHeight = 233;

        this.lable = lable;
        this.levelOf = levelOf;
        this.exp = exp;
        this.friendship = friendship;
        this.radius = radius;
        this.hasShaman = hasShaman;

        this.food = food;
        this.wood = wood;
        this.stone = stone;
        this.leather = leather;
        this.rare = rare;
    }

    @Override
    protected void init() {
        super.init();
        //this.leftPos = (this.width-this.imageWidth)/2-this.imageWidth/2;
        this.leftPos = width/2-imageWidth/2;
        this.topPos = (this.height-imageHeight)/2;
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        renderBackground(pGuiGraphics);
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
    }

    @Override
    public void renderBackground(GuiGraphics pGuiGraphics) {
        super.renderBackground(pGuiGraphics);
        RenderSystem.setShaderTexture(0, BG);
        pGuiGraphics.blit(BG, leftPos, topPos, 0, 0, this.imageWidth, this.imageHeight);


        String text = "Племя " + lable;
        drawSmallString(pGuiGraphics, this.font, text, this.leftPos+(this.imageWidth-this.font.width(text))/2+19, this.topPos+18,0xf5f0e8, false, 0.7f);

        text = "Уровень лагеря: " + String.valueOf(levelOf);
        drawSmallString(pGuiGraphics, this.font, text, this.leftPos+(this.imageWidth-this.font.width(text))/2+32, this.topPos+32, 0x35120a, false, 0.4f);

        text = "Отношение к вам: " + String.valueOf(friendship);
        drawSmallString(pGuiGraphics, this.font, text, this.leftPos+(this.imageWidth-this.font.width(text))/2+8, this.topPos+45, 0x35120a, false, 0.8f);

        //Friendship progressbar
        float barWidthTemp = (float) (friendship + 100) /200;
        int barWidth = Math.round(84*barWidthTemp);
        pGuiGraphics.blit(BG, leftPos+22, topPos+52, 0, imageHeight+1, barWidth, 8);
        drawSmallString(pGuiGraphics, this.font, "-100", this.leftPos+15, this.topPos+62, 0x431c10, false, 0.5f);
        drawSmallString(pGuiGraphics, this.font, "100", this.leftPos+100, this.topPos+62, 0x431c10, false, 0.5f);

        text = RelationStage.of(friendship).toString();
        drawSmallString(pGuiGraphics, this.font, RelationStage.of(friendship).toString(), this.leftPos+(this.imageWidth-this.font.width(text))/2+10, this.topPos+62, 0x431c10, false, 0.5f);

        //Level
        text = "Уровень: " + String.valueOf(levelOf);
        drawSmallString(pGuiGraphics, this.font, text, this.leftPos+(this.imageWidth-this.font.width(text))/2+10, this.topPos+72,0x431c10, false, 0.7f);

        //Exp Progressbar
        barWidthTemp = (float) exp/CampData.expForLevel.get(levelOf-1);
        barWidth = Math.round(40*barWidthTemp);
        pGuiGraphics.blit(BG, leftPos+22, topPos+52, 0, topPos+242, barWidth, 8);

        //Exp
        text = String.valueOf(exp) + "/" + String.valueOf(CampData.expForLevel.get(levelOf-1));
        drawSmallString(pGuiGraphics, this.font, text, this.leftPos+(this.imageWidth-this.font.width(text))/2+10, this.topPos+85,0x431c10, false, 0.5f);

        //TODO: Citizen count

        text = "Территория: "+radius+" блоков";
        drawSmallString(pGuiGraphics, this.font, text, this.leftPos+(this.imageWidth-this.font.width(text))/2+38, this.topPos+114,0x431c10, false, 0.65f);

        //Resources
        drawSmallString(pGuiGraphics, this.font, "Еда", this.leftPos+24, this.topPos+140,0x431c10, false, 0.5f);
        drawSmallString(pGuiGraphics, this.font, normalizeText(food), this.leftPos+33-this.font.width(normalizeText(food)), this.topPos+145,0x431c10, false, 0.5f);

        drawSmallString(pGuiGraphics, this.font, "Дерево", this.leftPos+40, this.topPos+140,0x431c10, false, 0.5f);
        drawSmallString(pGuiGraphics, this.font, normalizeText(wood), this.leftPos+54-this.font.width(normalizeText(wood)), this.topPos+145,0x431c10, false, 0.5f);

        drawSmallString(pGuiGraphics, this.font, "Камень", this.leftPos+61, this.topPos+140,0x431c10, false, 0.5f);
        drawSmallString(pGuiGraphics, this.font, normalizeText(stone), this.leftPos+74-this.font.width(normalizeText(stone)), this.topPos+145,0x431c10, false, 0.5f);

        drawSmallString(pGuiGraphics, this.font, "Кожа", this.leftPos+83, this.topPos+140,0x431c10, false, 0.5f);
        drawSmallString(pGuiGraphics, this.font, normalizeText(leather), this.leftPos+94-this.font.width(normalizeText(leather)), this.topPos+145,0x431c10, false, 0.5f);

        drawSmallString(pGuiGraphics, this.font, "Редкое", this.leftPos+100, this.topPos+140,0x431c10, false, 0.5f);
        drawSmallString(pGuiGraphics, this.font, normalizeText(rare), this.leftPos+113-this.font.width(normalizeText(rare)), this.topPos+145,0x431c10, false, 0.5f);

    }

    private void drawSmallString(GuiGraphics guiGraphics, Font font, String text, int x, int y, int color, boolean shadow, float scale){
        guiGraphics.pose().pushPose();
        guiGraphics.pose().scale(scale, scale, 1.0f);

        guiGraphics.drawString(font, text, x/scale, y/scale, color, shadow);

        guiGraphics.pose().popPose();
    }

    public String normalizeText(int value){
        if (value < 1000){
            return String.valueOf(value);
        }else{
            return String.valueOf(value/1000)+"k";
        }
    }
}
