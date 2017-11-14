package com.cjburkey.micha;

import java.util.regex.Pattern;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Class responsible for holding independent and self-contained standard core classes and methods.
 * 
 * @author cjburkey
 */
public final class Core {
	
	public static final Logger LOGGER = LogManager.getLogger("Micha");
	
	/**
	 * Logs a standard information message.
	 * 
	 * @param	msg		The message to log.
	 * @param	params	Parameters referenced in the message.
	 */
	public static final void logInfo(String msg, Object... params) {
		LOGGER.info(msg, params);
	}
	
	/**
	 * Logs a standard error message.
	 * 
	 * @param	msg		The error to log.
	 * @param	params	Parameters referenced in the error.
	 */
	public static final void logErr(String msg, Object... params) {
		LOGGER.error(msg, params);
	}
	
	/**
	 * Retrieve the current system time (fairly accurately) in nanoseconds.
	 * 
	 * @return	The current time in nanoseconds.
	 */
	public static long now() {
		return System.nanoTime();
	}
	
	/**
	 * Does as the name implies: handles crash reports so you don't have to!
	 * 
	 * @param	e			The <b>Throwable</b> instance (the exception)
	 * @param	killOnExit	Whether or not the crash is bad enough to warrant a messy JVM shutdown.
	 */
	public static void handleCrash(Throwable e, boolean killOnExit) {
		handleCrash(Thread.currentThread(), e, killOnExit);
	}
	/**
	 * Does as the name implies: handles crash reports so you don't have to!
	 * 
	 * @param	thread		The thread on which the crash occurred.
	 * @param	e			The <b>Throwable</b> instance (the exception)
	 * @param	killOnExit	Whether or not the crash is bad enough to warrant a messy JVM shutdown.
	 */
	public static void handleCrash(Thread thread, Throwable e, boolean killOnExit) {
		logErr("A fatal exception has occurred and the game must close.");
		logErr(" -- I'm so sorry D: -- ");
		logErr("Thread in question: {}", thread.getName());
		logErr("Error summary: {}", e.getMessage());
		logErr("Complete error:");
		e.printStackTrace();
		if (killOnExit) {
			System.exit(-1);
		}
	}
	
	/**
	 * Keeps track of a Semantic Versioning compatible version.
	 * <p>More information: {@link http://semver.org/}.</p>
	 * 
	 * @author cjburkey
	 */
	public final static class SemVer {
		
		/**
		 * A SemVer which cannot exist, usually used to show an error has occurred.
		 */
		public static final SemVer ERROR = new SemVer(-1, -1, -1);
		
		private final int major;
		private final int minor;
		private final int patch;
		
		/**
		 * Creates an instance of the Semantic Versioning tracker.
		 * <p>More information: {@link http://semver.org/}</p>
		 * 
		 * @param	major	The major version
		 * @param	minor	The minor version
		 * @param	patch	The patch version
		 */
		public SemVer(int major, int minor, int patch) {
			this.major = major;
			this.minor = minor;
			this.patch = patch;
		}
		
		/**
		 * Retrieves the major version of this Semantic Version.
		 * 
		 * @return	Major version.
		 */
		public int getMajor() {
			return major;
		}
		
		/**
		 * Retrieves the minor version of this Semantic Version.
		 * 
		 * @return	Minor version.
		 */
		public int getMinor() {
			return minor;
		}
		
