package app;

import misc.Misc;

/**
 * Класс цветов
 */
public class colors {

    /**
     * Цвет текста заголовка
     */
    public static final int LABEL_TEXT_COLOR = Misc.getColor(64, 255, 255, 255);
    /**
     * цвет подложки панелей
     */
    public static final int PANEL_BACKGROUND_COLOR = Misc.getColor(32, 0, 0, 0);

    /**
     * цвет фона
     */
    public static final int APP_BACKGROUND_COLOR = Misc.getColor(255, 38, 70, 83);

    /**
     * Запрещённый конструктор
     */
    private colors() {
        throw new AssertionError("Вызов этого конструктора запрещён");
    }
}