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
package com.sun.jdi.connect;

 
import java.io.IOException;
import java.util.Map;

import com.sun.jdi.VirtualMachine;

public interface AttachingConnector extends Connector {
	public VirtualMachine attach(Map arg1) throws IOException, IllegalConnectorArgumentsException;
}
