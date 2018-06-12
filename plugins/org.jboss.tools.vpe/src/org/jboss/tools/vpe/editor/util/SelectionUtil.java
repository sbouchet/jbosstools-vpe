/******************************************************************************* 
 * Copyright (c) 2007 Red Hat, Inc.
 * Distributed under license by Red Hat, Inc. All rights reserved.
 * This program is made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Red Hat, Inc. - initial API and implementation
 ******************************************************************************/

package org.jboss.tools.vpe.editor.util;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.swt.graphics.Point;
import org.eclipse.wst.sse.core.StructuredModelManager;
import org.eclipse.wst.sse.core.internal.provisional.IStructuredModel;
import org.eclipse.wst.sse.core.internal.provisional.IndexedRegion;
import org.eclipse.wst.sse.ui.StructuredTextEditor;
import org.w3c.dom.Node;

/**
 * Utility class for selection manipulating.
 * 
 * @author S.Dzmitrovich
 */
public class SelectionUtil {
	private static Node getSourceNodeByPosition(IStructuredModel model,
			int position) {
		// if we state at the end of text node, model will return
		// for us next node or null if on page exists only text node,
		// but we still in the end of text node, so we should check
		// this situation

		// get source node by position
		// see jbide-3163
		IndexedRegion node = model.getIndexedRegion(position);
		IndexedRegion possibleNode = position >= 1 ? model
				.getIndexedRegion(position - 1) : null;
		if (node == null && position >= 1) {
			node = possibleNode;
		} else if ((node != null)
				&& (((Node) node).getNodeType() != Node.TEXT_NODE)
				&& (node.getStartOffset() == position) && (position >= 1)) {
			// check for such situation #text<h1></h1>
			node = possibleNode;
		} else if ((node != null)
				&& (((Node) node).getNodeType() != Node.TEXT_NODE)
				&& (possibleNode != null)
				&& ((Node) possibleNode).getNodeType() == Node.TEXT_NODE) {
			node = possibleNode;
		}

		return (Node) node;
	}

	public static Node getNodeBySourcePosition(
			StructuredTextEditor sourceEditor, int position) {

		IDocument document = sourceEditor.getTextViewer().getDocument();

		IStructuredModel model = null;

		Node node = null;
		try {
			model = StructuredModelManager.getModelManager()
					.getExistingModelForRead(document);

			node = (Node) model.getIndexedRegion(position);

		} finally {
			if (model != null) {
				model.releaseFromRead();
			}
		}

		return node;
	}

	/**
	 * Returns sourceSelectionRange
	 * 
	 * @param sourceEditor
	 *            StructuredTextEditor object
	 * @return a <code>Point</code> with x as the offset 
	 * and y as the length of the current selection or <code>null</code>
	 */
	public static Point getSourceSelectionRange(StructuredTextEditor sourceEditor) {
		ITextViewer textViewer = sourceEditor.getTextViewer();
		if (textViewer != null) {
			return textViewer.getSelectedRange();
		}
		return null;
	}
}