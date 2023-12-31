/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.commons.compress.archivers.ar;

import java.io.File;

import org.apache.commons.compress.archivers.ArchiveEntry;

/**
 * Represents an archive entry in the "ar" format.
 * 
 * Each AR archive starts with "!<arch>" followed by a LF. After these 8 bytes
 * the archive entries are listed. The format of an entry header is as it follows:
 * 
 * <pre>
 * START BYTE   END BYTE    NAME                    FORMAT      LENGTH
 * 0            15          File name               ASCII       16
 * 16           27          Modification timestamp  Decimal     12
 * 28           33          Owner ID                Decimal     6
 * 34           39          Group ID                Decimal     6
 * 40           47          File mode               Octal       8
 * 48           57          File size (bytes)       Decimal     10
 * 58           59          File magic              \140\012    2
 * </pre>
 * 
 * This specifies that an ar archive entry header contains 60 bytes.
 * 
 * Due to the limitation of the file name length to 16 bytes GNU and BSD has
 * their own variants of this format. This formats are currently not supported
 * and file names with a bigger size than 16 bytes are not possible at the
 * moment.
 * 
 * @Immutable
 */
public class ArArchiveEntry implements ArchiveEntry {

    /** The header for each entry */
    //public static final String HEADER = "!<arch>\n";

    /** The trailer for each entry */
    //public static final String TRAILER = "`\012";
    
    /**
     * SVR4/GNU adds a trailing / to names; BSD does not.
     * They also vary in how names longer than 16 characters are represented.
     * (Not yet supported by this implementation)
     */
    private final String name;
	private final int userId;
	private final int groupId;
	private final int mode;
    private static final int DEFAULT_MODE = 33188; // = (octal) 0100644 
	private final long lastModified;
	private final long length;

	public ArArchiveEntry(String name, long length) {
		this(name, length, 0, 0, DEFAULT_MODE, 0);
	}
	
	public ArArchiveEntry(String name, long length, int userId, int groupId, int mode, long lastModified) {
		this.name = name;
		this.length = length;
		this.userId = userId;
		this.groupId = groupId;
		this.mode = mode;
		this.lastModified = lastModified;
	}

    public long getSize() {
		return this.getLength();
	}
	
	public String getName() {
		return name;
	}
	
	public int getUserId() {
		return userId;
	}
	
	public int getGroupId() {
		return groupId;
	}
	
	public int getMode() {
		return mode;
	}
	
	public long getLastModified() {
		return lastModified;
	}
	
	public long getLength() {
		return length;
	}

	public boolean isDirectory() {
		return false;
	}
}
