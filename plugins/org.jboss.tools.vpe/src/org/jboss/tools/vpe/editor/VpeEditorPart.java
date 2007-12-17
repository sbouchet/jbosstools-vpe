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
package org.jboss.tools.vpe.editor;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IPropertyListener;
import org.eclipse.ui.IReusableEditor;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartConstants;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.texteditor.IStatusField;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.ui.texteditor.ITextEditorExtension;
import org.eclipse.wst.sse.ui.StructuredTextEditor;
import org.jboss.tools.common.model.XModelObject;
import org.jboss.tools.common.model.event.XModelTreeEvent;
import org.jboss.tools.common.model.event.XModelTreeListener;
import org.jboss.tools.common.model.ui.util.ModelUtilities;
import org.jboss.tools.jst.jsp.editor.IVisualEditor;
import org.jboss.tools.jst.jsp.preferences.VpePreference;
import org.jboss.tools.vpe.VpePlugin;
import org.jboss.tools.vpe.editor.mozilla.EditorLoadWindowListener;
import org.jboss.tools.vpe.editor.mozilla.MozillaEditor;
import org.jboss.tools.vpe.editor.mozilla.MozillaPreview;
import org.jboss.tools.vpe.editor.xpl.SashForm;
import org.jboss.tools.vpe.selbar.SelectionBar;

