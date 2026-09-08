package ru.kiero.nomad.api;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;

public class GuiAPI {

    public static void drawSmallString(GuiGraphics guiGraphics, Font font, String text, int x, int y, int color, boolean shadow, float scale){
        guiGraphics.pose().pushPose();
        guiGraphics.pose().scale(scale, scale, 1.0f);

        guiGraphics.drawString(font, text, x/scale, y/scale, color, shadow);

        guiGraphics.pose().popPose();
    }

    public static String normalizeText(int value){
        if (value < 1000){
            return String.valueOf(value);
        }else{
            return String.valueOf(value/1000)+"k";
        }
    }
}
