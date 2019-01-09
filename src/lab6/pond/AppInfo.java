package lab6.pond;

import java.util.Arrays;
import java.util.List;

public class AppInfo {
	/**
	 * Your application name.
	 */
	public static final String APP_NAME = "DuckPond";
	/**
	 * Your name.
	 */
	public static final String APP_DEVELOPER = "Anya";
	/**
	 * A short description.
	 */
	public static final String APP_DESCRIPTION = "Forelesningskode";
	/**
	 * List of extra credits (e.g. for media sources)
	 */
	public static final List<String> APP_EXTRA_CREDITS = Arrays.asList(//
			/* "Graphics by Foo Bar" */
			);
	/**
	 * Help text. Could be used for an in-game help page, perhaps.
	 * <p>
	 * Use <code>\n</code> for new lines, <code>\f<code> between pages (if multi-page).
	 */
	public static final String APP_HELP = "Keyboards shortcuts:\n" //
		+ "    * F11: Toggle Fullscreen\n"
		+ "  with Ctrl/Cmd:\n" //
		+ "    * +: Zoom in\n"
		+ "    * -: Zoom out\n"
		+ "    * F: Toggle Fullscreen\n"
		+ "    * L: Redraw text\n"
		+ "    * P: Pause\n"
		+ "    * Q: Quit\n"
		+ "    * R: Cycle text resolution\n"
		;
}
