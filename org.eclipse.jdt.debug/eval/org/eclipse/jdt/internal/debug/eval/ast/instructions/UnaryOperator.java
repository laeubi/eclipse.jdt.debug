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

public abstract class UnaryOperator extends CompoundInstruction {
	protected int fExpressionTypeId;
	
	public UnaryOperator(int expressionTypeId, int start) {
		super(start);
		fExpressionTypeId= expressionTypeId;
	}
}
