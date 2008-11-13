/*******************************************************************************
 * Copyright (c) 2007 Exadel, Inc. and Red Hat, Inc.
 * Distributed under license by Red Hat, Inc. All rights reserved.
 * This program is made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Exadel, Inc. and Red Hat, Inc. - initial API and implementation
 ******************************************************************************/ 
package org.jboss.tools.vpe.editor.bundle;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.wst.sse.ui.StructuredTextEditor;
import org.jboss.tools.common.el.core.model.ELArgumentInvocation;
import org.jboss.tools.common.el.core.model.ELExpression;
import org.jboss.tools.common.el.core.model.ELInstance;
import org.jboss.tools.common.el.core.model.ELInvocationExpression;
import org.jboss.tools.common.el.core.model.ELModel;
import org.jboss.tools.common.el.core.model.ELPropertyInvocation;
import org.jboss.tools.common.el.core.model.ELUtil;
import org.jboss.tools.common.el.core.parser.ELParser;
import org.jboss.tools.common.el.core.parser.ELParserFactory;
import org.jboss.tools.common.el.core.parser.ELParserUtil;
import org.jboss.tools.common.model.XModel;
import org.jboss.tools.common.model.event.XModelTreeEvent;
import org.jboss.tools.common.model.event.XModelTreeListener;
import org.jboss.tools.common.model.options.PreferenceModelUtilities;
import org.jboss.tools.common.model.util.EclipseResourceUtil;
import org.jboss.tools.jst.jsp.preferences.VpePreference;
import org.jboss.tools.jst.web.project.WebProject;
import org.jboss.tools.jst.web.project.list.WebPromptingProvider;
import org.jboss.tools.vpe.VpePlugin;

public class BundleMap {
//	private static final String BEGIN_BUNDLE = "#{";
//	private static final String END_BUNDLE = "}";
//	private static final String BEGIN_ARRAY_STYLE_PROPERTY_NAME_SEPARATOR = "['";
//	private static final String END_ARRAY_STYLE_PROPERTY_NAME_SEPARATOR = "']";

	public static final String TITLE_ATTRIBUTE_NAME = "title";
	
	private BundleMapListener[] bundleMapListeners = new BundleMapListener[0];
	private StructuredTextEditor editor;
	
	private String[] javaSources;
    
    private BundleEntry[] bundles = new BundleEntry[0];
    private Map<String,UsedKey> usedKeys = new HashMap<String,UsedKey>();
    
    boolean isShowBundleUsageAsEL = "yes".equals(VpePreference.SHOW_RESOURCE_BUNDLES.getValue());
    XModelTreeListener modelListener = new ML();
	
	public void init(StructuredTextEditor editor){
		this.editor = editor;
		IEditorInput input = editor.getEditorInput();
		
		if (input instanceof IFileEditorInput) {
			javaSources = getJavaProjectSrcLocations(((IFileEditorInput)input).getFile().getProject());
		}
		refreshRegisteredBundles();
		PreferenceModelUtilities.getPreferenceModel().addModelTreeListener(modelListener);
	}
	
	public void refreshRegisteredBundles() {
		if (!hasJsfProjectNatureType() || !(editor.getEditorInput() instanceof IFileEditorInput)) return;
		IProject project = ((IFileEditorInput)editor.getEditorInput()).getFile().getProject();
		XModel model = EclipseResourceUtil.getModelNature(project).getModel();
		List<Object> l = WebPromptingProvider.getInstance().getList(model, WebPromptingProvider.JSF_REGISTERED_BUNDLES, null, null);
		if(l == null || l.size() < 2 || !(l.get(1) instanceof Map)) return;
		Map<?,?> map = (Map<?,?>)l.get(1);
		Iterator<?> it = map.keySet().iterator();
		while(it.hasNext()) {
			String uri = it.next().toString();
			String prefix = map.get(uri).toString();
			int hash = (prefix + ":" + uri).hashCode();
			removeBundle(hash);
			addBundle(hash, prefix, uri, -1000, true);
		}
	}
	
	public void clearAll(){
		bundles = new BundleEntry[0];
	    usedKeys = new HashMap<String,UsedKey>();
	}
	
	public void dispose() {
		PreferenceModelUtilities.getPreferenceModel().removeModelTreeListener(modelListener);
	}	

	private static final String[] JSF_PROJECT_NATURES = {
			WebProject.JSF_NATURE_ID
		};

