package com.cjburkey.micha.glfw;

public final class Window {
	
	private String title;
	private WindowSize size;
	private boolean sizeUpdate;
	
	/**
	 * Creates a window with the specified title and size.
	 * 
	 * @param	title	The title of the window.
	 * @param	size	The size of the window.
	 */
	public Window(String title, WindowSize size) {
		this.title = title;
		this.size = size;
		sizeUpdate = true;
	}
	
	/**
	 * Makes the window visible.
	 */
	public void show() {
		
	}
	
	/**
	 * Makes the window invisible.
	 */
	public void hide() {
		
	}
	
	/**
	 * Destroys GLFW and the window. Shouldn't be called unless the game is closing.
	 */
	public void destroy() {
		hide();
	}
	
	/**
	 * Sets the title of the window.
	 * 
	 * @param	title	The title of the window.
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
	 * Retrieves the title of the window.
	 * 
	 * @return	The title of the window.
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * Sets the size of the window.
	 * 
	 * @param	size	The new window size;
	 */
	public void setSize(WindowSize size) {
		this.size = size;
		sizeUpdate = true;
	}
	
	/**
	 * Retrieves the size of the window.
	 * 
	 * @return	The size of the window.
	 */
	public WindowSize getSize() {
		return size;
	}
	
	/**
	 * Called each frame before everything renders.
	 */
	public void onRenderBefore() {
		
	}
	
	/**
	 * Called each frame after everything renders.
	 */
	public void onRenderAfter() {
		
	}
	
	/**
	 * Class for keeping track of a window's width and height.
	 * 
	 * @author cjburkey
	 */
	public static final class WindowSize {
		
		private final int width;
		private final int height;
		
		/**
		 * Create a record of a window's size.
		 * 
		 * @param	width	The width of the window.
		 * @param	height	The height of the window.
		 */
		public WindowSize(int width, int height) {
			this.width = width;
			this.height = height;
		}
		
		/**
		 * Retrieves the width of the window.
		 * 
		 * @return	The width of the window.
		 */
		public int getWidth() {
			return width;
		}
		
		/**
		 * Retrieves the height of the window.
		 * 
		 * @return	The height of the window.
		 */
		public int getHeight() {
			return height;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + height;
			result = prime * result + width;
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
			WindowSize other = (WindowSize) obj;
			if (height != other.height) {
				return false;
			}
			if (width != other.width) {
				return false;
			}
			return true;
		}
		
		@Override
		public String toString() {
			StringBuilder out = new StringBuilder();
			out.append('(');
			out.append(width);
			out.append(',');
			out.append(' ');
			out.append(height);
			out.append(')');
			return out.toString();
		}
		
	}
	
}