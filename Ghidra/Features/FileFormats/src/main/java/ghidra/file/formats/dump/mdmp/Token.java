/* ###
 * IP: GHIDRA
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ghidra.file.formats.dump.mdmp;

import java.io.IOException;

import ghidra.app.util.bin.StructConverter;
import ghidra.file.formats.dump.DumpFileReader;
import ghidra.program.model.data.DataType;
import ghidra.program.model.data.StructureDataType;
import ghidra.util.exception.DuplicateNameException;

public class Token implements StructConverter {

	public final static String NAME = "MINIDUMP_TOKEN";

	private int tokenSize;
	private int tokenId;
	private long tokenHandle;

	private DumpFileReader reader;
	private long index;

	Token(DumpFileReader reader, long index) throws IOException {
		this.reader = reader;
		this.index = index;

		parse();
	}

	private void parse() throws IOException {
		reader.setPointerIndex(index);

		setTokenSize(reader.readNextInt());
		setTokenId(reader.readNextInt());
		setTokenHandle(reader.readNextInt());

	}

	/**
	 * @see ghidra.app.util.bin.StructConverter#toDataType()
	 */
	@Override
	public DataType toDataType() throws DuplicateNameException {
		StructureDataType struct = new StructureDataType(NAME, 0);

		struct.add(DWORD, 4, "Size", null);
		struct.add(DWORD, 4, "Id", null);
		struct.add(QWORD, 8, "Handle", null);

		return struct;
	}

	public int getTokenSize() {
		return tokenSize;
	}

	public void setTokenSize(int tokenSize) {
		this.tokenSize = tokenSize;
	}

	public int getTokenId() {
		return tokenId;
	}

	public void setTokenId(int tokenId) {
		this.tokenId = tokenId;
	}

	public long getTokenHandle() {
		return tokenHandle;
	}

	public void setTokenHandle(long tokenHandle) {
		this.tokenHandle = tokenHandle;
	}

}
