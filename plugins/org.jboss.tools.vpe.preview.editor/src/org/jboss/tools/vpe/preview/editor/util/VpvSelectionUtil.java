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

package org.jboss.tools.vpe.preview.editor.util;

import org.eclipse.swt.graphics.Point;
import org.jboss.tools.jst.web.ui.internal.editor.util.NodesManagingUtil;
import org.jboss.tools.vpe.editor.util.SelectionUtil;
import org.jboss.tools.vpe.preview.editor.context.VpvPageContext;
import org.w3c.dom.Node;

/**
 * Utility class for selection manipulating.
 * 
 * @author S.Dzmitrovich
 */
public class VpvSelectionUtil {
	
	/**
	 * Method is used to select the last selected node.
	 * 
	 * @param pageContext
	 *            VpePageContext object
	 * @return nsIDOMNode the last selected node
	 */
	public static Node getSelectedNode(VpvPageContext pageContext) {
		final Point point = SelectionUtil.getSourceSelectionRange(pageContext.getEditPart().getController().getSourceEditor());
		return SelectionUtil.getNodeBySourcePosition(pageContext.getEditPart().getController().getSourceEditor(), point.x);
	}

	
	/**
	 * select node completely
	 * 
	 * @param pageContext
	 * @param node
	 */
	public static void setSourceSelection(VpvPageContext pageContext, Node node) {

		int start = NodesManagingUtil.getStartOffsetNode(node);
		int length = NodesManagingUtil.getNodeLength(node);

		pageContext.getEditPart().getController().getSourceEditor().getTextViewer()
				.setSelectedRange(start, length);
		pageContext.getEditPart().getController().getSourceEditor().getTextViewer().revealRange(
				start, length);
	}

	/**
	 * 
	 * @param pageContext
	 * @param node
	 * @param offset
	 * @param length
	 */
	public static void setSourceSelection(VpvPageContext pageContext, 
			Node node, int offset, int length) {
		int start = NodesManagingUtil.getStartOffsetNode(node);
		setSourceSelection(pageContext, start + offset, length);
	}

	/**
	 * 
	 * @param pageContext
	 * @param node
	 * @param offset
	 */
	public static void setSourceSelection(VpvPageContext pageContext, Node node, int offset) {
		int start = NodesManagingUtil.getStartOffsetNode(node);
		pageContext.getEditPart().getController().getSourceEditor().getTextViewer()
				.getTextWidget().setSelection(start + offset);
	}

	/**
	 * 
	 * @param pageContext
	 * @param offset
	 * @param length
	 */
	public static void setSourceSelection(VpvPageContext pageContext,
			int offset, int length) {
		pageContext.getEditPart().getController().getSourceEditor().getTextViewer().
				setSelectedRange(offset, length);
		pageContext.getEditPart().getController().getSourceEditor().getTextViewer().
				revealRange(offset, length);
	}


}