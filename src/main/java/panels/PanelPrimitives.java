package panels;

import app.Primitive;
import io.github.humbleui.jwm.Event;
import io.github.humbleui.jwm.EventKey;
import io.github.humbleui.jwm.Window;
import io.github.humbleui.skija.Canvas;
import io.github.humbleui.skija.Paint;
import io.github.humbleui.skija.RRect;
import misc.CoordinateSystem2i;
import misc.Misc;
import misc.Vector2i;

import java.util.ArrayList;

/**
 * Панель игры
 */
public class PanelPrimitives extends Panel {
    /**
     * Список примитивов
     */
    private final ArrayList<Primitive> primitives = new ArrayList<>();
    /**
     * Положение текущего примитива
     */
    private int primitivePos;

    /**
     * Конструктор панели
     *
     * @param window          окно
     * @param drawBG          нужно ли рисовать подложку
     * @param backgroundColor цвет фона
     * @param padding         отступы
     */
    public PanelPrimitives(Window window, boolean drawBG, int backgroundColor, int padding) {
        super(window, drawBG, backgroundColor, padding);
        // добавляем точку
        primitives.add(((canvas, windowCS, p) -> canvas.drawRRect(
                RRect.makeXYWH(200, 200, 4, 4, 2), p)
        ));
        // добавляем угол
        primitives.add((canvas, windowCS, p) -> {
            // вершина угла
            Vector2i pointA = new Vector2i(400, 400);
            // опорные точки
            Vector2i pointB = new Vector2i(100, 200);
            Vector2i pointC = new Vector2i(500, 300);

            // определяем вектор смещения
            Vector2i AB = Vector2i.subtract(pointB, pointA);
            Vector2i AC = Vector2i.subtract(pointC, pointA);

            // получаем максимальную длину отрезка на экране, как длину диагонали экрана
            int maxDistance = (int) windowCS.getSize().length();
            // получаем новые точки для рисования, которые гарантируют, что линия
            // будет нарисована до границ экрана
            Vector2i renderPointB = Vector2i.sum(pointA, Vector2i.mult(AB, maxDistance));
            Vector2i renderPointC = Vector2i.sum(pointA, Vector2i.mult(AC, maxDistance));

            // рисуем его стороны
            canvas.drawLine(pointA.x, pointA.y, renderPointB.x, renderPointB.y, p);
            canvas.drawLine(pointA.x, pointA.y, renderPointC.x, renderPointC.y, p);

            // сохраняем цвет рисования
            int paintColor = p.getColor();
            // задаём красный цвет
            p.setColor(Misc.getColor(200, 255, 0, 0));

            // рисуем опорные точки
            canvas.drawRRect(RRect.makeXYWH(pointA.x - 1, pointA.y - 1, 2, 2, 1), p);
            canvas.drawRRect(RRect.makeXYWH(pointB.x - 1, pointB.y - 1, 2, 2, 1), p);
            canvas.drawRRect(RRect.makeXYWH(pointC.x - 1, pointC.y - 1, 2, 2, 1), p);

            // восстанавливаем исходный цвет рисования
            p.setColor(paintColor);
        });
        primitivePos = 0;
    }

    /**
     * Обработчик событий
     *
     * @param e событие
     */
    @Override
    public void accept(Event e) {
        // кнопки клавиатуры
        if (e instanceof EventKey eventKey) {
            // кнопка нажата с Ctrl
            if (eventKey.isPressed()) {
                switch (eventKey.getKey()) {
                    // Следующий примитив
                    case LEFT -> primitivePos = (primitivePos - 1 + primitives.size()) % primitives.size();
                    // Предыдущий примитив
                    case RIGHT -> primitivePos = (primitivePos + 1) % primitives.size();
                }
            }
        }
    }


    /**
     * Метод под рисование в конкретной реализации
     *
     * @param canvas   область рисования
     * @param windowCS СК окна
     */
    @Override
    public void paintImpl(Canvas canvas, CoordinateSystem2i windowCS) {
        // создаём перо
        Paint p = new Paint();
        // задаём цвет
        p.setColor(Misc.getColor(200, 255, 255, 255));
        // задаём толщину пера
        p.setStrokeWidth(5);
        // рисуем текущий примитив
        primitives.get(primitivePos).render(canvas, windowCS, p);
    }


}
