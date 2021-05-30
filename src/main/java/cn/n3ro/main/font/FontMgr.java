package cn.n3ro.main.font;

import java.awt.*;
import java.io.InputStream;

public class FontMgr {



    public GlyphPageFontRenderer simpleton10 = GlyphPageFontRenderer.create(getFont("simpleton.otf", 10), false);
    public GlyphPageFontRenderer simpleton11 = GlyphPageFontRenderer.create(getFont("simpleton.otf", 11), false);
    public GlyphPageFontRenderer simpleton12 = GlyphPageFontRenderer.create(getFont("simpleton.otf", 12), false);
    public GlyphPageFontRenderer simpleton13 = GlyphPageFontRenderer.create(getFont("simpleton.otf", 13), false);
    public GlyphPageFontRenderer simpleton14 = GlyphPageFontRenderer.create(getFont("simpleton.otf", 14), false);
    public GlyphPageFontRenderer simpleton15 = GlyphPageFontRenderer.create(getFont("simpleton.otf", 15), false);
    public GlyphPageFontRenderer simpleton16 = GlyphPageFontRenderer.create(getFont("simpleton.otf", 16), false);
    public GlyphPageFontRenderer simpleton17 = GlyphPageFontRenderer.create(getFont("simpleton.otf", 17), false);
    public GlyphPageFontRenderer simpleton18 = GlyphPageFontRenderer.create(getFont("simpleton.otf", 18), false);
    public GlyphPageFontRenderer simpleton19 = GlyphPageFontRenderer.create(getFont("simpleton.otf", 19), false);
    public GlyphPageFontRenderer simpleton20 = GlyphPageFontRenderer.create(getFont("simpleton.otf", 20), false);
    public GlyphPageFontRenderer simpleton21 = GlyphPageFontRenderer.create(getFont("simpleton.otf", 21), false);
    public GlyphPageFontRenderer simpleton22 = GlyphPageFontRenderer.create(getFont("simpleton.otf", 22), false);
    public GlyphPageFontRenderer simpleton23 = GlyphPageFontRenderer.create(getFont("simpleton.otf", 23), false);
    public GlyphPageFontRenderer simpleton24 = GlyphPageFontRenderer.create(getFont("simpleton.otf", 24), false);
    public GlyphPageFontRenderer simpleton25 = GlyphPageFontRenderer.create(getFont("simpleton.otf", 25), false);
    public GlyphPageFontRenderer simpleton30 = GlyphPageFontRenderer.create(getFont("simpleton.otf", 30), false);
    public GlyphPageFontRenderer simpleton35 = GlyphPageFontRenderer.create(getFont("simpleton.otf", 35), false);
    public GlyphPageFontRenderer simpleton40 = GlyphPageFontRenderer.create(getFont("simpleton.otf", 40), false);
    public GlyphPageFontRenderer simpleton45 = GlyphPageFontRenderer.create(getFont("simpleton.otf", 45), false);
    public GlyphPageFontRenderer simpleton50 = GlyphPageFontRenderer.create(getFont("simpleton.otf", 50), false);
    public GlyphPageFontRenderer simpleton55 = GlyphPageFontRenderer.create(getFont("simpleton.otf", 55), false);
    public GlyphPageFontRenderer simpleton60 = GlyphPageFontRenderer.create(getFont("simpleton.otf", 60), false);
    public GlyphPageFontRenderer simpleton65 = GlyphPageFontRenderer.create(getFont("simpleton.otf", 65), false);
    public GlyphPageFontRenderer simpleton70 = GlyphPageFontRenderer.create(getFont("simpleton.otf", 70), false);


    public GlyphPageFontRenderer stylesicons10 = GlyphPageFontRenderer.create(getFont("stylesicons.ttf", 10), false);
    public GlyphPageFontRenderer stylesicons11 = GlyphPageFontRenderer.create(getFont("stylesicons.ttf", 11), false);
    public GlyphPageFontRenderer stylesicons12 = GlyphPageFontRenderer.create(getFont("stylesicons.ttf", 12), false);
    public GlyphPageFontRenderer stylesicons13 = GlyphPageFontRenderer.create(getFont("stylesicons.ttf", 13), false);
    public GlyphPageFontRenderer stylesicons14 = GlyphPageFontRenderer.create(getFont("stylesicons.ttf", 14), false);
    public GlyphPageFontRenderer stylesicons15 = GlyphPageFontRenderer.create(getFont("stylesicons.ttf", 15), false);
    public GlyphPageFontRenderer stylesicons16 = GlyphPageFontRenderer.create(getFont("stylesicons.ttf", 16), false);
    public GlyphPageFontRenderer stylesicons17 = GlyphPageFontRenderer.create(getFont("stylesicons.ttf", 17), false);
    public GlyphPageFontRenderer stylesicons18 = GlyphPageFontRenderer.create(getFont("stylesicons.ttf", 18), false);
    public GlyphPageFontRenderer stylesicons19 = GlyphPageFontRenderer.create(getFont("stylesicons.ttf", 19), false);
    public GlyphPageFontRenderer stylesicons20 = GlyphPageFontRenderer.create(getFont("stylesicons.ttf", 20), false);
    public GlyphPageFontRenderer stylesicons21 = GlyphPageFontRenderer.create(getFont("stylesicons.ttf", 21), false);
    public GlyphPageFontRenderer stylesicons22 = GlyphPageFontRenderer.create(getFont("stylesicons.ttf", 22), false);
    public GlyphPageFontRenderer stylesicons23 = GlyphPageFontRenderer.create(getFont("stylesicons.ttf", 23), false);
    public GlyphPageFontRenderer stylesicons24 = GlyphPageFontRenderer.create(getFont("stylesicons.ttf", 24), false);
    public GlyphPageFontRenderer stylesicons25 = GlyphPageFontRenderer.create(getFont("stylesicons.ttf", 25), false);
    public GlyphPageFontRenderer stylesicons30 = GlyphPageFontRenderer.create(getFont("stylesicons.ttf", 30), false);
    public GlyphPageFontRenderer stylesicons35 = GlyphPageFontRenderer.create(getFont("stylesicons.ttf", 35), false);
    public GlyphPageFontRenderer stylesicons40 = GlyphPageFontRenderer.create(getFont("stylesicons.ttf", 40), false);
    public GlyphPageFontRenderer stylesicons45 = GlyphPageFontRenderer.create(getFont("stylesicons.ttf", 45), false);
    public GlyphPageFontRenderer stylesicons50 = GlyphPageFontRenderer.create(getFont("stylesicons.ttf", 50), false);
    public GlyphPageFontRenderer stylesicons55 = GlyphPageFontRenderer.create(getFont("stylesicons.ttf", 55), false);
    public GlyphPageFontRenderer stylesicons60 = GlyphPageFontRenderer.create(getFont("stylesicons.ttf", 60), false);
    public GlyphPageFontRenderer stylesicons65 = GlyphPageFontRenderer.create(getFont("stylesicons.ttf", 65), false);
    public GlyphPageFontRenderer stylesicons70 = GlyphPageFontRenderer.create(getFont("stylesicons.ttf", 70), false);

