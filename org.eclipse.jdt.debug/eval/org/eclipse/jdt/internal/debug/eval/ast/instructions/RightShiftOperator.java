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

import org.eclipse.jdt.debug.core.IJavaPrimitiveValue;
import org.eclipse.jdt.debug.core.IJavaValue;

public class RightShiftOperator extends BinaryOperator {
	public RightShiftOperator(int resultId, int leftTypeId, int rightTypeId, int start) {
		this(resultId, leftTypeId, rightTypeId, false, start);
	}

	protected RightShiftOperator(int resultId, int leftTypeId, int rightTypeId, boolean isAssignmentOperator, int start) {
		super(resultId, leftTypeId, rightTypeId, isAssignmentOperator, start);
	}

	/*
	 * @see BinaryOperator#getBooleanResult(IJavaValue, IJavaValue)
	 */
	protected boolean getBooleanResult(IJavaValue leftOperand, IJavaValue rightOperand) {
		return false;
	}

	/*
	 * @see BinaryOperator#getDoubleResult(IJavaValue, IJavaValue)
	 */
	protected double getDoubleResult(IJavaValue leftOperand, IJavaValue rightOperand) {
		return 0;
	}

	/*
	 * @see BinaryOperator#getFloatResult(IJavaValue, IJavaValue)
	 */
	protected float getFloatResult(IJavaValue leftOperand, IJavaValue rightOperand) {
		return 0;
	}

	/*
	 * @see BinaryOperator#getIntResult(IJavaValue, IJavaValue)
	 */
	protected int getIntResult(IJavaValue leftOperand, IJavaValue rightOperand) {
		// unary type promotion on both operands see 5.6.1 and 15.18
		switch (fRightTypeId) {
			case T_long :
				return ((IJavaPrimitiveValue) leftOperand).getIntValue() >> ((IJavaPrimitiveValue) rightOperand).getLongValue();
			case T_int :
			case T_short :
			case T_byte :
			case T_char :
				return ((IJavaPrimitiveValue) leftOperand).getIntValue() >> ((IJavaPrimitiveValue) rightOperand).getIntValue();
			default :
				return 0;
		}
	}

	/*
	 * @see BinaryOperator#getLongResult(IJavaValue, IJavaValue)
	 */
	protected long getLongResult(IJavaValue leftOperand, IJavaValue rightOperand) {
		// unary type promotion on both operands see 5.6.1 and 15.18
		switch (fRightTypeId) {
			case T_long :
				return ((IJavaPrimitiveValue) leftOperand).getLongValue() >> ((IJavaPrimitiveValue) rightOperand).getLongValue();
			case T_int :
			case T_short :
			case T_byte :
			case T_char :
				return ((IJavaPrimitiveValue) leftOperand).getLongValue() >> ((IJavaPrimitiveValue) rightOperand).getIntValue();
			default :
				return 0;
		}
	}

	/*
	 * @see BinaryOperator#getStringResult(IJavaValue, IJavaValue)
	 */
	protected String getStringResult(IJavaValue leftOperand, IJavaValue rightOperand) {
		return null;
	}

	protected int getInternResultType() {
		// unary type promotion on both operands see 5.6.1 and 15.18
		return getUnaryPromotionType(fLeftTypeId);
	}

	public String toString() {
		return InstructionsEvaluationMessages.getString("RightShiftOperator._>>___operator_1"); //$NON-NLS-1$
	}

}
