package com.cjburkey.micha;

import com.cjburkey.micha.Core.SemVer;

/**
 * Class responsible for handling of the core engine aspects.
 * 
 * @author cjburkey
 */
public final class Micha {
	
	public final SemVer MICHA_VERSION;
	
	/**
	 * Creates an instance of Micha.
	 */
	public Micha() {
		Core.logInfo("Creating Micha.");
		MICHA_VERSION = SemVer.parse("0.0.1");
		Thread.setDefaultUncaughtExceptionHandler((t, e) -> Core.handleCrash(t, e, true));
		Core.logInfo("Set default error handler.");
		Core.logInfo("Micha created.");
	}
	
	/**
	 * Launches Micha, begins LWJGL and IGameEngineImplementation search.
	 * 
	 * @param	args	Optionally, arguments passed to the game.
	 */
	public void launch(String... args) {
		Core.logInfo("Launching Micha {} with arguments: {}", MICHA_VERSION, args);
	}
	
}