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
package org.eclipse.jdi.internal.event;


import java.io.DataInputStream;
import java.io.IOException;

import org.eclipse.jdi.internal.MirrorImpl;
import org.eclipse.jdi.internal.VirtualMachineImpl;
import org.eclipse.jdi.internal.request.RequestID;

import com.sun.jdi.event.AccessWatchpointEvent;

/**
 * this class implements the corresponding interfaces
 * declared by the JDI specification. See the com.sun.jdi package
 * for more information.
 *
 */
public class AccessWatchpointEventImpl extends WatchpointEventImpl implements AccessWatchpointEvent {
	/** Jdwp Event Kind. */
	public static final byte EVENT_KIND = EVENT_FIELD_ACCESS;
	
	/**
	 * Creates new AccessWatchpointEventImpl.
	 */
	protected AccessWatchpointEventImpl(VirtualMachineImpl vmImpl, RequestID requestID) {
		super("AccessWatchpointEvent", vmImpl, requestID); //$NON-NLS-1$
	}

	/**
	 * @return Creates, reads and returns new EventImpl, of which requestID has already been read.
	 */
	public static WatchpointEventImpl read(MirrorImpl target, RequestID requestID, DataInputStream dataInStream) throws IOException {
		VirtualMachineImpl vmImpl = target.virtualMachineImpl();
		AccessWatchpointEventImpl event = new AccessWatchpointEventImpl(vmImpl, requestID);
		event.readWatchpointEventFields(target, dataInStream);
		return event;
   	}
}
