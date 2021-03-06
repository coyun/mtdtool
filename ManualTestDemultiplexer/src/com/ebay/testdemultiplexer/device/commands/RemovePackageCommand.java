/**
 * Copyright 2012-2013 eBay Software Foundation - All Rights Reserved
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * ============================================================================
 * 
 * @author Benjamin Yarger <byarger@ebay.com>
 * 
 * Class: RemovePackageCommand
 * 
 * Description: 
 * Definition of an un-install apk command for any given device.
 */

package com.ebay.testdemultiplexer.device.commands;

import com.ebay.testdemultiplexer.connection.TestDevice;
import com.ebay.testdemultiplexer.device.commands.recorder.CommandDeserializer;
import com.ebay.testdemultiplexer.device.commands.recorder.CommandSerializer;
import com.ebay.testdemultiplexer.util.TestDemultiplexerConstants;

public class RemovePackageCommand extends DeviceCommand implements 
	CommandDeserializer, CommandSerializer {

	/** Serialization key identifier. */
	public static final String SERIALIZED_KEY = "REMOVE_PACKAGE_COMMAND";
	
	/** Number of serialized tokens to expect. */
	private static final int NUM_SERIAL_TOKENS = 2;
	
	/** Package name to remove from IChimpDevice. */
	private String packageName;
	
	/**
	 * Default constructor should only be used when deserializing data.
	 */
	public RemovePackageCommand() {
		
	}
	
	/**
	 * Create a new Remove Package Command.
	 * @param packageName Name of package to remove from IChimpDevice.
	 */
	public RemovePackageCommand(String packageName) {
		this.packageName = packageName;
	}

	/* (non-Javadoc)
	 * @see com.ebay.testdemultiplexer.device.commands.DeviceCommand#execute(com.ebay.testdemultiplexer.connection.TestDevice)
	 */
	public void execute(TestDevice device) {
		device.getIChimpDevice().removePackage(packageName);
	}

	/* (non-Javadoc)
	 * @see com.ebay.testdemultiplexer.device.commands.recorder.CommandDeserializer#deserializeCommand(java.lang.String)
	 */
	public boolean deserializeCommand(String data) {
		
		String[] tokens = 
				data.split(TestDemultiplexerConstants.SERIAL_SEPARATOR);
		
		if (tokens.length != NUM_SERIAL_TOKENS) {
			return false;
		} else if (!tokens[0].equals(SERIALIZED_KEY)) {
			return false;
		}
		
		packageName = tokens[1];
		
		return true;
	}
	
	/* (non-Javadoc)
	 * @see com.ebay.testdemultiplexer.device.commands.recorder.CommandSerializer#serializeCommand()
	 */
	public String serializeCommand() {
		
		String serialized = SERIALIZED_KEY;
		serialized += TestDemultiplexerConstants.SERIAL_SEPARATOR;
		serialized += packageName;
		
		return serialized;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return SERIALIZED_KEY;
	}
}
