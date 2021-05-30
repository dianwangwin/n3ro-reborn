package cn.n3ro.main.font;

import static org.lwjgl.opengl.GL11.GL_LINEAR;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glScaled;
import static org.lwjgl.opengl.GL11.glTexParameteri;

import java.awt.Font;
import java.util.Locale;
import java.util.Random;


import cn.n3ro.main.Client;
import cn.n3ro.main.utils.Colors;
import cn.n3ro.main.utils.RenderUtil;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.StringUtils;

public class GlyphPageFontRenderer {
    public Random fontRandom = new Random();
    /**
     * Current X coordinate at which to draw the next character.
     */
    private float posX;
    /**
     * Current Y coordinate at which to draw the next character.
     */
    private float posY;
    /**
     * Array of RGB triplets defining the 16 standard chat colors followed by 16 darker version of the same colors for
     * drop shadows.
     */
    private int[] colorCode = new int[32];
    /**
     * Used to specify new red value for the current color.
     */
    private float red;
    /**
     * Used to specify new blue value for the current color.
     */
    private float blue;
    /**
     * Used to specify new green value for the current color.
     */
    private float green;
    /**
     * Used to speify new alpha value for the current color.
     */
    private float alpha;

    /**
     * Set if the "n" style (underlined) is active in currently rendering string
     */
    private boolean underlineStyle;
    /**
     * Set if the "m" style (strikethrough) is active in currently rendering string
     */
    private boolean strikethroughStyle;

    private GlyphPage regularGlyphPage;

    public GlyphPageFontRenderer(GlyphPage regularGlyphPage) {
        this.regularGlyphPage = regularGlyphPage;

        for (int i = 0; i < 32; ++i) {
            int j = (i >> 3 & 1) * 85;
            int k = (i >> 2 & 1) * 170 + j;
            int l = (i >> 1 & 1) * 170 + j;
            int i1 = (i & 1) * 170 + j;

            if (i == 6) {
                k += 85;
            }


            if (i >= 16) {
                k /= 4;
                l /= 4;
                i1 /= 4;
            }

            this.colorCode[i] = (k & 255) << 16 | (l & 255) << 8 | i1 & 255;
        }
    }


    public static GlyphPageFontRenderer create(Font font, boolean allChars) {
        GlyphPage regularPage;
        regularPage = new GlyphPage(font, true, true);
        regularPage.generateGlyphPage(allChars ? Client.instance.chars : Client.instance.ascii_chars, allChars);
        return new GlyphPageFontRenderer(regularPage);
    }

    public void drawCenteredString(String text, float x, float y, int color) {
        this.drawString(text, x - (float) (this.getStringWidth(text) / 2), y, color);
    }

    public void drawCenteredStringOutline(String text, float x, float y, int color) {
        this.drawString(text, x - (float) (this.getStringWidth(text) / 2) - 0.5f, y, Colors.BLACK.c);
        this.drawString(text, x - (float) (this.getStringWidth(text) / 2) + 0.5f, y, Colors.BLACK.c);
        this.drawString(text, x - (float) (this.getStringWidth(text) / 2), y - 0.5f, Colors.BLACK.c);
        this.drawString(text, x - (float) (this.getStringWidth(text) / 2), y + 0.5f, Colors.BLACK.c);
        this.drawString(text, x - (float) (this.getStringWidth(text) / 2), y, color);
    }

    public int drawStringWithShadow(String text, float x, float y, int color) {
        this.drawString(StringUtils.stripControlCodes(text), x + 0.8f, y + 0.8f, RenderUtil.reAlpha(Colors.BLACK.c, 0.8f));
        return this.drawString(text, x, y, color);
    }

    public int drawStringWithLightShadow(String text, float x, float y, int color) {
        this.drawString(StringUtils.stripControlCodes(text), x + 0.5f, y + 0.5f, RenderUtil.reAlpha(Colors.BLACK.c, 0.8f));
        return this.drawString(text, x, y, color);
    }

    public int drawString(String text, float x, float y, int color) {
        int i = drawString(text, x, y, color, false);
        return i;
    }

