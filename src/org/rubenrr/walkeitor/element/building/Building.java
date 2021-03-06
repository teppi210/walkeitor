package org.rubenrr.walkeitor.element.building;

import android.util.Log;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.tmx.TMXTile;
import org.andengine.input.touch.TouchEvent;
import org.rubenrr.walkeitor.config.ElementConfig;
import org.rubenrr.walkeitor.config.status.StatusConfig;
import org.rubenrr.walkeitor.manager.util.Storage;
import org.rubenrr.walkeitor.manager.GameManager;
import org.rubenrr.walkeitor.manager.SceneManager;
import org.rubenrr.walkeitor.manager.TextureRegionManager;
import org.rubenrr.walkeitor.manager.util.OccupiedTiles;
import org.rubenrr.walkeitor.manager.util.SpriteAttachable;
import org.rubenrr.walkeitor.menu.MenuStrategy;
import org.rubenrr.walkeitor.production.ProductionStrategy;
import org.rubenrr.walkeitor.util.TileLocatable;

/**
 * User: Ruben Rubio Rey
 * Date: 30/03/13
 * Time: 1:07 PM
 */
public abstract class Building extends Sprite implements SpriteAttachable, TileLocatable {

    private MenuStrategy menu;
    private ProductionStrategy production;
    private ElementConfig elementConfig;
    private StatusConfig statusConfig;
    private Storage storage; // all buildings can store something
    TMXTile tmxTile;

    public Building(float pX, float pY, ElementConfig elementConfig, StatusConfig statusConfig) {
        super(pX, pY, TextureRegionManager.getInstance().get(elementConfig), SceneManager.getInstance().getVertexBufferObjectManager());
        this.elementConfig = elementConfig;
        this.statusConfig = statusConfig;
        this.storage = elementConfig.getStorage();
        this.production = elementConfig.getProductionStrategy(this, this.storage);

        SceneManager.getInstance().attachChild(this);

        // if building status is none it is ready. We have to register it
        // in the game manager
        if (this.getStatusConfig().isReady()) {
            this.registerBuilding();
        }
        this.setTiledPosition();
    }


    // registers the building at game manager
    private void registerBuilding() {
        GameManager.getInstance().addBuilding(this);
    }

    /**
     * Set the position adjusted to the matched Tile
     * TODO code duplicated with Unit
     */
    private void setTiledPosition() {
        this.setTiledPosition(this.getX(), this.getY());
    }
    private void setTiledPosition(float pX, float pY) {
        TMXTile tmxTile = SceneManager.getInstance().getTile(pX, pY);
        if (tmxTile != null) {
            this.tmxTile = tmxTile;
            this.setPosition(this.tmxTile.getTileX(), this.tmxTile.getTileY());
        } else {
            Log.w("Building/setTiledPosition", "TmxTile is NULL!");
        }
    }

    public int getTileColumn() {
        this.tmxTile = SceneManager.getInstance().getTile(this.getX(), this.getY());
        return this.tmxTile.getTileColumn();
    }

    public int getTileRow() {
        this.tmxTile = SceneManager.getInstance().getTile(this.getX(), this.getY());
        return this.tmxTile.getTileRow();
    }

    @Override
    public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {

        if (this.getStatusConfig().isReady()) {
            switch (pSceneTouchEvent.getAction()) {
                case TouchEvent.ACTION_DOWN:
                    this.menu.display();
                    break;
                //case TouchEvent.ACTION_MOVE: {
                //    break;}
                //case TouchEvent.ACTION_UP:
                //    break;
            }
        } else if (this.getStatusConfig().isSetLocation()) {
            switch (pSceneTouchEvent.getAction()) {
                case TouchEvent.ACTION_DOWN: {
                    //this.menu.actionOnTouch(this);
                    break;
                }
                case TouchEvent.ACTION_MOVE: {
                    this.setTiledPosition(pSceneTouchEvent.getX(),pSceneTouchEvent.getY());
                    break;
                }
                case TouchEvent.ACTION_UP: { // The object is moved so we can evaluate what menu we display
                    this.menu.display(statusConfig, pSceneTouchEvent);
                    break;
                }

            }


        }

        return true;
    }

    public int getColumnTileSize() {
        return this.elementConfig.getTileSizeColumn();
    }

    public int getRowTileSize() {
        return this.elementConfig.getTileSizeRow();
    }


    @Override
    public String toString() {
        final String string = this.elementConfig.toString() + " " + super.toString();
        return super.toString();
    }

    protected void setMenu(MenuStrategy menu, SpriteAttachable sprite) {
        menu.setSprite(sprite);
        this.menu = menu;
    }

    protected StatusConfig getStatusConfig() {
        return statusConfig;
    }

    protected void setStatusConfig(StatusConfig statusConfig) {
        this.statusConfig = statusConfig;
    }

    /**
     * The mine is new, therefore the user should set the location of this object
     */
    public void setDragAndDropLocation() {
        this.setStatusConfig(StatusConfig.SET_LOCATION);
        this.setAlpha(0.2f);
        this.setZIndex(99); // first line to respond to the everything
    }

    public void constructionFinish() {
        this.setStatusConfig(StatusConfig.READY);
        this.registerBuilding();
        this.setAlpha(1f);
        this.setZIndex(0);

        // remove the green colour from the background
        OccupiedTiles.clearAll();

        //start production
        this.production.startProduction();

    }

    public ElementConfig getElementConfig() {
        return this.elementConfig;
    }

    public Storage getStorage() {
        return this.storage;
    }

    public ProductionStrategy getProduction() {
        return production;
    }
}
