package de.kumpelblase2.dragonslair.api;

import java.sql.*;
import org.bukkit.Location;
import de.kumpelblase2.dragonslair.*;
import de.kumpelblase2.dragonslair.utilities.GeneralUtilities;
import de.kumpelblase2.dragonslair.utilities.WorldUtility;

public class Dungeon
{
	private String name;
	private int id;
	private Objective startingObjective;
	private Chapter startingChapter;
	private Location startinPosition;
	private String safeWord;
	private int minPlayers;
	private int maxPlayers;
	private String startMessage;
	private String endMessage;
	private String readyMessage;
	
	public Dungeon()
	{
		this.id = -1;
	}
	
	public Dungeon(ResultSet result)
	{
		try
		{
			this.id = result.getInt(TableColumns.Dungeons.ID.ordinal());
			this.name = result.getString(TableColumns.Dungeons.NAME.ordinal());
			this.startingObjective = DragonsLairMain.getSettings().getObjectives().get(result.getInt(TableColumns.Dungeons.STARTING_OBJECTIVE.ordinal()));
			this.startingChapter = DragonsLairMain.getSettings().getChapters().get(result.getInt(TableColumns.Dungeons.STARTING_CHAPTER.ordinal()));
			this.startinPosition = WorldUtility.stringToLocation(result.getString(TableColumns.Dungeons.STARTING_POSITION.ordinal()));
			this.safeWord = result.getString(TableColumns.Dungeons.SAFE_WORD.ordinal());
			this.maxPlayers = result.getInt(TableColumns.Dungeons.MAX_PLAYERS.ordinal());
			this.minPlayers = result.getInt(TableColumns.Dungeons.MIN_PLAYERS.ordinal());
			this.startMessage = result.getString(TableColumns.Dungeons.START_MESSAGE.ordinal());
			if(result.wasNull())
				this.startMessage = "";
			
			this.endMessage = result.getString(TableColumns.Dungeons.END_MESSAGE.ordinal());
			if(result.wasNull())
				this.endMessage = "";
			
			this.readyMessage = result.getString(TableColumns.Dungeons.PARTY_READY_MESSAGE.ordinal());
			if(result.wasNull())
				this.readyMessage = "";
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	public int getID()
	{
		return this.id;
	}
	
	public void setName(String newname)
	{
		this.name = newname;
	}
	
	public String getName()
	{
		return this.name;
	}

	public Objective getStartingObjective()
	{
		return this.startingObjective;
	}
	
	public void setStartingObjective(int id)
	{
		this.startingObjective = DragonsLairMain.getSettings().getObjectives().get(id);
	}
	
	public Chapter getStartingChapter()
	{
		return this.startingChapter;
	}
	
	public void setStartingChapter(int id)
	{
		this.startingChapter = DragonsLairMain.getSettings().getChapters().get(id);
	}
	
	public Location getStartingPosition()
	{
		return this.startinPosition;
	}
	
	public void setStartingLocation(Location loc)
	{
		this.startinPosition = loc;
	}

	public String getSafeWord()
	{
		return this.safeWord;
	}
	
	public void setSafeWord(String word)
	{
		this.safeWord = word;
	}
	
	public int getMinPlayers()
	{
		return this.minPlayers;
	}
	
	public void setMinPlayers(int min)
	{
		this.minPlayers = min;
	}
	
	public int getMaxPlayers()
	{
		return this.maxPlayers;
	}
	
	public void setMaxPlayers(int max)
	{
		this.maxPlayers = max;
	}
	
	public String getStartingMessage()
	{
		return GeneralUtilities.replaceColors(this.startMessage);
	}
	
	public void setStartingMessage(String message)
	{
		this.startMessage = message;
	}
	
	public String getEndMessage()
	{
		return GeneralUtilities.replaceColors(this.endMessage);
	}
	
	public void setEndMessage(String message)
	{
		this.endMessage = message;
	}
	
	public String getPartyReadyMessage()
	{
		return GeneralUtilities.replaceColors(this.readyMessage);
	}
	
	public void setPartyReadyMessage(String message)
	{
		this.readyMessage = message;
	}
	
	public void save()
	{
		try
		{			
			if(this.id != -1)
			{
				PreparedStatement st = DragonsLairMain.createStatement("REPLACE INTO " + Tables.DUNGEONS + "(" +
						"dungeon_id," +
						"dungeon_name," +
						"dungeon_starting_objective," +
						"dungeon_starting_chapter," +
						"dungeon_starting_pos," +
						"dungeon_safe_word," +
						"dungeon_min_players," +
						"dungeon_max_players," +
						"dungeon_start_message," +
						"dungeon_end_message," +
						"dungeon_party_ready_message" +
						") VALUES(?,?,?,?,?,?,?,?,?,?,?)");
				st.setInt(1, this.id);
				st.setString(2, this.name);
				st.setInt(3, this.startingObjective.getID());
				st.setInt(4, this.startingChapter.getID());
				st.setString(5, WorldUtility.locationToString(this.startinPosition));
				st.setString(6, this.safeWord);
				st.setInt(7, this.minPlayers);
				st.setInt(8, this.maxPlayers);
				st.setString(9, this.startMessage);
				st.setString(10, this.endMessage);
				st.setString(11, this.readyMessage);
				st.execute();
			}
			else
			{
				PreparedStatement st = DragonsLairMain.createStatement("INSERT INTO " + Tables.DUNGEONS + "(" +
						"dungeon_name," +
						"dungeon_starting_objective," +
						"dungeon_starting_chapter," +
						"dungeon_starting_pos," +
						"dungeon_safe_word," +
						"dungeon_min_players," +
						"dungeon_max_players," +
						"dungeon_start_message," +
						"dungeon_end_message," +
						"dungeon_party_ready_message" +
						") VALUES(?,?,?,?,?,?,?,?,?,?)");
				st.setString(1, this.name);
				st.setInt(2, this.startingObjective.getID());
				st.setInt(3, this.startingChapter.getID());
				st.setString(4, WorldUtility.locationToString(this.startinPosition));
				st.setString(5, this.safeWord);
				st.setInt(6, this.minPlayers);
				st.setInt(7, this.maxPlayers);
				st.setString(8, this.startMessage);
				st.setString(9, this.endMessage);
				st.setString(10, this.readyMessage);
				st.execute();
				ResultSet keys = st.getGeneratedKeys();
				if(keys.next())
					this.id = keys.getInt(1);
			}
		}
		catch(Exception e)
		{
			DragonsLairMain.Log.warning("Unable to save dungeon " + this.id);
			DragonsLairMain.Log.warning(e.getMessage());
		}
	}

	public void remove()
	{
		if(this.id == - 1)
			return;
		
		try
		{
			PreparedStatement st = DragonsLairMain.createStatement("DELETE FROM " + Tables.DUNGEONS + " WHERE `dungeon_id` = ?");
			st.setInt(1, this.id);
			st.executeUpdate();
		}
		catch(Exception e)
		{
			DragonsLairMain.Log.warning("Unable to delete dungeon " + this.id);
			DragonsLairMain.Log.warning(e.getMessage());
		}
	}
}