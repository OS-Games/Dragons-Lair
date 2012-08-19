package de.kumpelblase2.dragonslair.commanddialogs.objectives;

import org.bukkit.ChatColor;
import org.bukkit.conversations.*;
import de.kumpelblase2.dragonslair.commanddialogs.GeneralConfigDialog;

public class ObjectiveManageDialog extends ValidatingPrompt
{
	private final String[] options = new String[] { "create", "list", "delete", "edit", "back" };
	
	@Override
	public String getPromptText(ConversationContext arg0)
	{
		arg0.getForWhom().sendRawMessage(ChatColor.GREEN + "What do you want to do?");
		return ChatColor.AQUA + "create, list, delete, edit, back";
	}

	@Override
	protected Prompt acceptValidatedInput(ConversationContext arg0, String arg1)
	{
		if(arg1.equalsIgnoreCase("create"))
		{
			return new ObjectiveCreateDialog();
		}
		else if(arg1.startsWith("list"))
		{
			if(arg1.contains(" "))
				return new ObjectiveListDialog(Integer.parseInt(arg1.split("\\ ")[1]) - 1);
			else
				return new ObjectiveListDialog();
		}
		else if(arg1.equalsIgnoreCase("delete"))
		{
			return new ObjectiveDeleteDialog();
		}
		else if(arg1.equalsIgnoreCase("edit"))
		{
			return new ObjectiveEditDialog();
		}
		else if(arg1.equalsIgnoreCase("back"))
		{
			return new GeneralConfigDialog();
		}
		return END_OF_CONVERSATION;
	}

	@Override
	protected boolean isInputValid(ConversationContext context, String input)
	{
		if(input.contains(" "))
		{
			String splitt[] = input.split("\\ ");
			if(!splitt[0].equals("list"))
				return false;
			else
			{
				if(splitt.length > 2)
					return false;
				else
				{
					try
					{
						Integer.parseInt(splitt[1]);
						return true;
					}
					catch(Exception e)
					{
						return false;
					}
				}
			}		
		}
		else
		{
			for(String option : this.options)
			{
				if(option.equalsIgnoreCase(input))
					return true;
			}
		}
		return false;
	}

}
