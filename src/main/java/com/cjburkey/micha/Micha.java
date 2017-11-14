package com.cjburkey.micha;

import com.cjburkey.micha.Core.GameLoop;
import com.cjburkey.micha.Core.SemVer;

/**
 * Class responsible for handling of the core engine aspects.
 * 
 * @author cjburkey
 */
public final class Micha {
	
	/**
	 * The version of Micha running.
	 */
	public final SemVer MICHA_VERSION;
	
	/**
	 * The core gameloop.
	 */
	public final GameLoop gameLoop;
	
	/**
	 * Creates an instance of Micha.
	 */
	public Micha() {
		Core.logInfo("Creating Micha.");
		MICHA_VERSION = SemVer.parse("0.0.1");
		Thread.setDefaultUncaughtExceptionHandler((t, e) -> Core.handleCrash(t, e, true));
		Core.logInfo("Set default error handler.");
		gameLoop = new GameLoop(() -> onExitGame(), (d) -> onUpdateGame(d), () -> onRenderGame(), 60, 320);
		Core.logInfo("Created game loop.");
		Core.logInfo("Micha created.");
	}
	
	/**
	 * Launches Micha, begins LWJGL and IGameEngineImplementation search.
	 * 
	 * @param	args	Optionally, arguments passed to the game.
	 */
	public void launch(String... args) {
		Core.logInfo("Launching Micha {} with arguments: {}", MICHA_VERSION, args);
		initGame();
		Core.logInfo("Starting game loop.");
		gameLoop.start();
	}
	
	/**
	 * Called when the core game is being initialized.
	 */
	private void initGame() {
		Core.logInfo("Initializing the game.");
	}
	
	/**
	 * Called when the game is closing nicely.
	 */
	private void onExitGame() {
		
	}
	
	/**
	 * Called every game update.
	 * 
	 * @param	delta	The delta between this update and previous updates.
	 */
	private void onUpdateGame(double delta) {
		
	}
	
	/**
	 * Called every frame rendered.
	 */
	private void onRenderGame() {
		if (gameLoop.isFpsFresh() && gameLoop.getFps() != 0) {
			Core.logInfo("FPS: {}", gameLoop.getFps());
		}
	}
	
	// -- IMPORTANT STATIC STUFF -- //
	
	/**
	 * Called when a fatal error has occurred and the game must stop. Only called if the game <i>should</i> be able to shut down nicely.
	 * 
	 * @param	e	The exception thrown
	 */
	public static void onFatalError(Throwable e) {
		Core.logErr("Shutting game down nicely due to error: {}" + e.getMessage());
		GameLaunch.getMicha().gameLoop.stop();
	}
	
}