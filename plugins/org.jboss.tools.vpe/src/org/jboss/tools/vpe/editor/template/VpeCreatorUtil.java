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
package org.jboss.tools.vpe.editor.template;

import java.io.IOException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.wst.sse.core.internal.provisional.IModelManager;
import org.eclipse.wst.sse.core.internal.provisional.StructuredModelManager;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocument;
import org.eclipse.wst.xml.core.internal.provisional.document.IDOMModel;
import org.eclipse.wst.xml.core.internal.provisional.document.IDOMNode;
import org.jboss.tools.vpe.VpePlugin;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class VpeCreatorUtil {
	public static final int FACET_TYPE_NONE = 0;
	public static final int FACET_TYPE_HEADER = 1;
	public static final int FACET_TYPE_FOOTER = 2;
	public static final int FACET_TYPE_BODY = 3;
	public static final int FACET_TYPE_NAVIGATION = 4;
	public static final int FACET_TYPE_CAPTION = 5;

	public static boolean isFacet(Node node) {
		if (node != null && node.getNodeType() == Node.ELEMENT_NODE) {
			return "facet".equals(node.getLocalName()); //$NON-NLS-1$
		}
		return false;
	}

	public static String getFacetName(Node node) {
		Element nodeElement = (Element)node;
		String nameAttrName = "name"; //$NON-NLS-1$
		if (node != null && node.getNodeType() == Node.ELEMENT_NODE && 
				nodeElement.hasAttribute(nameAttrName)) {
			return nodeElement.getAttribute(nameAttrName);
		}
		return null;
	}

	public static int getFacetType(Node node) {
		String value = getFacetName(node);
		if (value != null) {
			if ("header".equals(value)) { //$NON-NLS-1$
				return FACET_TYPE_HEADER;
			} else if ("footer".equals(value)) { //$NON-NLS-1$
				return FACET_TYPE_FOOTER;
			} else if ("body".equals(value)) { //$NON-NLS-1$
				return FACET_TYPE_BODY;
			} else if ("navigation".equals(value)) { //$NON-NLS-1$
				return FACET_TYPE_NAVIGATION;
			} else if ("caption".equals(value)) { //$NON-NLS-1$
				return FACET_TYPE_CAPTION;
			}
		}
		return FACET_TYPE_NONE;
	}

	public static boolean isInclude(Node node) {
		if (node != null && node.getNodeType() == Node.ELEMENT_NODE) {
			return node.getNodeName().indexOf("jsp:include") >=0 || node.getNodeName().indexOf("jsp:directive.include")>=0; //$NON-NLS-1$ //$NON-NLS-2$
		}
		return false;
	}
	
	/**
	 * Releases document model from read
	 * @see VpeCreatorUtil#getDocumentForRead(IFile)
	 * @param document
	 */
	public static void releaseDocumentFromRead(Document document) {
		if (document instanceof IDOMNode) {
			IDOMModel wtpModel = ((IDOMNode)document).getModel();
			if (wtpModel != null) {
				wtpModel.releaseFromRead();
			}
		}
	}

	/**
	 * Return dom document for read, document shoud be released from read
	 * @see VpeCreatorUtil#releaseDocumentFromRead(Document)
	 * @param file
	 * @return dom document for read
	 */
	public static Document getDocumentForRead(IFile file) {
		IDOMModel wtpModel = null;
		try {
			wtpModel = (IDOMModel)StructuredModelManager.getModelManager().getModelForRead(file);
			if (wtpModel != null) return wtpModel.getDocument();
		} catch(IOException e) {
			VpePlugin.getPluginLog().logError(e);
		} catch(CoreException e) {
			VpePlugin.getPluginLog().logError(e);
		}
		return null;
	}
	/**
	 * Return dom document for read, document shoud be released from read
	 * @see VpeCreatorUtil#releaseDocumentFromRead(Document)
	 * @param file
	 * @return dom document for read
	 */
	public static Document getDocumentForRead(String content) {
		IDOMModel wtpModel = null;

		IModelManager modelManager = StructuredModelManager.getModelManager();

		wtpModel = (IDOMModel) modelManager
				.createUnManagedStructuredModelFor("org.eclipse.wst.html.core.htmlsource"); //$NON-NLS-1$
		IStructuredDocument document = wtpModel.getStructuredDocument();
		document.set(content);
		if (wtpModel != null)
			return wtpModel.getDocument();
		return null;
	}

	public static void setAttributes(Element visualElement, Element sourceElement, VpeAttributeInfo[] attrsInfo) {
		if (attrsInfo != null) {
			for (int i = 0; i < attrsInfo.length; i++) {
				attrsInfo[i].setAttribure(visualElement, sourceElement);
			}
		}
	}
	
	public static void setAttributes(Element visualElement, VpeAttributeInfo[] attrsInfo) {
		setAttributes(visualElement, null, attrsInfo);
	}

	public static Node getTextChildNode(Node sourceElement) {
		// ignore empty text
		NodeList children = sourceElement.getChildNodes();
		for (int i=0; i<children.getLength(); i++) {
			Node child = children.item(i);
			if (child.getNodeType() == Node.TEXT_NODE && child.getNodeValue().trim().length()>0) {
				return child;
			}
		}
		return null;
	}
}
