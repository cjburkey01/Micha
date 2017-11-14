package com.cjburkey.micha;

/**
 * Class responsible for starting the game.
 * 
 * @author cjburkey
 */
public final class GameLaunch {
	
	private static Micha core;
	
	/**
	 * Called upon the first launch of the program.
	 * 
	 * @param	args	Arguments passed to the program.
	 */
	public static void main(String[] args) {
		core = new Micha();
		core.launch(args);
	}
	
	/**
	 * Retrieves the instance of Micha currently running.
	 * 
	 * @return	The Micha instance.
	 */
	public static Micha getMicha() {
		return core;
	}
	
}