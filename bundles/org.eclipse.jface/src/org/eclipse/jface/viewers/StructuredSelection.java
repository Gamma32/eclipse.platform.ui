/*******************************************************************************
 * Copyright (c) 2000, 2013 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.jface.viewers;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.core.runtime.Assert;

/**
 * A concrete implementation of the <code>IStructuredSelection</code> interface,
 * suitable for instantiating.
 * <p>
 * This class is not intended to be subclassed.
 * </p>
 * @param <E>
 * @noextend This class is not intended to be subclassed by clients.
 */
public class StructuredSelection<E> implements IStructuredSelection<E> {

    /**
     * The element that make up this structured selection.
     */
    private E[] elements;

    /**
     * The element comparer, or <code>null</code>
     */
	private IElementComparer comparer;

    /**
     * The canonical empty selection. This selection should be used instead of
     * <code>null</code>.
     */
    public static final StructuredSelection EMPTY = new StructuredSelection();

    /**
     * Creates a new empty selection.
     * See also the static field <code>EMPTY</code> which contains an empty selection singleton.
     *
     * @see #EMPTY
     */
    public StructuredSelection() {
    }

    /**
     * Creates a structured selection from the given elements.
     * The given element array must not be <code>null</code>.
     *
     * @param elements an array of elements
     */
    public StructuredSelection(E[] elements) {
    	Assert.isNotNull(elements);
        this.elements = (E[]) new Object[elements.length];
        System.arraycopy(elements, 0, this.elements, 0, elements.length);
    }

    /**
     * Creates a structured selection containing a single object.
     * The object must not be <code>null</code>.
     *
     * @param element the element
     */
    public StructuredSelection(E element) {
        Assert.isNotNull(element);
        elements = (E[]) new Object[] { element };
    }

    /**
     * Creates a structured selection from the given <code>List</code>.
     * @param elements list of selected elements
     */
    public StructuredSelection(List<E> elements) {
    	this(elements, null);
    }

    /**
	 * Creates a structured selection from the given <code>List</code> and
	 * element comparer. If an element comparer is provided, it will be used to
	 * determine equality between structured selection objects provided that
	 * they both are based on the same (identical) comparer. See bug
	 *
	 * @param elements
	 *            list of selected elements
	 * @param comparer
	 *            the comparer, or null
	 * @since 3.4
	 */
	public StructuredSelection(List<E> elements, IElementComparer comparer) {
        Assert.isNotNull(elements);
        this.elements = (E[]) elements.toArray();
        this.comparer = comparer;
	}

	/**
     * Returns whether this structured selection is equal to the given object.
     * Two structured selections are equal if they contain the same elements
     * in the same order.
     *
     * @param o the other object
     * @return <code>true</code> if they are equal, and <code>false</code> otherwise
     */
    @Override
	public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        //null and other classes
        if (!(o instanceof StructuredSelection)) {
            return false;
        }
        StructuredSelection<E> s2 = (StructuredSelection<E>) o;

        // either or both empty?
        if (isEmpty()) {
            return s2.isEmpty();
        }
        if (s2.isEmpty()) {
            return false;
        }

        boolean useComparer = comparer != null && comparer == s2.comparer;

        //size
        int myLen = elements.length;
        if (myLen != s2.elements.length) {
            return false;
        }
        //element comparison
        for (int i = 0; i < myLen; i++) {
        	if (useComparer) {
                if (!comparer.equals(elements[i], s2.elements[i])) {
                    return false;
                }
        	} else {
	            if (!elements[i].equals(s2.elements[i])) {
	                return false;
	            }
        	}
        }
        return true;
    }

    /* (non-Javadoc)
     * Method declared in IStructuredSelection.
     */
    public E getFirstElement() {
        return isEmpty() ? null : elements[0];
    }

    /* (non-Javadoc)
     * Method declared in ISelection.
     */
    public boolean isEmpty() {
        return elements == null || elements.length == 0;
    }

    /* (non-Javadoc)
     * Method declared in IStructuredSelection.
     */
    public Iterator<E> iterator() {
        return Arrays.<E>asList(elements == null ? (E[])new Object[0] : elements)
                .iterator();
    }

    /* (non-Javadoc)
     * Method declared in IStructuredSelection.
     */
    public int size() {
        return elements == null ? 0 : elements.length;
    }

    /* (non-Javadoc)
     * Method declared in IStructuredSelection.
     */
    public E[] toArray() {
        return (E[]) (elements == null ? new Object[0] : (Object[]) elements.clone());
    }

    /* (non-Javadoc)
     * Method declared in IStructuredSelection.
     */
    public List<E> toList() {
        return Arrays.<E>asList(elements == null ? (E[])new Object[0] : elements);
    }

    /**
     * Internal method which returns a string representation of this
     * selection suitable for debug purposes only.
     *
     * @return debug string
     */
    @Override
	public String toString() {
        return isEmpty() ? JFaceResources.getString("<empty_selection>") : toList().toString(); //$NON-NLS-1$
    }
}
