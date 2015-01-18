package br.com.debico.core.helpers;

import com.github.slugify.Slugify;

public final class WebUtils {

	private static final Slugify SLUGIFY = new Slugify(true);
	
	private WebUtils() {
		
	}
	
	/**
	 * Transforma uma {@link String} em um <code>pretty link</code>.
	 * <p/>
	 * Por exemplo, Brasileirão 2014 ficaria <code>brasileirao-2014</code> 
	 * 
	 * @see <a href="https://github.com/slugify/slugify">Slugify</a>
	 * @param theString
	 * @return
	 */
	public static String toPermalink(final String theString) {
		return SLUGIFY.slugify(theString).toString();
	}
	
}