	private boolean hasJsfProjectNatureType() {
		try {
			IEditorInput ei = editor.getEditorInput();
			if(!(ei instanceof IFileEditorInput)) return false;
			IProject project = ((IFileEditorInput)ei).getFile().getProject();
			if (!project.exists() || !project.isOpen()) return false;

			for (int i = 0; i < JSF_PROJECT_NATURES.length; i++) {
				if (project.getNature(JSF_PROJECT_NATURES[i]) != null) 
					return true;
			}
		} catch (CoreException e) {
			VpePlugin.getPluginLog().logError(e);
		}
		return false;
	}
	
	public boolean openBundle(String expression, String locale){
		List<ELInstance> is = parseJSFExpression(expression);
		if(is == null || is.size() == 0) return false;
		String prefix = null;
		String propertyName = null;
		for (ELInstance i: is) {
			ELExpression expr = i.getExpression();
			if(expr == null) continue;
			List<ELInvocationExpression> invs = expr.getInvocations();
			if(invs.size() > 0) {
				String[] values = getCall(invs.get(0));
				if(values != null) {
					prefix = values[0];
					propertyName = values[1];
					break;
				}
			}
		}
		if(prefix == null) return false;

		BundleEntry entry = getBundle(prefix);
		
		if(entry == null){
			if (hasJsfProjectNatureType()) {
				IProject project = ((IFileEditorInput)editor.getEditorInput()).getFile().getProject();
				XModel model = EclipseResourceUtil.getModelNature(project).getModel();
				String prefix2 = prefix;
				if(propertyName != null && prefix != null) {
					prefix2 = prefix + "." + propertyName;
				}
				WebPromptingProvider.getInstance().getList(model, WebPromptingProvider.JSF_BEAN_OPEN, prefix2, null);
			}
			return false;
		}

		if (hasJsfProjectNatureType()) {
			Properties p = new Properties();
			p.put(WebPromptingProvider.BUNDLE, entry.uri);
			p.put(WebPromptingProvider.KEY, propertyName);
			if (locale != null) p.put(WebPromptingProvider.LOCALE, locale);
			p.put(WebPromptingProvider.FILE, ((IFileEditorInput)editor.getEditorInput()).getFile().getProject());
	
			IProject project = ((IFileEditorInput)editor.getEditorInput()).getFile().getProject();
			XModel model = EclipseResourceUtil.getModelNature(project).getModel();
	
			WebPromptingProvider.getInstance().getList(model, WebPromptingProvider.JSF_OPEN_KEY, entry.uri, p);
			String error = p.getProperty(WebPromptingProvider.ERROR); 
			return (error == null || error.length() == 0);
		}
		return false;
	}

	public IFile getBundleFile(String uri){
		IEditorInput input = editor.getEditorInput();
		IProject project = ((FileEditorInput)input).getFile().getProject();
		String name = uri.replace('.','/')+".properties";
		
		if(project == null || !project.isOpen()) return null;
		try {
			if(!project.hasNature(JavaCore.NATURE_ID)) return null;
			IJavaProject javaProject = JavaCore.create(project);		
			IClasspathEntry[] es = javaProject.getResolvedClasspath(true);
			for (int i = 0; i < es.length; i++) {
				if(es[i].getEntryKind() != IClasspathEntry.CPE_SOURCE) continue;
				IFile file = (IFile)project.getWorkspace().getRoot().getFolder(es[i].getPath()).findMember("/"+name);
				if(file != null && file.exists()) return file;
			}
		} catch (CoreException e) {
			VpePlugin.getPluginLog().logError(e);
			return null;
		}
		return null;
	}
	
	private ResourceBundle getBundleByUrl(String uri) {
		try {
			if (javaSources!=null) {
				File file;
				URL[] urls = new URL[javaSources.length];
				for (int i=0;i<javaSources.length;++i) {
					try {
						file = new File(javaSources[i]).getCanonicalFile();
						urls[i] = file.toURL();
					} catch (IOException ioe) {
						VpePlugin.reportProblem(ioe);
						return null;
					}
				}
				ClassLoader classLoader = new URLClassLoader(urls, ClassLoader.getSystemClassLoader());
				ResourceBundle bundle = ResourceBundle.getBundle(uri, Locale.getDefault(), classLoader);
				
				return bundle;
			}
		} catch (MissingResourceException ex) {
		    // Ignore this exception
		}
	
		return null;
	}
	
	private static String[] getJavaProjectSrcLocations(IProject project) {
		return EclipseResourceUtil.getJavaProjectSrcLocations(project);
	}
	
