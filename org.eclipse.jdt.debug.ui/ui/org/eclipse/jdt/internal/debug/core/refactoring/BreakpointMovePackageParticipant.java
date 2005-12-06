/*******************************************************************************
 * Copyright (c) 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.jdt.internal.debug.core.refactoring;

import java.util.List;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.debug.core.IJavaBreakpoint;
import org.eclipse.jdt.internal.debug.ui.BreakpointUtils;

/**
 * Breakpoint participant for package move.
 * 
 * @since 3.2
 */
public class BreakpointMovePackageParticipant extends BreakpointMoveParticipant {

	/* (non-Javadoc)
	 * @see org.eclipse.jdt.internal.debug.core.refactoring.BreakpointRenameParticipant#accepts(org.eclipse.jdt.core.IJavaElement)
	 */
	protected boolean accepts(IJavaElement element) {
		return element instanceof IPackageFragment && getArguments().getDestination() instanceof IPackageFragmentRoot;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jdt.internal.debug.core.refactoring.BreakpointMoveParticipant#gatherChanges(org.eclipse.core.resources.IMarker[], java.util.List)
	 */
	protected void gatherChanges(IMarker[] markers, List changes) throws CoreException, OperationCanceledException {
		IPackageFragmentRoot destRoot = (IPackageFragmentRoot)getDestination();
		for (int i = 0; i < markers.length; i++) {
			IMarker marker = markers[i];
			IBreakpoint breakpoint = getBreakpoint(marker);
			if (breakpoint instanceof IJavaBreakpoint) {
				IJavaBreakpoint javaBreakpoint = (IJavaBreakpoint) breakpoint;
				IType breakpointType = BreakpointUtils.getType(javaBreakpoint);
				if (breakpointType != null) {
					String breakpointPackageName = breakpointType.getPackageFragment().getElementName();
					IPackageFragment destBreakpointPackage = destRoot.getPackageFragment(breakpointPackageName);
					ICompilationUnit cu = destBreakpointPackage.getCompilationUnit(breakpointType.getCompilationUnit().getElementName());
					String[] typeNames = breakpointType.getTypeQualifiedName().split("\\$"); //$NON-NLS-1$
					IType destType = cu.getType(typeNames[0]);
					for (int j = 1; j < typeNames.length; j++) {
						destType = destType.getType(typeNames[j]);
					}
					changes.add(createTypeChange(javaBreakpoint, destType, breakpointType));
				}
			}
		}
	}
	
}