		/**
		 * Retrieves the patch version of this Semantic Version.
		 * 
		 * @return	Patch version.
		 */
		public int getPatch() {
			return patch;
		}
		
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + major;
			result = prime * result + minor;
			result = prime * result + patch;
			return result;
		}
		
		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			SemVer other = (SemVer) obj;
			if (major != other.major) {
				return false;
			}
			if (minor != other.minor) {
				return false;
			}
			if (patch != other.patch) {
				return false;
			}
			return true;
		}
		
		@Override
		public String toString() {
			StringBuilder out = new StringBuilder();
			out.append(major);
			out.append('.');
			out.append(minor);
			out.append('.');
			out.append(patch);
			return out.toString();
		}
		
		/**
		 * Takes in a Semantic Version string and returns a SemVer object.
		 * The string should match the SemVer standards: {@link http://semver.org/}.
		 * 
		 * <p>(Only the core version is supported, currently. So <b>0.0.1-beta</b> would be invalid, but <b>0.0.1</b> is completely valid).</p>
		 * 
		 * @param	semver		The string representing the version.
		 * @return	A SemVer	instance corresponding to the input SemVer string. If there is an error, <b>SemVer.ERROR</b> is returned instead.
		 */
		public static SemVer parse(String semver) {
			String[] split = semver.split(Pattern.quote("."));
			if (split.length != 3) {
				logErr("Unable to parse SemVer string: \"{}\" - The string could not be split into three parts.", semver);
				return ERROR;
			}
			try {
				int major = Integer.parseInt(split[0]);
				int minor = Integer.parseInt(split[1]);
				int patch = Integer.parseInt(split[2]);
				return new SemVer(major, minor, patch);
			} catch(Exception e) {
			}
			logErr("Unable to parse SemVer string: \"{}\" - Could not parse integer major/minor/patch.", semver);
			return ERROR;
		}
		
	}
	
	public static final class GameLoop {
		
		/*private final long NANO_PER_SEC = 1000000000L;
		private final long TARGET_FPS = 60;
		private final long TARGET_UPDATE_TIME = NANO_PER_SEC / TARGET_FPS;
		private final int UPDATE_CAP = 320;
		private final long MIN_FRAME_TIME = NANO_PER_SEC / UPDATE_CAP;*/
		
		private static final long NANO_PER_SEC = 1000000000L;
		//private final long targetFps;
		private final long targetUpdateTime;
		//private final int updateCap;
		private final long minFrameTime;
		
		private boolean running = false;
		private final ILoopCall onExit;
		private final IUpdateCall onUpdate;
		private final ILoopCall onRender;
		
		private int fps = 0;
		private int tmpFps = 0;
		private long lastFpsTime;
		private long lastLoopTime;
		private long nextLoopTime;
		
		/**
		 * Initializes an instance of GameLoop, setting up the values to prepare to begin.
		 * 
		 * @param	onExit		Called when the game is meant to close peacefully.
		 * @param	onUpdate	Called every game update.
		 * @param	onRender	Called every frame render.
		 * @param	targetFps	The target frames-per-second.
		 * @param	updateCap	The maximum number of updates that can occur within a second.
		 */
		public GameLoop(ILoopCall onExit, IUpdateCall onUpdate, ILoopCall onRender, int targetFps, int updateCap) {
			this.onExit = onExit;
			this.onUpdate = onUpdate;
			this.onRender = onRender;
			//this.targetFps = targetFps;
			//this.updateCap = updateCap;
			
			targetUpdateTime = NANO_PER_SEC / targetFps;
			minFrameTime = NANO_PER_SEC / updateCap;
			
			lastFpsTime = now();
			lastLoopTime = now();
			nextLoopTime = now() + minFrameTime;
		}
		
		/**
		 * The true initializer of the game loop, there's no going back after this begins.
		 */
		private void beginLoop() {
			while (running) {
				nextLoopTime = now() + minFrameTime;
				long now = now();
				long updateLength = now - lastLoopTime;
				lastLoopTime = now;
				double delta = ((double) updateLength / (double) targetUpdateTime);
				
				onUpdate.onCall(delta);
				onRender.onCall();
				
				tmpFps ++;
				if (now - lastFpsTime >= NANO_PER_SEC) {
					fps = tmpFps;
					tmpFps = 0;
					lastFpsTime = now;
				}
				
				while (now() < nextLoopTime) {
					try {
						Thread.sleep(1);
					} catch (Exception e) {
						handleCrash(e, true);
					}
				}
				
				onExit.onCall();
			}
		}
		
		/**
		 * Initializes the loop.
		 * <p>Note: This will lock the current thread; it is best to complete all initialization before the gameloop begins.</p>
		 */
		public void start() {
			if (!running) {
				running = true;
				beginLoop();
			}
		}
		
		/**
		 * Gently shuts the game down, gives everything time to close handles and release resources.
		 */
		public void stop() {
			if (running) {
				running = false;
			}
		}
		
		/**
		 * Retrieves the frames-per-second of the previous second.
		 * 
		 * @return	FPS of the previous second.
		 */
		public int getFps() {
			return fps;
		}
		
		/**
		 * Callback for a general no-parameters-required loop call.
		 * 
		 * @author cjburkey
		 */
		@FunctionalInterface
		public static interface ILoopCall {
			
			/**
			 * Called when the specific event occurs.
			 */
			void onCall();
			
		}
		
		/**
		 * Callback for an update, passes delta.
		 * 
		 * @author cjburkey
		 */
		@FunctionalInterface
		public static interface IUpdateCall {
			
			/**
			 * Called each game update.
			 * 
			 * @param	delta	The delta value between frames.
			 */
			void onCall(double delta);
			
		}
		
	}
	
}