	private void removeBundle(int hashCode, boolean refresh) {
		if (bundles.length == 0) return;
		int index = -1;
		for (int i = 0; i < bundles.length; i++) {
			if (hashCode == bundles[i].hashCode){
				index = i;
				break;
			}
		}
		if (index == -1) return;
		if (bundles.length == 1) {
			bundles = new BundleEntry[0];
			return;
		}
		BundleEntry[] newBundles = new BundleEntry[bundles.length - 1];
		System.arraycopy(bundles, 0, newBundles, 0, index);
		System.arraycopy(bundles, index + 1, newBundles, index, bundles.length - index - 1);
		bundles = newBundles;
		if(refresh)refreshUsedKeys();
	}

	public void removeBundle(int hashCode) {
		removeBundle(hashCode, true);
	}
	
	private void addBundle(int hashCode, String prefix, String uri, int offset, boolean refresh) {
		ResourceBundle bundle = getBundleByUrl(uri);
		BundleEntry entry = new BundleEntry(bundle, uri, prefix, hashCode, offset);
		if(bundle!=null) {
			BundleEntry[] newBundles = new BundleEntry[bundles.length + 1];
			System.arraycopy(bundles, 0, newBundles, 0, bundles.length);
			bundles = newBundles;
			bundles[bundles.length - 1] = entry;
		}
		if(refresh)refreshUsedKeys();
	}
	
	public void changeBundle(int hashCode, String prefix, String uri, int offset){
		removeBundle(hashCode, false);
		addBundle(hashCode, prefix, uri, offset, true);
	}
	
	private void changeBundleWithoutRefresh(int hashCode, String prefix, String uri, int offset){
		removeBundle(hashCode, false);
		addBundle(hashCode, prefix, uri, offset, false);
	}

	private BundleEntry getBundle(String prefix) {
		if(prefix == null) return null;
		BundleEntry lastBundle = null;
		for (int i = 0; i < bundles.length; i++) {
			if (prefix.equals(bundles[i].prefix)){
				if (lastBundle == null || lastBundle.offset > bundles[i].offset) {
					lastBundle = bundles[i];
				}
			}
		}
		return lastBundle;
	}
	
	public void refresh(){
		refreshRegisteredBundles();
		IEditorInput input = editor.getEditorInput();
			
		if (input instanceof IFileEditorInput) {
			javaSources = getJavaProjectSrcLocations(((IFileEditorInput)input).getFile().getProject());
			UsedKey key;
			UsedKey[] array = new UsedKey[0];
			array = usedKeys.values().toArray(array);			
			
			for(int i=0; i<array.length;i++){
				key = (UsedKey)array[i];
				changeBundleWithoutRefresh(key.hashCode, key.prefix, key.uri, key.offset);
			}
			refreshUsedKeys();
		}
	}
	
	private void refreshUsedKeys(){
		UsedKey key;
		UsedKey[] array = new UsedKey[0];
		array = (UsedKey[])usedKeys.values().toArray(array);
		
		for(int i=0; i<array.length;i++){
			key = (UsedKey)array[i];
			BundleEntry entry = getBundle(key.prefix);
			if(entry != null){
				String value;
				try{
					value = (String)entry.bundle.getObject(key.key);
				}catch(MissingResourceException ex){
					value = null;
					fireBundleKeyChanged(key.prefix, key.key, value);
					usedKeys.remove(key);
					continue;
				}
				if((value == null && key.value != null) || (value != null && key.value == null)){
					key.value = value;
					fireBundleKeyChanged(key.prefix, key.key, value);
					continue;
				}else if(value != null && key.value != null && !value.equals(key.value)){
					key.value = value;
					fireBundleKeyChanged(key.prefix, key.key, value);
					continue;
				}
			} else{
				key.value = null;
				fireBundleKeyChanged(key.prefix, key.key, null);
			}
		}
	}
	
	private List<ELInstance> parseJSFExpression(String expression){
		ELParser parser = ELParserUtil.getDefaultFactory().createParser();
		ELModel model = parser.parse(expression);
		List<ELInstance> is = model.getInstances();
		return is;
	}
	
	public String getBundleValue(String name){
		if(isShowBundleUsageAsEL) return name;
		List<ELInstance> is = parseJSFExpression(name);
		if(is == null) return null;
		StringBuffer sb = new StringBuffer();
		int index = 0;
		for (ELInstance i: is) {
			int start = i.getStartPosition();
			sb.append(name.substring(index, start));
			index = start;
			if(i.getExpression() instanceof ELInvocationExpression) {
				ELInvocationExpression expr = (ELInvocationExpression)i.getExpression();
				String[] values = getCall(expr);
				if(values != null) {
					String value = (values == null) ? null : getBundleValue(values[0], values[1]);
					if(value != null) {
						sb.append(value);
						index = i.getEndPosition();
					}
					
				}
			}
			if(index < i.getEndPosition()) {
				sb.append(name.substring(index, i.getEndPosition()));
				index = i.getEndPosition();
			}
		}
		sb.append(name.substring(index));
		return sb.toString();
	}
	
