/*******************************************************************************
 * Copyright (c) 2006, 2013 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/

package org.eclipse.jface.viewers;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;

/**
 * The ColumnLabelProvider is the label provider for viewers
 * that have column support such as {@link TreeViewer} and
 * {@link TableViewer}
 *
 * <p><b>This classes is intended to be subclassed</b></p>
 * @param <E> Type of an element of the model
 * @param <I> Type of the input
 *
 * @since 3.3
 *
 */
public class ColumnLabelProvider<E,I> extends CellLabelProvider<E,I> implements
		IFontProvider, IColorProvider, ILabelProvider<E> {

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.CellLabelProvider#update(org.eclipse.jface.viewers.ViewerCell)
	 */
	@Override
	public void update(ViewerCell<E> cell) {
		E element = cell.getElement();
		cell.setText(getText(element));
		Image image = getImage(element);
		cell.setImage(image);
		cell.setBackground(getBackground(element));
		cell.setForeground(getForeground(element));
		cell.setFont(getFont(element));

	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IFontProvider#getFont(java.lang.Object)
	 */
	public Font getFont(Object element) {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IColorProvider#getBackground(java.lang.Object)
	 */
	public Color getBackground(Object element) {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IColorProvider#getForeground(java.lang.Object)
	 */
	public Color getForeground(Object element) {
		return null;
	}


	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ILabelProvider#getImage(java.lang.Object)
	 */
	public Image getImage(E element) {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ILabelProvider#getText(java.lang.Object)
	 */
	public String getText(E element) {
		return element == null ? "" : element.toString();//$NON-NLS-1$
	}

}
