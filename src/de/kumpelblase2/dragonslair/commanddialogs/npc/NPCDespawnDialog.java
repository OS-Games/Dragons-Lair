package de.kumpelblase2.dragonslair.commanddialogs.npc;

import org.bukkit.ChatColor;
import org.bukkit.conversations.*;
import de.kumpelblase2.dragonslair.DragonsLairMain;

public class NPCDespawnDialog extends ValidatingPrompt
{

	@Override
	public String getPromptText(ConversationContext context)
	{
		return ChatColor.GREEN + "Please enter the name of the npc you want to despawn:";
	}

	@Override
	protected Prompt acceptValidatedInput(ConversationContext context, String input)
	{
		if(input.equals("back") || input.equals("cancel"))
			return new NPCDespawnDialog();
		
		try
		{
			Integer id = Integer.parseInt(input);
			DragonsLairMain.getDungeonManager().despawnNPC(id);
		}
		catch(Exception e)
		{
			DragonsLairMain.getDungeonManager().despawnNPC(input);
		}
		
		context.getForWhom().sendRawMessage(ChatColor.GREEN + "NPC despawned!");
		return new NPCManageDialog();
	}

	@Override
	protected boolean isInputValid(ConversationContext context, String input)
	{
		if(input.equals("back") || input.equals("cancel"))
			return true;
		
		try
		{
			Integer id = Integer.parseInt(input);
			if(DragonsLairMain.getSettings().getNPCs().containsKey(id))
			{
				if(!DragonsLairMain.getDungeonManager().getSpawnedNPCIDs().containsKey(id))
				{
					context.getForWhom().sendRawMessage(ChatColor.RED + "The npc isn't spawned.");
					return false;
				}
				return true;
			}
			else
			{
				context.getForWhom().sendRawMessage(ChatColor.RED + "The npc doesn't exist.");
				return false;
			}
		}
		catch(Exception e)
		{
			if(DragonsLairMain.getSettings().getNPCByName(input) != null)
			{
				if(DragonsLairMain.getDungeonManager().getNPCByName(input) != null)
					return true;
				else
				{
					context.getForWhom().sendRawMessage(ChatColor.RED + "The npc isn't spawned.");
				}
			}
			context.getForWhom().sendRawMessage(ChatColor.RED + "The npc doesn't exist.");
			return false;
		}		
	}

}