	String[] getCall(ELInvocationExpression expr) {
		if(expr == null) return null;
		ELInvocationExpression left = expr.getLeft();
		if(left == null) return null;
		String name = expr.getMemberName();
		if(name == null || name.length() == 0) return null;
		if(expr instanceof ELPropertyInvocation) {
			return new String[]{left.getText(), name};
		} else if(expr instanceof ELArgumentInvocation) {
			if(name.startsWith("\"") || name.startsWith("'")) {
				name = name.substring(1);
			}
			if(name.endsWith("\"") || name.endsWith("'")) {
				name = name.substring(0, name.length() - 1);
			}
			if(name.length() == 0) return null;
			return new String[]{left.getText(), name};
		}
		return null;
	}
	
	private String getBundleValue(String prefix, String propertyName) {
		BundleEntry entry = getBundle(prefix);
		if(entry != null) {
			String name = prefix + "." + propertyName;
			try{
				String value = (String)entry.bundle.getObject(propertyName);
				if(!usedKeys.containsKey(name))
					usedKeys.put(name, new UsedKey(entry.uri, prefix, propertyName, value, entry.hashCode, entry.offset));
				return value;
			}catch(MissingResourceException ex){
				return null;
			}
		} 
		return null;
	}
	
	public void addBundleMapListener(BundleMapListener listener) {
		if (listener != null) {
			BundleMapListener[] newBundleMapListener = new BundleMapListener[bundleMapListeners.length + 1];
			System.arraycopy(bundleMapListeners, 0, newBundleMapListener, 0, bundleMapListeners.length);
			bundleMapListeners = newBundleMapListener;
			bundleMapListeners[bundleMapListeners.length - 1] = listener;
		}
	}
	
	public void removeBundleMapListener(BundleMapListener listener) {
		if (listener == null || bundleMapListeners.length == 0) return;
		int index = -1;
		for (int i = 0; i < bundleMapListeners.length; i++) {
			if (listener == bundleMapListeners[i]){
				index = i;
				break;
			}
		}
		if (index == -1) return;
		if (bundleMapListeners.length == 1) {
			bundleMapListeners = new BundleMapListener[0];
			return;
		}
		BundleMapListener[] newBundleMapListener = new BundleMapListener[bundleMapListeners.length - 1];
		System.arraycopy(bundleMapListeners, 0, newBundleMapListener, 0, index);
		System.arraycopy(bundleMapListeners, index + 1, newBundleMapListener, index, bundleMapListeners.length - index - 1);
		bundleMapListeners = newBundleMapListener;
	}
	
	private void fireBundleKeyChanged(String prefix, String key, String value) {
		for (int i = 0; i < bundleMapListeners.length; i++) {
			bundleMapListeners[i].bundleKeyChanged(prefix, key, value);
		}
	}
	
	class Expression{
		public String prefix;
		public String propertyName;
	}
	
	class BundleEntry{
		public ResourceBundle bundle;
		public String uri;
		public String prefix;
		public int hashCode;
		public int offset;
		
		public BundleEntry(ResourceBundle bundle, String uri, String prefix, int hashCode, int offset){
			this.bundle = bundle;
			this.uri = uri;
			this.prefix = prefix;
			this.hashCode = hashCode;
			this.offset = offset;
		}
	}
	
	class UsedKey{
		public int hashCode;
		public String uri;
		public String prefix;
		public String key;
		public String value;
		public int offset;
		
		public UsedKey(String uri, String prefix, String key, String value, int hashCode, int offset){
			this.uri = uri;
			this.prefix = prefix;
			this.key = key;
			this.value = value;
			this.hashCode = hashCode;
			this.offset = offset;
		}
	}
	
	class ML implements XModelTreeListener {

		public void nodeChanged(XModelTreeEvent event) {
		    boolean b = "yes".equals(VpePreference.SHOW_RESOURCE_BUNDLES.getValue());	
			if(isShowBundleUsageAsEL != b) {
				isShowBundleUsageAsEL = b;
				refresh();
			}			
		}

		public void structureChanged(XModelTreeEvent event) {
		}
		
	}

}
