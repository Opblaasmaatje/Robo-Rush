package game.objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import game.entities.Player;
import game.items.Item;
import game.items.ItemWood;
import game.main.GameObject;
import game.main.ID;
import game.textures.Textures;
import game.world.BIOME;

public class Tree extends GameObject {

	private ArrayList<ArrayList<BufferedImage>> tex_rows;

	private BIOME biome;
	private Player player;

	private Random r = new Random();

	public Tree(int x, int y, int z_index, ID id, BIOME biome, Player player) {
		super(x, y, z_index, id);
		this.biome = biome;
		this.player = player;

		tex_rows = new ArrayList<ArrayList<BufferedImage>>();
		int tree_type = r.nextInt(5);
		if (biome == BIOME.Forest) {
			switch (tree_type) {
				case 0:
					addTexRow(new int[] { 0, 1 });
					addTexRow(new int[] { 15, 16 });
					break;
				default:
					addTexRow(new int[] { 0, 1 });
					addTexRow(new int[] { 15, 16 });
			}
		}

	}

	public void tick() {
		int player_cenX = player.getX() + 8;
		int player_cenY = player.getY() + (16 + 8) / 2;
		int tree_cenY = y + 8;

		if (player_cenY > tree_cenY) {
			this.setZIndex(1);
		} else {
			this.setZIndex(3);
		}
	}

	public void render(Graphics g) {
		renderTreeTiles(g, x - 8, y - this.width, this.height);
		// g.drawRect(getBounds().x, getBounds().y, getBounds().width,
		// getBounds().height);
	}

	private void renderTreeTiles(Graphics g, int local_x, int local_y, int tile_size) {
		int r = 0;
		int c = 0;
		for (ArrayList<BufferedImage> row : tex_rows) {
			for (BufferedImage tex : row) {
				g.drawImage(tex, local_x + (c * tile_size), local_y + (r * tile_size), tile_size, tile_size, null);
				c++;
			}
			r++;
			c = 0;
		}
	}

	public Rectangle getBounds() {
		return new Rectangle(x, y + 3, this.width, this.height - 6);
	}

	public Rectangle getSelectBounds() {
		return new Rectangle(x, y, this.width, this.height);
	}

	public void addTexRow(int[] tex_ids) {
		ArrayList<BufferedImage> row = new ArrayList<BufferedImage>();
		for (int id : tex_ids) {
			row.add(Textures.tileSetNatureBlocks.get(id));
		}
		this.tex_rows.add(row);
	}

	public Item getItem() {
		// return new ItemWood(5, this.id, Textures.wood);
		return null;
	}

}
