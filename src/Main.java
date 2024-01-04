import components.MEImage;
import core.MEIconImage;
import core.MEngineLoop;
import components.MEWindow;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        MEWindow win = new MEWindow(500, 400, "Example");
        //win.setBackground();
        win.pack();
        win.show();

        MEIconImage ico = new MEIconImage("MEngine_transp.png");
        ico.applyOnWindow(win.get_window());

        MEngineLoop loop = new MEngineLoop(win.get_window());
        loop.set_background_color(50 , 50, 50);
        //loop.display_new_rect(0, 0);
        loop.display_new_rect(50, 350, 100, 100);

        loop.display_new_rect(100, 350, 100, 100);
        loop.start();

    }
}
