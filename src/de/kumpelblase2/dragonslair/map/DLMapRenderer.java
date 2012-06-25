package de.kumpelblase2.dragonslair.map;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.map.*;
import de.kumpelblase2.dragonslair.DragonsLairMain;

public class DLMapRenderer extends MapRenderer
{
	private int rendercall = 0;
	
	@Override
	public void render(MapView view, MapCanvas canvas, Player player)
	{
		this.rendercall++;
		if(rendercall < 20)
			return;
		
		this.rendercall = 0;
		
		if(player.getItemInHand() == null || player.getItemInHand().getType() != Material.MAP)
			return;
		
		ItemStack mapstack = player.getItemInHand();
		if(!mapstack.getEnchantments().containsKey(Enchantment.ARROW_INFINITE))
			return;
		
		if(DragonsLairMain.getDungeonManager().getDungeonOfPlayer(player.getName()) == null)
			return;
		
		DLMap map = DragonsLairMain.getDungeonManager().getMapOfPlayer(player);
		map.checkUpdate();
		if(map.isRendered())
			return;
		
		DLMapRenderer.clear(canvas);
		
		String[] text = map.getSplittedText(map.getTitle() +"////" + map.getText().replace("#player#", player.getName()));
		int maxLine = text.length;
		if(maxLine > map.maxLinesPerMap)
			maxLine = map.getCurrentLine() + map.maxLinesPerMap;
		
		if(maxLine > text.length)
			maxLine = text.length;
		
		int currentRowPos = 3;
		
		for(int i = map.getCurrentLine(); i < maxLine; i++)
		{
			canvas.drawText((128 / 2) - (MinecraftFont.Font.getWidth(text[i]) / 2) - 10, currentRowPos, MinecraftFont.Font, text[i]);
			currentRowPos += map.lineHeight;
		}
		player.sendMap(view);
		map.setRendered(true);
	}
	
	public static void clear(MapCanvas canvas)
	{
		for(int i = 0; i < 128; i++)
		{
			for(int i2 = 0; i2 < 128; i2++)
				canvas.setPixel(i, i2, (byte)0);
		}
	}
}