    /**
     * Draws the specified string.
     */
    public int drawString(String text, float x, float y, int color, boolean dropShadow) {
        GlStateManager.enableAlpha();
        this.resetStyles();
        int i;

        if (dropShadow) {
            i = this.renderString(text, x + 1.0F, y + 1.0F, color, true);
            i = Math.max(i, this.renderString(text, x, y, color, false));
        } else {
            i = this.renderString(text, x, y, color, false);
        }

        return i;
    }

    /**
     * Render single line string by setting GL color, current (posX,posY), and calling renderStringAtPos()
     */
    private int renderString(String text, float x, float y, int color, boolean dropShadow) {
        if (text == null) {
            return 0;
        } else {

            if ((color & -67108864) == 0) {
                color |= -16777216;
            }

            if (dropShadow) {
                color = (color & 16579836) >> 2 | color & -16777216;
            }

            this.red = (float) (color >> 16 & 255) / 255.0F;
            this.blue = (float) (color >> 8 & 255) / 255.0F;
            this.green = (float) (color & 255) / 255.0F;
            this.alpha = (float) (color >> 24 & 255) / 255.0F;
            GlStateManager.color(this.red, this.blue, this.green, this.alpha);
            this.posX = x * 2.0f;
            this.posY = y * 2.0f;
            this.renderStringAtPos(text, dropShadow);
            return (int) (this.posX / 4.0f);
        }
    }

    /**
     * Render a single line string at the current (posX,posY) and update posX
     */
    private void renderStringAtPos(String text, boolean shadow) {
        GlyphPage glyphPage = getCurrentGlyphPage();

        glPushMatrix();

        glScaled(0.5, 0.5, 0.5);

        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        GlStateManager.enableTexture2D();

        glyphPage.bindTexture();

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

        for (int i = 0; i < text.length(); ++i) {
            char c0 = text.charAt(i);

            if (c0 == 167 && i + 1 < text.length()) {
                int i1 = "0123456789abcdefklmnor".indexOf(text.toLowerCase(Locale.ENGLISH).charAt(i + 1));

                if (i1 < 16) {
                    this.strikethroughStyle = false;
                    this.underlineStyle = false;

                    if (i1 < 0) {
                        i1 = 15;
                    }

                    if (shadow) {
                        i1 += 16;
                    }

                    int j1 = this.colorCode[i1];
                    /**
                     * Text color of the currently rendering string.
                     */

                    GlStateManager.color((float) (j1 >> 16) / 255.0F, (float) (j1 >> 8 & 255) / 255.0F, (float) (j1 & 255) / 255.0F, this.alpha);
                } else if (i1 == 18) {
                    this.strikethroughStyle = true;
                } else if (i1 == 19) {
                    this.underlineStyle = true;
                } else {
                    this.strikethroughStyle = false;
                    this.underlineStyle = false;

                    GlStateManager.color(this.red, this.blue, this.green, this.alpha);
                }

                ++i;
            } else {
                glyphPage = getCurrentGlyphPage();

                glyphPage.bindTexture();

                float f = glyphPage.drawChar(c0, posX, posY);

                doDraw(f, glyphPage);
            }
        }

        glyphPage.unbindTexture();

        glPopMatrix();
    }