    public GlyphPageFontRenderer roboto10 = GlyphPageFontRenderer.create(getFont("roboto-regular.ttf", 10), false);
    public GlyphPageFontRenderer roboto11 = GlyphPageFontRenderer.create(getFont("roboto-regular.ttf", 11), false);
    public GlyphPageFontRenderer roboto12 = GlyphPageFontRenderer.create(getFont("roboto-regular.ttf", 12), false);
    public GlyphPageFontRenderer roboto13 = GlyphPageFontRenderer.create(getFont("roboto-regular.ttf", 13), false);
    public GlyphPageFontRenderer roboto14 = GlyphPageFontRenderer.create(getFont("roboto-regular.ttf", 14), false);
    public GlyphPageFontRenderer roboto15 = GlyphPageFontRenderer.create(getFont("roboto-regular.ttf", 15), false);
    public GlyphPageFontRenderer roboto16 = GlyphPageFontRenderer.create(getFont("roboto-regular.ttf", 16), false);
    public GlyphPageFontRenderer roboto17 = GlyphPageFontRenderer.create(getFont("roboto-regular.ttf", 17), false);
    public GlyphPageFontRenderer roboto18 = GlyphPageFontRenderer.create(getFont("roboto-regular.ttf", 18), false);
    public GlyphPageFontRenderer roboto19 = GlyphPageFontRenderer.create(getFont("roboto-regular.ttf", 19), false);
    public GlyphPageFontRenderer roboto20 = GlyphPageFontRenderer.create(getFont("roboto-regular.ttf", 20), false);
    public GlyphPageFontRenderer roboto21 = GlyphPageFontRenderer.create(getFont("roboto-regular.ttf", 21), false);
    public GlyphPageFontRenderer roboto22 = GlyphPageFontRenderer.create(getFont("roboto-regular.ttf", 22), false);
    public GlyphPageFontRenderer roboto23 = GlyphPageFontRenderer.create(getFont("roboto-regular.ttf", 23), false);
    public GlyphPageFontRenderer roboto24 = GlyphPageFontRenderer.create(getFont("roboto-regular.ttf", 24), false);
    public GlyphPageFontRenderer roboto25 = GlyphPageFontRenderer.create(getFont("roboto-regular.ttf", 25), false);
    public GlyphPageFontRenderer roboto30 = GlyphPageFontRenderer.create(getFont("roboto-regular.ttf", 30), false);
    public GlyphPageFontRenderer roboto35 = GlyphPageFontRenderer.create(getFont("roboto-regular.ttf", 35), false);
    public GlyphPageFontRenderer roboto40 = GlyphPageFontRenderer.create(getFont("roboto-regular.ttf", 40), false);
    public GlyphPageFontRenderer roboto45 = GlyphPageFontRenderer.create(getFont("roboto-regular.ttf", 45), false);
    public GlyphPageFontRenderer roboto50 = GlyphPageFontRenderer.create(getFont("roboto-regular.ttf", 50), false);
    public GlyphPageFontRenderer roboto55 = GlyphPageFontRenderer.create(getFont("roboto-regular.ttf", 55), false);
    public GlyphPageFontRenderer roboto60 = GlyphPageFontRenderer.create(getFont("roboto-regular.ttf", 60), false);
    public GlyphPageFontRenderer roboto65 = GlyphPageFontRenderer.create(getFont("roboto-regular.ttf", 65), false);
    public GlyphPageFontRenderer roboto70 = GlyphPageFontRenderer.create(getFont("roboto-regular.ttf", 70), false);


    public Font getFont(String name, int size) {
        Font font;
        try {
            InputStream is = FontMgr.class.getResourceAsStream("/assets/fonts/" + name);
            font = Font.createFont(0, is);
            font = font.deriveFont(Font.PLAIN, size);
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Error loading font " + name);
            font = new Font("Arial", Font.PLAIN, size);
        }
        return font;
    }

}
