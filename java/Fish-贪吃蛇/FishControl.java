package Fish;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class FishControl  implements KeyListener{
    FishModel model;

    public FishControl(FishModel model){
        this.model = model;
    }

    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (model.running){              
            switch (keyCode) {
                case KeyEvent.VK_UP:
                    model.changeDirection(FishModel.UP);
                    break;
                case KeyEvent.VK_DOWN:
                    model.changeDirection(FishModel.DOWN);
                    break;
                case KeyEvent.VK_LEFT:
                    model.changeDirection(FishModel.LEFT);
                    break;
                case KeyEvent.VK_RIGHT:
                    model.changeDirection(FishModel.RIGHT);
                    break;
                default:
            }

            if (keyCode == KeyEvent.VK_R ||
                    keyCode == KeyEvent.VK_S ||
                    keyCode == KeyEvent.VK_ENTER) {
                model.reset();
            }
        }
        }

        public void keyReleased(KeyEvent e) {
        }

        public void keyTyped(KeyEvent e) {
        }
    }
