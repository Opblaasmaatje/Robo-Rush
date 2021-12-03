package game.assets.levels.level_1;

import game.assets.levels.def.*;
import game.assets.tiles.Tile_Static;
import game.system.helpers.Helpers;
import game.system.helpers.Logger;
import game.textures.TEXTURE_LIST;
import game.textures.Texture;

import java.awt.*;
import java.util.LinkedList;
import java.util.Random;

public class Level_1 extends Level {

    public Level_1() {
        super();
    }

    @Override
    protected void generateLevel(Random rand) {
        this.generator.generate();
        // convert the generation to tiles and objects;
        this.objects.addAll(this.generator.getDungeonInTiles(TEXTURE_LIST.dungeon));
    }
}
