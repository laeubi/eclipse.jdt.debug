/*******************************************************************************
 * Copyright (c) 2000, 2003 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.jdt.internal.debug.eval.ast.instructions;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.debug.core.IJavaArray;
import org.eclipse.jdt.debug.core.IJavaArrayType;

public class ArrayInitializerInstruction extends ArrayInstruction {

	private String fTypeSignature;
	
	private int fDimensions;
	
	private int fLength;

	/**
	 * Constructor for ArrayInitializerInstruction.
	 * @param start
	 */
	public ArrayInitializerInstruction(String typeSignature, int length, int dimensions, int start) {
		super(start);
		fTypeSignature = typeSignature;
		fDimensions = dimensions;
		fLength = length;
	}

	/*
	 * @see Instruction#execute()
	 */
	public void execute() throws CoreException {
		
		IJavaArrayType arrayType = getArrayType(fTypeSignature.replace('/','.'), fDimensions);
		IJavaArray array = arrayType.newInstance(fLength);
		
		for (int i = fLength - 1; i >= 0; i--) {
			array.setValue(i, popValue());
		}
		
		push(array);
		
	}

	public String toString() {
		return InstructionsEvaluationMessages.getString("ArrayInitializerInstruction.array_initializer_1"); //$NON-NLS-1$
	}

}