    private void doDraw(float f, GlyphPage glyphPage) {
        if (this.strikethroughStyle) {
            Tessellator tessellator = Tessellator.getInstance();
            WorldRenderer worldrenderer = tessellator.getWorldRenderer();
            GlStateManager.disableTexture2D();
            worldrenderer.begin(7, DefaultVertexFormats.POSITION);
            worldrenderer.pos((double) this.posX, (double) (this.posY + (float) (glyphPage.getMaxFontHeight() / 2)), 0.0D).endVertex();
            worldrenderer.pos((double) (this.posX + f), (double) (this.posY + (float) (glyphPage.getMaxFontHeight() / 2)), 0.0D).endVertex();
            worldrenderer.pos((double) (this.posX + f), (double) (this.posY + (float) (glyphPage.getMaxFontHeight() / 2) - 1.0F), 0.0D).endVertex();
            worldrenderer.pos((double) this.posX, (double) (this.posY + (float) (glyphPage.getMaxFontHeight() / 2) - 1.0F), 0.0D).endVertex();
            tessellator.draw();
            GlStateManager.enableTexture2D();
        }

        if (this.underlineStyle) {
            Tessellator tessellator1 = Tessellator.getInstance();
            WorldRenderer worldrenderer1 = tessellator1.getWorldRenderer();
            GlStateManager.disableTexture2D();
            worldrenderer1.begin(7, DefaultVertexFormats.POSITION);
            int l = this.underlineStyle ? -1 : 0;
            worldrenderer1.pos((double) (this.posX + (float) l), (double) (this.posY + (float) glyphPage.getMaxFontHeight()), 0.0D).endVertex();
            worldrenderer1.pos((double) (this.posX + f), (double) (this.posY + (float) glyphPage.getMaxFontHeight()), 0.0D).endVertex();
            worldrenderer1.pos((double) (this.posX + f), (double) (this.posY + (float) glyphPage.getMaxFontHeight() - 1.0F), 0.0D).endVertex();
            worldrenderer1.pos((double) (this.posX + (float) l), (double) (this.posY + (float) glyphPage.getMaxFontHeight() - 1.0F), 0.0D).endVertex();
            tessellator1.draw();
            GlStateManager.enableTexture2D();
        }

        this.posX += f;
    }


    private GlyphPage getCurrentGlyphPage() {
        return regularGlyphPage;
    }

    /**
     * Reset all style flag fields in the class to false; called at the start of string rendering
     */
    private void resetStyles() {
        this.underlineStyle = false;
        this.strikethroughStyle = false;
    }

    public int getFontHeight() {
        return regularGlyphPage.getMaxFontHeight() / 2;
    }

    public int getStringWidth(String text) {
        if (text == null) {
            return 0;
        }
        int width = 0;

        GlyphPage currentPage;

        int size = text.length();

        boolean on = false;

        for (int i = 0; i < size; i++) {
            char character = text.charAt(i);

            if (character == '\247')
                on = true;
            else if (on && character >= '0' && character <= 'r') {
                int colorIndex = "0123456789abcdefklmnor".indexOf(character);
                i++;
                on = false;
            } else {
                if (on) i--;
                character = text.charAt(i);
                currentPage = getCurrentGlyphPage();
                width += currentPage.getWidth(character) - 8;
            }
        }

        return width / 2;
    }

    /**
     * Trims a string to fit a specified Width.
     */
    public String trimStringToWidth(String text, int width) {
        return this.trimStringToWidth(text, width, false);
    }

    /**
     * Trims a string to a specified width, and will reverse it if par3 is set.
     */
    public String trimStringToWidth(String text, int maxWidth, boolean reverse) {
        StringBuilder stringbuilder = new StringBuilder();

        boolean on = false;

        int j = reverse ? text.length() - 1 : 0;
        int k = reverse ? -1 : 1;
        int width = 0;

        GlyphPage currentPage;

        for (int i = j; i >= 0 && i < text.length() && i < maxWidth; i += k) {
            char character = text.charAt(i);

            if (character == '\247')
                on = true;
            else if (on && character >= '0' && character <= 'r') {
                int colorIndex = "0123456789abcdefklmnor".indexOf(character);
                if (colorIndex < 16) {
                } else if (colorIndex == 17) {
                } else if (colorIndex == 20) {
                } else if (colorIndex == 21) {
                }
                i++;
                on = false;
            } else {
                if (on) i--;

                character = text.charAt(i);

                currentPage = getCurrentGlyphPage();

                width += (currentPage.getWidth(character) - 8) / 2;
            }

            if (i > width) {
                break;
            }

            if (reverse) {
                stringbuilder.insert(0, character);
            } else {
                stringbuilder.append(character);
            }
        }

        return stringbuilder.toString();
    }
}