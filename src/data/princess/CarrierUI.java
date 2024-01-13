package data.princess;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.ModSpecAPI;
import com.fs.starfarer.api.SettingsAPI;
import com.fs.starfarer.api.combat.BaseEveryFrameCombatPlugin;
import com.fs.starfarer.api.combat.ViewportAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.CombatEngineAPI;
import com.fs.starfarer.api.combat.ShipHullSpecAPI;
import com.fs.starfarer.api.combat.ShipHullSpecAPI.EngineSpecAPI;
import com.fs.starfarer.api.combat.FighterLaunchBayAPI;
import com.fs.starfarer.api.loading.Description;
import com.fs.starfarer.api.loading.Description.Type;
import com.fs.starfarer.api.loading.FighterWingSpecAPI;
import com.fs.starfarer.api.loading.HullModSpecAPI;
import com.fs.starfarer.api.loading.WeaponSpecAPI;
import com.fs.starfarer.api.loading.MissileSpecAPI;
import com.fs.starfarer.api.input.InputEventAPI;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.StringBuilder;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

import org.codehaus.janino.JavaSourceClassLoader;
import org.codehaus.janino.util.resource.ResourceFinder;
import com.fs.starfarer.loading.Objectsuper;
import java.util.List;

import java.lang.Class;
import java.lang.ClassLoader;

import com.fs.starfarer.util.IntervalTracker;

import java.awt.Color;
import org.lazywizard.lazylib.ui.FontException;
import org.lazywizard.lazylib.ui.LazyFont;
import org.lazywizard.lazylib.ui.LazyFont.DrawableString;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;

