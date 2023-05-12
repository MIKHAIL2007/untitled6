package app;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.humbleui.skija.Canvas;
import io.github.humbleui.skija.Paint;
import io.github.humbleui.skija.RRect;
import io.github.humbleui.skija.Rect;
import misc.*;

import static app.application.task;

/**
 * Класс прямой
 **/

public class Corner {
    //Первая точка
    public Vector2d a;
    //Вторая точка
    public Vector2d b;
    public Vector2d c;
    public final CoordinateSystem2d ownCS;;
    @JsonCreator
    public Corner(@JsonProperty("a") Vector2d a, @JsonProperty("b") Vector2d b, @JsonProperty("c") Vector2d c, @JsonProperty("ownCS") CoordinateSystem2d ownCS) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.ownCS = ownCS;
    }
    public int getColor() {
        return Misc.getColor(0xCC, 0x00, 0x00, 0xFF);
    }
    void renderCorner(Canvas canvas, CoordinateSystem2i windowCS) {   // добавляем угол
        Vector2i pointA =  windowCS.getCoords(a, ownCS);
        Vector2i pointB =  windowCS.getCoords(b, ownCS);
        Vector2i pointC =  windowCS.getCoords(c, ownCS);

        // определяем вектор смещения
        Vector2i AB = Vector2i.subtract(pointB, pointA);
        Vector2i AC = Vector2i.subtract(pointC, pointA);

        // получаем максимальную длину отрезка на экране, как длину диагонали экрана
        int maxDistance = (int) windowCS.getSize().length();
        // получаем новые точки для рисования, которые гарантируют, что линия
        // будет нарисована до границ экрана
        Vector2i renderPointB = Vector2i.sum(pointA, Vector2i.mult(AB, maxDistance));
        Vector2i renderPointC = Vector2i.sum(pointA, Vector2i.mult(AC, maxDistance));
        try (Paint p = new Paint()) {

            canvas.drawLine(pointA.x, pointA.y, renderPointB.x, renderPointB.y, p);
            canvas.drawLine(pointA.x, pointA.y, renderPointC.x, renderPointC.y, p);

            // сохраняем цвет рисования
            int paintColor = p.getColor();
            // задаём красный цвет
            p.setColor(Misc.getColor(200, 255, 0, 0));

            // рисуем опорные точки
            canvas.drawRRect(RRect.makeXYWH(pointA.x - 4, pointA.y - 4, 8, 8, 4), p);
            canvas.drawRRect(RRect.makeXYWH(pointB.x - 4, pointB.y - 4, 8, 8, 4), p);
            canvas.drawRRect(RRect.makeXYWH(pointC.x - 4, pointC.y - 4, 8, 8, 4), p);

            // восстанавливаем исходный цвет рисования
            p.setColor(paintColor);
        }
            // рисуем его стороны


    }
    /*public Point cross(Line line){
        double a1 = b.y - a.y;
        double b1 = a.x - b.x;
        double c1 = a1*(a.x) + b1*(a.y);

        double a2 = line.b.y - line.a.y;
        double b2 = line.a.x - line.b.x;
        double c2 = a2*(line.a.x)+ b2*(line.a.y);

        double determinant = a1*b2 - a2*b1;

        if (determinant == 0)
        {
            return null;
        }
        else
        {
            double x = (b2*c1 - b1*c2)/determinant;
            double y = (a1*c2 - a2*c1)/determinant;
            return new Point(new Vector2d(x, y));
        }


    }
*/
}