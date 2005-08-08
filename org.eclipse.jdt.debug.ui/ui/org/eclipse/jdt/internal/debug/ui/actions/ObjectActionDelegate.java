/*******************************************************************************
 * Copyright (c) 2000, 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.jdt.internal.debug.ui.actions;


import org.eclipse.jdt.internal.debug.ui.JDIDebugUIPlugin;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.ui.IActionDelegate2;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;

public abstract class ObjectActionDelegate implements IObjectActionDelegate, IActionDelegate2 {
	
	IWorkbenchPart fPart = null;
	
	/**
	 * @see IObjectActionDelegate#setActivePart(IAction, IWorkbenchPart)
	 */
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		fPart = targetPart;
	}
	
	/**
	 * @see IActionDelegate#selectionChanged(IAction, ISelection)
	 */
	public void selectionChanged(IAction action, ISelection sel) {
	}
	
	protected IStructuredSelection getCurrentSelection() {
		IWorkbenchPage page= JDIDebugUIPlugin.getActivePage();
		if (page != null) {
			ISelection selection= page.getSelection();
			if (selection instanceof IStructuredSelection) {
				return (IStructuredSelection)selection;
			}	
		}
		return null;
	}
	
	/**
	 * Displays the given error message in the status line.
	 * 
	 * @param message
	 */
	protected void showErrorMessage(String message) {
		if (fPart instanceof IViewPart) {
			IViewSite viewSite = ((IViewPart)fPart).getViewSite();
			IStatusLineManager manager = viewSite.getActionBars().getStatusLineManager();
			manager.setErrorMessage(message);
			Display.getCurrent().beep();
		}
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.ui.IActionDelegate2#dispose()
	 */
	public void dispose() {
		fPart = null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IActionDelegate2#init(org.eclipse.jface.action.IAction)
	 */
	public void init(IAction action) {
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IActionDelegate2#runWithEvent(org.eclipse.jface.action.IAction, org.eclipse.swt.widgets.Event)
	 */
	public void runWithEvent(IAction action, Event event) {
		run(action);
	}
	
	protected void typeHierarchyError() {
		showErrorMessage(ActionMessages.ObjectActionDelegate_Unable_to_display_type_hierarchy__The_selected_source_element_is_not_contained_in_the_workspace__1); 
	}

}