public class CarrierUI extends BaseEveryFrameCombatPlugin
	{
	private static Logger logger = Global.getLogger(CarrierUI.class);
	
//	private static volatile JavaSourceClassLoader loader;

	private Class ac;
	
	private Object heresy;
	private MethodHandle heresyLoader;
	
	public Color GREEN;
	public Color BLUE;
	
	public DrawableString font;
	
	public void init(CombatEngineAPI engine)
		{
		GREEN = Global.getSettings().getColor("textFriendColor");
		BLUE = Global.getSettings().getColor("textNeutralColor");
		
	//	Object test = Global.getSettings().getHullModSpec("shielded_holds");
	//	if (loader == null)
			{
			// It doesn't matter if the code is too long - the basic compiler will spit equally long IL from shorter code. Maybe with less variable usage, but that's VM's job and, frankly, only happens once on game load.
			try
				{
				LazyFont fontd = LazyFont.loadFont("graphics/fonts/victor14.fnt");
				font = fontd.createText();
				/*
				Class clazz = test.getClass();
				ClassLoader cloader = clazz.getClassLoader();
			//ResourceFinder resloader = (ResourceFinder) (new Objectsuper());
				MethodHandle resloaderctor = MethodHandles.lookup().findConstructor(Objectsuper.class, MethodType.methodType(void.class));
				ResourceFinder resloader = (ResourceFinder) (resloaderctor.invoke());
			
			//	loader = new JavaSourceClassLoader(cloader, resloader, "UTF-8");
				loader = (JavaSourceClassLoader) (MethodHandles.lookup().findConstructor(JavaSourceClassLoader.class, MethodType.methodType(void.class, ClassLoader.class, ResourceFinder.class, String.class)).invoke(cloader, resloader, "UTF-8"));
			//	heresy = (Heresy)loader.loadClass("data.princess.Heresy").newInstance();*/
			
				Class field = Class.forName("java.lang.reflect.Field", true, Class.class.getClassLoader());
				
				MethodType type = MethodType.methodType(field, new Class[] { String.class });
				
			//	logger.log(Level.INFO, fieldGetter);
				
				
				
			//	if (ac == null)
					{
				//	logger.log(Level.INFO, "AC NOT FOUND");
					ac = Class.forName("com.fs.starfarer.combat.entities.ship.A.ooOO");
					}
				/*
				MethodHandle fieldsGetter = MethodHandles.lookup().findVirtual(Class.class, "getDeclaredFields", type);
				Object[] fields = ac.getDeclaredFields();
				
				MethodType typeType = MethodType.methodType(field, new Class[] { String.class });
				MethodHandle fieldTypeGetter = MethodHandles.lookup().findVirtual(field, "getType", typeType);
				
				for (int i = 0; i < fields.length; i++)
					{
					if (fields[i].getType().equals(IntervalTracker.class))
						heresy = fields[i];
					}
				if (heresy == null)*/
					{
				//	logger.log(Level.INFO, "HERESY NOT FOUND");
					MethodHandle fieldGetter = MethodHandles.lookup().findVirtual(Class.class, "getDeclaredField", type);
					heresy = fieldGetter.invoke(ac, "if.String");
					}
				
				logger.log(Level.INFO, MethodHandles.lookup().findVirtual(field, "setAccessible", MethodType.methodType(void.class, new Class[] { boolean.class })));
				MethodHandles.lookup().findVirtual(field, "setAccessible", MethodType.methodType(void.class, new Class[] { boolean.class })).invoke(heresy, true);
				
				heresyLoader = MethodHandles.lookup().findVirtual(field, "get", MethodType.methodType(Object.class, new Class[] { Object.class }));
				}
			catch (Throwable ex)
				{
				logger.log(Level.INFO, "oop", ex);
				}
			}
		}
	/*
	@Override
	public void renderInUICoords(ViewportAPI viewport)*/
	public void advance(float amount, List<InputEventAPI> events)
		{
		if (Global.getCombatEngine().getCombatUI() == null || Global.getCombatEngine().getCombatUI().isShowingCommandUI() || !Global.getCombatEngine().isUIShowingHUD())
			{
		//	logger.log(Level.INFO, "beep");
			return;
			}
		
		ShipAPI ship = Global.getCombatEngine().getPlayerShip();
		if (ship == null || !ship.hasLaunchBays())
			{
		//	logger.log(Level.INFO, "boop");
			return;
			}
		
		List<FighterLaunchBayAPI> bays = ship.getLaunchBaysCopy();
		
	//	logger.log(Level.INFO, "baap" + bays.size());
		
		for (int i = 0; i < bays.size(); i++)
			{
			FighterLaunchBayAPI bay = (FighterLaunchBayAPI)bays.get(i);
			
			IntervalTracker val = begForInterval(bay);
			if (val != null)
				{
				int perc = (int)((1f - val.getRemaining() / val.getIntervalDuration()) * 100);
				
				Color borderCol = GREEN;
				if (!ship.isAlive())
					borderCol = BLUE;
					
				float alpha = 1;
				if (Global.getCombatEngine().isUIShowingDialog())
					alpha = 0.28f;
				
				// For the record this isn't quite how alpha works.
				Color shadowcolor = new Color(0f, 0f, 0f, 1f - Global.getCombatEngine().getCombatUI().getCommandUIOpacity());
				Color color = new Color(borderCol.getRed() / 255f, borderCol.getGreen() / 255f, borderCol.getBlue() / 255f, alpha * (borderCol.getAlpha() / 255f) * (1f - Global.getCombatEngine().getCombatUI().getCommandUIOpacity()));
				
				Vector2f loc = new Vector2f(232f + 53f * i, 31f);
				
				if(Global.getSettings().getScreenScaleMult() != 1)
					{
					loc.scale(Global.getSettings().getScreenScaleMult());
					font.setFontSize(14 * Global.getSettings().getScreenScaleMult());
					}
				
				GL11.glPushAttrib(GL11.GL_ENABLE_BIT);
				GL11.glMatrixMode(GL11.GL_PROJECTION);
				GL11.glPushMatrix();
				GL11.glViewport(0, 0, Display.getWidth(), Display.getHeight());
				GL11.glOrtho(0.0, Display.getWidth(), 0.0, Display.getHeight(), -1.0, 1.0);
				GL11.glEnable(GL11.GL_TEXTURE_2D);
				GL11.glEnable(GL11.GL_BLEND);
				GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
				
				font.setText(String.format("%s%%", perc)); // Easiest way to toString in java, don't judge.
				font.setMaxWidth(46 * Global.getSettings().getScreenScaleMult());
				font.setMaxHeight(14 * Global.getSettings().getScreenScaleMult());
				font.setColor(shadowcolor);
				font.draw(loc);
				font.setColor(color);
				font.draw(loc.translate(-1f, 1f));
				
				GL11.glDisable(GL11.GL_TEXTURE_2D);
				GL11.glDisable(GL11.GL_BLEND);
				GL11.glPopMatrix();
				GL11.glPopAttrib();
				}
			
		//	logger.log(Level.INFO, begForInterval(bay));
			}
		}
		
	public IntervalTracker begForInterval(FighterLaunchBayAPI fighter)
		{
		try
			{
			if (ac.isInstance(fighter))
				{
			//	MethodHandles.lookup().findVirtual(field, "setAccessible", MethodType.methodType(void.class, new Class[] { boolean.class })).invoke(heresy, true);
				return (IntervalTracker) heresyLoader.invoke(heresy, fighter);
				}
			}
		catch (Throwable up)
			{
			logger.log(Level.INFO, "wee woo", up);
			}
		return null;
		}
	}
