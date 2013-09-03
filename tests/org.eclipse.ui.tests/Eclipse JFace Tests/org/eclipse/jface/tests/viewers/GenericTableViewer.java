/*******************************************************************************
 * Copyright (c) 2013 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 ******************************************************************************/

package org.eclipse.jface.tests.viewers;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TableItem;
import org.junit.Test;

/**
 * @since 3.5
 *
 */
public class GenericTableViewer extends ViewerTestCase{

	private TableViewer<TestModel, List<TestModel>> tableViewer;

	/**
	 * @param name
	 */
	public GenericTableViewer(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Test
	public void test() {
		TableItem[] items = tableViewer.getTable().getItems();
		assertEquals(4, items.length);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.tests.viewers.ViewerTestCase#createViewer(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected StructuredViewer createViewer(Composite parent) {
		tableViewer = new TableViewer<TestModel, List<TestModel>>(parent);
		tableViewer.setContentProvider(new IStructuredContentProvider<Object, Object>() {

			public void dispose() {
				// TODO Auto-generated method stub

			}

			public void inputChanged(Viewer<Object> viewer, Object oldInput,
					Object newInput) {

			}

			public Object[] getElements(Object inputElement) {
				return ((List)inputElement).toArray();
			}

		});

		return tableViewer;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.tests.viewers.ViewerTestCase#setInput()
	 */
	@Override
	protected void setInput() {
		List<TestModel> testModels = new ArrayList<TestModel>();
		testModels.add(new TestModel(0, 0));
		testModels.add(new TestModel(0, 0));
		testModels.add(new TestModel(0, 0));
		testModels.add(new TestModel(0, 0));
		tableViewer.setInput(testModels);
	}

	class SubTestModel extends TestModel{

		/**
		 * @param numLevels
		 * @param numChildren
		 */
		public SubTestModel(int numLevels, int numChildren) {
			super(numLevels, numChildren);
		}

	}
}
