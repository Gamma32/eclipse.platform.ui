/*******************************************************************************
 * Copyright (c) 2003, 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - Initial API and implementation
 *******************************************************************************/
package org.eclipse.ui.internal.browser;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;
/**
 * The main web browser plugin class.
 */
public class WebBrowserUIPlugin extends AbstractUIPlugin {
	// Web browser plugin id
	public static final String PLUGIN_ID = "org.eclipse.ui.browser";

	// singleton instance of this class
	private static WebBrowserUIPlugin singleton;
	
	// cached copy of all browsers
	private static List browsers;

	/**
	 * Create the WebBrowserUIPlugin
	 */
	public WebBrowserUIPlugin() {
		super();
		singleton = this;
	}

	/**
	 * Returns the singleton instance of this plugin.
	 *
	 * @return org.eclipse.ui.internal.browser.WebBrowserPlugin
	 */
	public static WebBrowserUIPlugin getInstance() {
		return singleton;
	}

	/**
	 * Returns the translated String found with the given key.
	 *
	 * @param key java.lang.String
	 * @return java.lang.String
	 */
	public static String getResource(String key) {
		try {
			return Platform.getResourceString(getInstance().getBundle(), key);
		} catch (Exception e) {
			return key;
		}
	}

	/**
	 * Returns the translated String found with the given key,
	 * and formatted with the given arguments using java.text.MessageFormat.
	 *
	 * @param key java.lang.String
	 * @param arg java.lang.String
	 * @return java.lang.String
	 */
	public static String getResource(String key, String arg) {
		try {
			String text = getResource(key);
			return MessageFormat.format(text, new String[] { arg });
		} catch (Exception e) {
			return key;
		}
	}

	/**
	 * Shuts down this plug-in and saves all plug-in state.
	 *
	 * @exception Exception
	 */
	public void stop(BundleContext context) throws Exception {
		super.stop(context);
		BrowserManager.safeDispose();
	}
	
	/**
	 * Returns an array of all known browers.
	 * <p>
	 * A new array is returned on each call, so clients may store or modify the result.
	 * </p>
	 * 
	 * @return a possibly-empty array of browser instances {@link IClient}
	 */
	public static IBrowserExt[] getBrowsers() {
		if (browsers == null)
			loadBrowsers();
		IBrowserExt[] c = new IBrowserExt[browsers.size()];
		browsers.toArray(c);
		return c;
	}
	
	public static IBrowserExt findBrowsers(String executable) {
		IBrowserExt[] browsers = getBrowsers();
		if (browsers == null || executable == null)
			return null;
		
		int ind1 = executable.lastIndexOf("/");
		int ind2 = executable.lastIndexOf("\\");
		if (ind2 > ind1)
			ind1 = ind2;
		executable = executable.substring(ind1 + 1);
		
		String os = Platform.getOS();
		int size = browsers.length;
		for (int i = 0; i < size; i++) {
			if (browsers[i].getOS().toLowerCase().indexOf(os) != -1) {
				if (browsers[i].isAvailable()) {
					String executable2 = browsers[i].getExecutable();
					if (executable.startsWith(executable2))
						return browsers[i];
				}
			}
		}
		return null;
	}
	
	/**
	 * Load the browsers extension point.
	 */
	private static synchronized void loadBrowsers() {
		if (browsers != null)
			return;
		Trace.trace(Trace.CONFIG, "->- Loading .browsers extension point ->-");
		IExtensionRegistry registry = Platform.getExtensionRegistry();
		IConfigurationElement[] cf = registry.getConfigurationElementsFor(PLUGIN_ID, "internalBrowsers");

		int size = cf.length;
		browsers = new ArrayList(size);
		for (int i = 0; i < size; i++) {
			try {
				browsers.add(new BrowserExt(cf[i]));
				Trace.trace(Trace.CONFIG, "  Loaded browser: " + cf[i].getAttribute("id"));
			} catch (Throwable t) {
				Trace.trace(Trace.SEVERE, "  Could not load browser: " + cf[i].getAttribute("id"), t);
			}
		}
		Trace.trace(Trace.CONFIG, "-<- Done loading .browsers extension point -<-");
	}
	
	/**
	 * Logs an Error message with an exception. Note that the message should
	 * already be localized to proper locale. ie: Resources.getString() should
	 * already have been called
	 */
	public static synchronized void logError(String message, Throwable ex) {
		if (message == null)
			message = ""; //$NON-NLS-1$
		Status errorStatus = new Status(IStatus.ERROR, PLUGIN_ID, IStatus.OK,
				message, ex);
		WebBrowserUIPlugin.getInstance().getLog().log(errorStatus);
	}

	/**
	 * Logs a Warning message with an exception. Note that the message should
	 * already be localized to proper local. ie: Resources.getString() should
	 * already have been called
	 */
	/*public static synchronized void logWarning(String message) {
		if (WebBrowserUIPlugin.DEBUG) {
			if (message == null)
				message = ""; //$NON-NLS-1$
			Status warningStatus = new Status(IStatus.WARNING, PLUGIN_ID,
					IStatus.OK, message, null);
			WebBrowserUIPlugin.getInstance().getLog().log(warningStatus);
		}
	}*/
}