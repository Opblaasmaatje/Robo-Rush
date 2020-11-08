package game.inventory;

import game.items.Item;
import game.main.Game;
import game.main.GameObject;
import game.main.Handler;
import game.main.MouseInput;
import game.world.World;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class InventorySystem {
	public static final int slot_w = 20, slot_h = 20;
	public static final int stackSize = 99;
	public static final Color slot_bg = new Color(0, 0, 0, 127);
	public static final Color slot_outline = new Color(20, 20, 20, 255);
	public static final Color slot_hover = new Color(255, 255, 255, 50);

	private Handler handler;
	private World world;

	public Inventory player_hotbar;
	public int hotbar_selected;
	private Inventory player_inv;
	private ArrayList<Inventory> open_inventories = new ArrayList<>();

	//public static boolean player_inventory_open = true;
	public static Item holding = null;

	public InventorySystem() {
		this.player_hotbar = Game.player.hotbar;
		this.player_inv = Game.player.inventory;
		this.handler = Game.handler;
	}

	public void tick() {
		this.player_hotbar.tick();
		for(Inventory inv : open_inventories) {
			inv.tick();
		}
	}

	public void render(Graphics g) {
		player_hotbar.render(g);
		//if(player_inventory_open) player_inventory.render(g);

		for(Inventory inv : open_inventories) {
			inv.render(g);
		}
	}

	public void mouseClicked(MouseEvent e) {
		for(Inventory inv : open_inventories) {
			if(Game.mouseInput.mouseOverLocalRect(inv.getInventoryBounds())) {
				switch (e.getButton()) {
					case MouseEvent.BUTTON1 -> inv.mouseLeftClick(e);
					case MouseEvent.BUTTON2 -> inv.mouseMiddleClick(e);
					case MouseEvent.BUTTON3 -> inv.mouseRightClick(e);
					default -> System.out.println("Mouse function not recognized");
				}
			} else {
				mouseClickedOutside(e);
			}
		}
	}

	public void mouseClickedOutside(MouseEvent e) {

	}

	public void addOpenInventory(Inventory inv) {
		if(!this.open_inventories.contains(inv)) {
			this.open_inventories.add(inv);
		} else {
			removeOpenInventory(inv);
		}
	}
	public void removeOpenInventory(Inventory inv) {
		this.open_inventories.remove(inv);
	}

	public void pickupItemToPlayerInv(GameObject obj) {
		Item item = obj.getItem();
		if(item != null) {
			if(player_inv.canAcceptItem(item)) {
				player_inv.addItem(item);
				handler.findAndRemoveObject(obj, world);
			}
		}
	}

	public void setWorld(World world) {
		this.world = world;
	}

	public boolean inventoryIsOpen() {
		return this.open_inventories.size() > 0;
	}
}
