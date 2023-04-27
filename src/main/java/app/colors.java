package app;

import misc.Misc;

/**
 * Класс цветов
 */
public class colors {
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