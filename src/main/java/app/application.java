package app;

import controls.Label;
import io.github.humbleui.jwm.*;
import io.github.humbleui.jwm.Event;
import io.github.humbleui.jwm.skija.EventFrameSkija;
import io.github.humbleui.skija.Canvas;
import io.github.humbleui.skija.Paint;
import io.github.humbleui.skija.Surface;
import misc.CoordinateSystem2i;
import misc.Misc;

import java.io.File;
import java.util.function.Consumer;

import static app.colors.APP_BACKGROUND_COLOR;
import static app.colors.PANEL_BACKGROUND_COLOR;

/**
 * Класс окна приложения
 */
public class application implements Consumer<Event>
{
    /**
     * отступы панелей
     */
    /**
     * Первый заголовок
     */

    public static final int PANEL_PADDING = 5;
    /**
     * радиус скругления элементов
     */
    public static final int C_RAD_IN_PX = 4;
    /**
     * окно
     */
    private final Window window;
    private final Label label;
    /**
     * Конструктор окна приложения
     */
    // конструктор приложения
    public application() {

        // создаём окно
        window = App.makeWindow();
        // задаём обработчиком событий текущий объект
        window.setEventListener(this);
        window.setTitle("Java 2D");
        // задаём размер окна
        window.setWindowSize(900, 900);
// задаём его положение
        window.setWindowPosition(100, 100);
        // задаём иконкy
        label = new Label(window, true, PANEL_BACKGROUND_COLOR, PANEL_PADDING, "Привет, мир!");
        switch (Platform.CURRENT) {
            case WINDOWS -> window.setIcon(new File("src/main/resources/windows.ico"));
            case MACOS -> window.setIcon(new File("src/main/resources/macos.icns"));
        }
        // названия слоёв, которые будем перебирать
        String[] layerNames = new String[]{
                "LayerGLSkija", "LayerRasterSkija"
        };

        // перебираем слои
        for (String layerName : layerNames) {
            String className = "io.github.humbleui.jwm.skija." + layerName;
            try {
                Layer layer = (Layer) Class.forName(className).getDeclaredConstructor().newInstance();
                window.setLayer(layer);
                break;
            } catch (Exception e) {
                System.out.println("Ошибка создания слоя " + className);
            }
        }

        // если окну не присвоен ни один из слоёв
        if (window._layer == null)
            throw new RuntimeException("Нет доступных слоёв для создания");
        // делаем окно видимым
        window.setVisible(true);

    }

    // обработчик событий

    /**
     * метод
     * @param e
     */
    /**
     * Рисование
     *
     * @param canvas низкоуровневый инструмент рисования примитивов от Skija
     * @param height высота окна
     * @param width  ширина окна
     */
    /**
     * Рисование
     *
     * @param canvas   низкоуровневый инструмент рисования примитивов от Skija
     * @param windowCS СК окна
     */
    /**
     * Рисование
     *
     * @param canvas   низкоуровневый инструмент рисования примитивов от Skija
     * @param windowCS СК окна
     */
    /**
     * Рисование
     *
     * @param canvas   низкоуровневый инструмент рисования примитивов от Skija
     * @param windowCS СК окна
     */
    public void paint(Canvas canvas, CoordinateSystem2i windowCS) {
        // запоминаем изменения (пока что там просто заливка цветом)
        canvas.save();
        // очищаем канвас
        canvas.clear(APP_BACKGROUND_COLOR);
        // рисуем заголовок
        // рисуем заголовок в точке [100,100] с шириной и выостой 200
        label.paint(canvas, new CoordinateSystem2i(100, 100, 200, 200));
        // восстанавливаем состояние канваса
        canvas.restore();
    }
    @Override
    public void accept(Event e) {
        if (e instanceof EventWindowClose) {
            // завершаем работу приложения
            App.terminate();
        }else if (e instanceof EventWindowCloseRequest) {
            window.close();
        }
        else if (e instanceof EventFrameSkija ee) {
            // получаем поверхность рисования
            Surface s = ee.getSurface();
            // очищаем её канвас заданным цветом
            paint(s.getCanvas(), new CoordinateSystem2i(s.getWidth(), s.getHeight()));
        }

    }

}
