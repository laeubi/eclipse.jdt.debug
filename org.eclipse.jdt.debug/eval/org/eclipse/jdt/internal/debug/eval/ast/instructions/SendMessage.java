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


import java.text.MessageFormat;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.debug.core.IJavaObject;
import org.eclipse.jdt.debug.core.IJavaValue;
import org.eclipse.jdt.debug.core.IJavaVariable;
import org.eclipse.jdt.internal.debug.core.JDIDebugPlugin;
 
/**
 * Sends an message to an instance. The arguments are on the
 * stack in reverse order, followed by the receiver.
 * Pushes the result, if any, onto the stack
 */
public class SendMessage extends CompoundInstruction {
	
	private int fArgCount;
	private String fSelector;
	private String fSignature;
	private String fDeclaringType;
	
	public SendMessage(String selector, String signature, int argCount, String declaringType, int start) {
		super(start);
		fArgCount= argCount;
		fSelector= selector;
		fSignature= signature;
		fDeclaringType= declaringType;
	}
	
	public void execute() throws CoreException {
		IJavaValue[] args = new IJavaValue[fArgCount];
		// args are in reverse order
		for (int i= fArgCount - 1; i >= 0; i--) {
			args[i] = (IJavaValue)popValue();
		}
		Object receiver = pop();
		IJavaValue result = null;
		
		if (receiver instanceof IJavaVariable) {
			receiver = ((IJavaVariable) receiver).getValue();	
		}
		
		if (receiver instanceof IJavaObject) {
			result = ((IJavaObject)receiver).sendMessage(fSelector, fSignature, args, getContext().getThread(), fDeclaringType);
		} else {
			throw new CoreException(new Status(Status.ERROR, JDIDebugPlugin.getUniqueIdentifier(), Status.OK, InstructionsEvaluationMessages.getString("SendMessage.Attempt_to_send_a_message_to_a_non_object_value_1"), null)); //$NON-NLS-1$
		}
		setLastValue(result);
		if (!fSignature.endsWith(")V")) { //$NON-NLS-1$
			// only push the result if not a void method
			push(result);
		}
	}
	
	public String toString() {
		return MessageFormat.format(InstructionsEvaluationMessages.getString("SendMessage.send_message_{0}_{1}_2"), new String[]{fSelector,fSignature}); //$NON-NLS-1$
	}
}