public class VpeEditorPart extends EditorPart implements ITextEditor,
		ITextEditorExtension, IReusableEditor, IVisualEditor {
	private SashForm container;
	private IEditorPart activeEditor;
	private XModelTreeListener listener;
	XModelObject optionsObject;
	private SelectionBar selectionBar = new SelectionBar();
	private ActivationListener activationListener = new ActivationListener();
	private static final QualifiedName SPLITTER_POSITION_KEY1 = new QualifiedName(
			"", "splitter_position1");
	private static final QualifiedName SPLITTER_POSITION_KEY2 = new QualifiedName(
			"", "splitter_position2");
	private static final QualifiedName SPLITTER_POSITION_KEY3 = new QualifiedName(
			"", "splitter_position3");

	private int controlCount = 0;

	// parent editor
	private EditorPart multiPageEditor;

	// 4 tabs - VISUALSOURCE_MODE, VISUAL_MODE, SOURCE_MODE, PREVIEW_MODE
	private int visualMode = 0;
	/** default web-browser */
	// visible in Preview tab
	private MozillaPreview previewWebBrowser = null;
	// visible in Visual/Source and in Visual tabs
	private MozillaEditor visualEditor = null;
	// visible in Visual/Source and in Source tabs - created in anycase active tab
	private StructuredTextEditor sourceEditor = null;

	/** preview content */
	// preview
	private Composite previewContent = null;
	// visual
	private Composite visualContent = null;
	// source
	private Composite sourceContent = null;

	// returns JSPMultipageEditor for closing by ctrl+F4 and ctrl+shift+F4 keys
	public EditorPart getParentEditor() {
		return multiPageEditor;
	}

	public void close(boolean save) {
		if (sourceEditor != null)
			sourceEditor.close(save);
	}

	public void doRevertToSaved() {
		if (sourceEditor != null)
			sourceEditor.doRevertToSaved();
	}

	public IDocumentProvider getDocumentProvider() {
		if (sourceEditor != null)
			return sourceEditor.getDocumentProvider();
		else {
			return null;
		}
	}

	public IRegion getHighlightRange() {
		if (sourceEditor != null)
			return sourceEditor.getHighlightRange();
		else {
			return null;
		}
	}

	public ISelectionProvider getSelectionProvider() {
		if (sourceEditor != null)
			return sourceEditor.getSelectionProvider();
		else {
			return null;
		}
	}

	public boolean isEditable() {
		if (sourceEditor != null)
			return sourceEditor.isEditable();
		else {
			return false;
		}
	}

	public void removeActionActivationCode(String actionId) {
		if (sourceEditor != null)
			sourceEditor.removeActionActivationCode(actionId);
	}

	public void resetHighlightRange() {
		if (sourceEditor != null)
			sourceEditor.resetHighlightRange();
	}

	public void selectAndReveal(int offset, int length) {
		if (sourceEditor != null)
			sourceEditor.selectAndReveal(offset, length);
	}

	public void setAction(String actionID, IAction action) {
		if (sourceEditor != null)
			sourceEditor.setAction(actionID, action);
	}

	public void setActionActivationCode(String actionId,
			char activationCharacter, int activationKeyCode,
			int activationStateMask) {
		if (sourceEditor != null)
			sourceEditor.setActionActivationCode(actionId, activationCharacter,
					activationKeyCode, activationStateMask);
	}

	public void setHighlightRange(int offset, int length, boolean moveCursor) {
		if (sourceEditor != null)
			sourceEditor.setHighlightRange(offset, length, moveCursor);
	}

	public void showHighlightRangeOnly(boolean showHighlightRangeOnly) {
		if (sourceEditor != null)
			sourceEditor.showHighlightRangeOnly(showHighlightRangeOnly);
	}

	public boolean showsHighlightRangeOnly() {
		if (sourceEditor != null)
			return sourceEditor.showsHighlightRangeOnly();
		else {
			return false;
		}
	}

	public void addRulerContextMenuListener(IMenuListener listener) {
		if (sourceEditor != null)
			sourceEditor.addRulerContextMenuListener(listener);
	}

	public boolean isEditorInputReadOnly() {
		if (sourceEditor != null)
			return sourceEditor.isEditorInputReadOnly();
		else {
			return false;
		}
	}

	public void removeRulerContextMenuListener(IMenuListener listener) {
		if (sourceEditor != null)
			sourceEditor.removeRulerContextMenuListener(listener);
	}

	public void setStatusField(IStatusField field, String category) {
		if (visualMode == VISUAL_MODE) {
			if (field != null) {
				field.setImage(null);
				field.setText(null);
			}
		} else if (sourceEditor != null)
			sourceEditor.setStatusField(field, category);
	}

	public VpeEditorPart(EditorPart multiPageEditor,
			StructuredTextEditor textEditor, boolean visualMode) {
		sourceEditor = textEditor;
		// this.visualMode = visualMode;
		this.multiPageEditor = multiPageEditor;
	}

	public IAction getAction(String actionID) {
		return getSourceEditor().getAction(actionID);
	}

	public VpeEditorPart() {
	}

	public void doSave(IProgressMonitor monitor) {
		if (sourceEditor != null) {
			sourceEditor.doSave(monitor);
		}
	}

	public void doSaveAs() {
		if (sourceEditor != null) {
			sourceEditor.doSaveAs();
			setInput(sourceEditor.getEditorInput());
		}
	}

	public void init(IEditorSite site, IEditorInput input)
			throws PartInitException {
		setSite(site);
		setInput(input);
	}

	public void setInput(IEditorInput input) {
		super.setInput(input);
		if (null != visualEditor && visualEditor.getEditorInput() != null
				&& visualEditor.getEditorInput() != getEditorInput()) {
			visualEditor.setInput(input);
		}
	}

	public boolean isDirty() {
		if (sourceEditor != null) {
			return sourceEditor.isDirty();
		} else {
			return false;
		}
	}

	public boolean isSaveAsAllowed() {
		if (sourceEditor != null) {
			return sourceEditor.isSaveAsAllowed();
		} else {
			return false;
		}
	}

	protected int[] loadSplitterPosition() {
		int[] sizes = new int[3];
		try {
			IEditorInput input = getEditorInput();
			if (!(input instanceof IFileEditorInput))
				return null;

			IFile file = ((IFileEditorInput) input).getFile();
			String s = file.getPersistentProperty(SPLITTER_POSITION_KEY1);
			if (s != null) {
				sizes[0] = Integer.parseInt(s);
			} else
				return null;

			s = file.getPersistentProperty(SPLITTER_POSITION_KEY2);
			if (s != null) {
				sizes[1] = Integer.parseInt(s);
			} else
				return null;

			s = file.getPersistentProperty(SPLITTER_POSITION_KEY3);
			if (s != null) {
				sizes[2] = Integer.parseInt(s);
			} else
				return null;

		} catch (Exception e) {
			VpePlugin.getPluginLog().logError(e);
			return null;
		}
		return sizes;
	}

	protected void saveSplitterPosition(int[] weights) {
		IEditorInput input = getEditorInput();
		if (!(input instanceof IFileEditorInput))
			return;
		IFile file = ((IFileEditorInput) input).getFile();
		try {
			String s = String.valueOf(weights[0]);
			file.setPersistentProperty(SPLITTER_POSITION_KEY1, s);
			s = String.valueOf(weights[1]);
			file.setPersistentProperty(SPLITTER_POSITION_KEY2, s);
			s = String.valueOf(weights[2]);
			file.setPersistentProperty(SPLITTER_POSITION_KEY3, s);
		} catch (Exception e) {
			VpePlugin.getPluginLog().logError(e);
		}
	}

	protected void setVisualMode(String showSelBar, 
		boolean flagSC, boolean flagVC, boolean flagPC) {

		if (null != selectionBar) {
			if (flagPC) {
				getSourceEditor();
			}
			selectionBar.showBar(showSelBar);
		}
		if (null != sourceContent) {
			sourceContent.setVisible(flagSC);
			//Added by Max Areshkau
			//was fixed bug(border which drawed by iflasher doesn't hide on MACOS when we swith
			// to souce view)
			//if(Platform.getOS().equals(Platform.OS_MACOSX)&&controller!=null) {
			//	getVE().getController().visualRefresh();
			//}
		}
		if (null != visualContent) {
			if (flagVC) {
				getVE();
			}
			visualContent.setVisible(flagVC);
		}
		if (null != previewContent) {
			if (flagPC) {
				getWB();
			}
			previewContent.setVisible(flagPC);
		}
	}

	public void setVisualMode(int type) {
		String showSelectionBar = VpePreference.SHOW_SELECTION_TAG_BAR.getValue();
		switch (type) {
		case VISUALSOURCE_MODE:
			setVisualMode(showSelectionBar, true, true, false);
			break;

		case VISUAL_MODE:
			setVisualMode(showSelectionBar, false, true, false);
			break;

		case SOURCE_MODE:
			setVisualMode(showSelectionBar, true, false, false);
			break;

		case PREVIEW_MODE:
			setVisualMode("no", false, false, true);
			break;
		}
		container.layout();
		if (visualMode == SOURCE_MODE && type != SOURCE_MODE) {
			visualMode = type;
			if (getVE().getController() != null) {
				getVE().getController().visualRefresh();
				if (type != PREVIEW_MODE) {
					getVE().getController().sourceSelectionChanged();
				}
			}
		}
		visualMode = type;
	}

	public int getVisualMode() {
		return visualMode;
	}

	public void createPartControl(Composite parent) {
		controlCount++;
		if (controlCount > 1) {
			return;
		}
		// //////////////////////////////////////////////////////////////
		Composite cmpEdTl = new Composite(parent, SWT.NONE);
		GridLayout layoutEdTl = new GridLayout(1, false);
		layoutEdTl.verticalSpacing = 0;
		layoutEdTl.marginHeight = 0;
		layoutEdTl.marginBottom = 3;
		layoutEdTl.marginWidth = 0;
		cmpEdTl.setLayout(layoutEdTl);
		cmpEdTl.setLayoutData(new GridData(GridData.FILL_BOTH));

		Composite cmpEd = new Composite(cmpEdTl, SWT.NATIVE);
		GridLayout layoutEd = new GridLayout(1, false);
		layoutEd.marginBottom = 0;
		layoutEd.marginHeight = 1;
		layoutEd.marginWidth = 0;
		layoutEd.marginRight = 0;
		layoutEd.marginLeft = 1;
		layoutEd.verticalSpacing = 0;
		layoutEd.horizontalSpacing = 0;
		cmpEd.setLayout(layoutEd);
		cmpEd.setLayoutData(new GridData(GridData.FILL_BOTH));
		// /////////////////////////////////////////////////////////////////
		
		container = new SashForm(cmpEd, SWT.VERTICAL);
		container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		// ////////////////////////////////////////////////////
		selectionBar.createToolBarComposite(cmpEdTl, true);
		// ///////////////////////////////////////////////////
		
		getSC();
		getVC();
		getPC();
		int[] weights = loadSplitterPosition();
		if (weights != null) {
			container.setWeights(weights);
		}
		container.addWeightsChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent event) {
				saveSplitterPosition(container.getWeights());
			}
		});
		parent.addControlListener(new ControlListener() {
			public void controlMoved(ControlEvent event) {

			}

			public void controlResized(ControlEvent event) {
				container.layout();
			}
		});
		activeEditor = setupSourceEditor();
		try {
			IWorkbenchWindow window = getSite().getWorkbenchWindow();
			window.getPartService().addPartListener(activationListener);
			window.getShell().addShellListener(activationListener);
		} catch (Exception e) {
			VpePlugin.reportProblem(e);
		}
		// setVisualMode(visualMode);
		// ///////////////////////////////////////
		// ///// Add preference listener
		optionsObject = ModelUtilities.getPreferenceModel().getByPath(
				VpePreference.EDITOR_PATH);
		listener = new XModelTreeListener() {

			public void nodeChanged(XModelTreeEvent event) {
				String showSelectionBar = VpePreference.SHOW_SELECTION_TAG_BAR
						.getValue();
				selectionBar.showBar(showSelectionBar);
			}

			public void structureChanged(XModelTreeEvent event) {
			}

		};
		optionsObject.getModel().addModelTreeListener(listener);
		// ///////////////////////////////////////
		cmpEd.layout();
	}

	public void setFocus() {
		if (activeEditor != null) {
			activeEditor.setFocus();
		}
	}

	public void dispose() {
		super.dispose();
		if (optionsObject != null) {
			optionsObject.getModel().removeModelTreeListener(listener);
		}
		if (activationListener != null) {
			IWorkbenchWindow window = getSite().getWorkbenchWindow();
			window.getPartService().removePartListener(activationListener);
			Shell shell = window.getShell();
			if (shell != null && !shell.isDisposed())
				shell.removeShellListener(activationListener);
			activationListener = null;
		}
		// editor will disposed as part of multipart editor
		if (sourceEditor != null) {
			sourceEditor.dispose();
			sourceEditor = null;
		}

		if (visualEditor != null) {
			visualEditor.dispose();
			visualEditor = null;
		}

		if (previewContent != null) {
			previewContent.dispose();
			previewContent = null;
		}
		
		if (selectionBar != null) {
			selectionBar.dispose();
			selectionBar = null;
		}
	}

	public Object getAdapter(Class adapter) {
		if (sourceEditor != null) {
			return sourceEditor.getAdapter(adapter);
		} else {
			return null;
		}
	}

	private class ActivationListener extends ShellAdapter implements
			IPartListener {
		private IWorkbenchPart fActivePart;
		private boolean fIsHandlingActivation = false;

		public void partActivated(IWorkbenchPart part) {
			fActivePart = part;
			handleActivation();
		}

		public void partBroughtToTop(IWorkbenchPart part) {
		}

		public void partClosed(IWorkbenchPart part) {
		}

		public void partDeactivated(IWorkbenchPart part) {
			fActivePart = null;
		}

		public void partOpened(IWorkbenchPart part) {
		}

		public void shellActivated(ShellEvent e) {
			e.widget.getDisplay().asyncExec(new Runnable() {
				public void run() {
					handleActivation();
				}
			});
		}

		private void handleActivation() {
			if (fIsHandlingActivation)
				return;

			if (fActivePart == multiPageEditor) {
				fIsHandlingActivation = true;
				try {
					if (sourceEditor != null) {
						sourceEditor.safelySanityCheckState(getEditorInput());
					}
				} finally {
					fIsHandlingActivation = false;
				}
			}
		}
	}

	public VpeController getController() {
		return getVE().getController();
	}

	public StructuredTextEditor getSourceEditor() {
		return sourceEditor;
	}
	
	public Composite getSC() {
		if (null != sourceContent) {
			return sourceContent;
		}
		sourceContent = new Composite(container, SWT.NONE);
		sourceContent.setLayout(new FillLayout());
		return sourceContent;
	}
	
	public StructuredTextEditor setupSourceEditor() {
		if (null == sourceEditor) {
			sourceEditor = new StructuredTextEditor() {
				public void safelySanityCheckState(IEditorInput input) {
					super.safelySanityCheckState(input);
				}
			};
		}
		sourceEditor.addPropertyListener(new IPropertyListener() {
			public void propertyChanged(Object source, int propId) {
				if (propId == IWorkbenchPartConstants.PROP_TITLE) {
					VpeEditorPart.this.setPartName(sourceEditor.getTitle());
				}
				VpeEditorPart.this.firePropertyChange(propId);
			}
		});
		try {
			sourceEditor.init(getEditorSite(), getEditorInput());
		} catch (PartInitException e) {
			VpePlugin.reportProblem(e);
		}
		sourceEditor.createPartControl(getSC());
		getSC().addListener(SWT.Activate, new Listener() {
			public void handleEvent(Event event) {
				if (event.type == SWT.Activate) {
					if (activeEditor != sourceEditor) {
						activeEditor = sourceEditor;
						setFocus();
					}
				}
			}
		});
		return sourceEditor;
	}

	public Composite getVC() {
		if (null != visualContent) {
			return visualContent;
		}
		visualContent = new Composite(container, SWT.NONE);
		visualContent.setLayout(new FillLayout());
		return visualContent;
	}
	
	public MozillaEditor getVE() {
		if (null != visualEditor) {
			return visualEditor;
		}
		visualEditor = new MozillaEditor();
		try {
			visualEditor.init(getEditorSite(), getEditorInput());
		} catch (Exception e) {
			VpePlugin.reportProblem(e);
		}
		if (null == visualEditor) {
			return visualEditor;
		}
		visualEditor.setEditorLoadWindowListener(new EditorLoadWindowListener() {
			public void load() {
				visualEditor.setEditorLoadWindowListener(null);
				visualEditor.setController(new VpeController(VpeEditorPart.this));
				selectionBar.setVpeController(visualEditor.getController());
				visualEditor.getController().setSelectionBarController(selectionBar);
				try {
					visualEditor.getController().init(sourceEditor, visualEditor);
				} catch (Exception e) {
					VpePlugin.reportProblem(e);
				}
			}
		});
		visualEditor.createPartControl(getVC());
		getVC().addListener(SWT.Activate, new Listener() {
			public void handleEvent(Event event) {
				if (event.type == SWT.Activate) {
					if (null != visualEditor && activeEditor != visualEditor) {
						activeEditor = visualEditor;
						setFocus();
					}
				}
			}
		});
		return visualEditor;
	}

	public Composite getPC() {
		if (null != previewContent) {
			return previewContent;
		}
		previewContent = new Composite(container, SWT.NONE);
		previewContent.setLayout(new FillLayout());
		return previewContent;
	}

	public MozillaPreview getWB() {
		if (null != previewWebBrowser) {
			return previewWebBrowser;
		}
		previewWebBrowser = new MozillaPreview(this, sourceEditor);
		try {
			previewWebBrowser.init(getEditorSite(), getEditorInput());
		} catch (Exception e) {
			VpePlugin.reportProblem(e);
		}
		if (null == previewWebBrowser) {
			return previewWebBrowser;
		}
		previewWebBrowser.setEditorLoadWindowListener(new EditorLoadWindowListener() {
			public void load() {
				previewWebBrowser.setEditorLoadWindowListener(null);
				previewWebBrowser.buildDom();
			}
		});
		previewWebBrowser.createPartControl(getPC());
		getPC().addListener(SWT.Activate, new Listener() {
			public void handleEvent(Event event) {
				if (event.type == SWT.Activate) {
					if (null != previewWebBrowser && activeEditor != previewWebBrowser) {
						activeEditor = previewWebBrowser;
						setFocus();
					}
				}
			}
		});
		return previewWebBrowser;
	}

}
