package org.rubenrr.walkeitor.menu.building;

import android.util.Log;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.rubenrr.walkeitor.config.FontConfig;
import org.rubenrr.walkeitor.config.StatusConfig;
import org.rubenrr.walkeitor.element.building.Building;
import org.rubenrr.walkeitor.element.building.Mine;
import org.rubenrr.walkeitor.element.building.Refinery;
import org.rubenrr.walkeitor.manager.FontLoadManager;
import org.rubenrr.walkeitor.manager.GameManager;
import org.rubenrr.walkeitor.manager.SceneManager;
import org.rubenrr.walkeitor.menu.MenuActions;
import org.rubenrr.walkeitor.menu.MenuBase;
import org.rubenrr.walkeitor.menu.MenuOption;

/**
 * User: Ruben Rubio Rey
 * Date: 14/04/13
 * Time: 6:50 PM
 */
public class OilMineMenu extends BuildableMenuBase {

    @Override
    public void display() {
        // no implementation yet
    }

    /**
     * Accept the location and build the mine
     */
    void displayAcceptStartConstruction() {
        MenuOption option1 = new MenuOption("Start construction");
        this.addMenuOption(option1, new StartConstructionOilMineAction());
    }

    class StartConstructionOilMineAction implements MenuActions {
        public void execute(float pX, float pY) {
            //construction started
            OilMineMenu.this.clear();
            Mine mine = (Mine)OilMineMenu.this.getSprite();

            // we mark the construction as finished
            mine.constructionFinish();
        }
    }

}
