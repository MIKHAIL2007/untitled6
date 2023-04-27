package app;

import io.github.humbleui.jwm.*;
import io.github.humbleui.jwm.Event;

import java.util.function.Consumer;

/**
 * Класс окна приложения
 */
public class application implements Consumer<Event>
{

    /**
     * окно
     */
    private final Window window;

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
        // делаем окно видимым
        window.setVisible(true);

    }

    // обработчик событий

    /**
     * метод
     * @param e
     */
    @Override
    public void accept(Event e) {
        if (e instanceof EventWindowClose) {
            // завершаем работу приложения
            App.terminate();
        }else if (e instanceof EventWindowCloseRequest) {
            window.close();
        }

    }

}
