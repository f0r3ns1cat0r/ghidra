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
package ghidra.trace.database.target.iface;

import ghidra.trace.database.target.DBTraceObject;
import ghidra.trace.database.target.DBTraceObjectInterface;
import ghidra.trace.model.target.TraceObject;
import ghidra.trace.model.target.iface.TraceAggregate;
import ghidra.trace.util.TraceChangeRecord;

public class DBTraceObjectAggregate implements TraceAggregate, DBTraceObjectInterface {
	private final DBTraceObject object;

	public DBTraceObjectAggregate(DBTraceObject object) {
		this.object = object;
	}

	@Override
	public TraceObject getObject() {
		return object;
	}

	@Override
	public TraceChangeRecord<?, ?> translateEvent(TraceChangeRecord<?, ?> rec) {
		return null;
	}
}
