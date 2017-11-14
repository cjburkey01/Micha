package com.cjburkey.micha;

public final class GameLaunch {
	
	private static Micha core;
	
	public static void main(String[] args) {
		core = new Micha();
		core.launch(args);
	}
	
	public static Micha getMicha() {
		return core;
	}
	
}