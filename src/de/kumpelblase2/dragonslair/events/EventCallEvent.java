package de.kumpelblase2.dragonslair.events;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import de.kumpelblase2.dragonslair.api.Event;

public class EventCallEvent extends BaseEvent
{
	private final Player m_player;
	private Event m_event;
	private boolean m_onCooldown;
	
	public EventCallEvent(Event e, final Player p, boolean onCD)
	{
		this.m_player = p;
		this.m_event = e;
		this.m_onCooldown = onCD;
	}
	
	public void setOnCooldown(boolean onCD)
	{
		this.m_onCooldown = onCD;
	}
	
	public boolean isOnCooldown()
	{
		return this.m_onCooldown;
	}
	
	public Event getEvent()
	{
		return this.m_event;
	}
	
	public final Player getPlayer()
	{
		return this.m_player;
	}
	
	public static HandlerList getHandlerList()
	{
		return handlers;
	}
}