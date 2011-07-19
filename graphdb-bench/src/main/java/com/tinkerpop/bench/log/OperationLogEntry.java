package com.tinkerpop.bench.log;

public class OperationLogEntry {

	private int opId = -1;
	private String name = null;
	private String type = null;
	private String[] args = null;
	private long time = -1;
	private String result = null;
	private long memory = -1;

	public OperationLogEntry(int opId, String name, String type, String[] args,
			long time, String result, long memory) {
		super();
		this.opId = opId;
		this.name = name;
		this.type = type;
		this.args = args;
		this.time = time;
		this.result = result;
		this.memory = memory;
	}

	public int getOpId() {
		return opId;
	}

	public String getName() {
		return name;
	}

	public String getType() {
		return type;
	}

	public String[] getArgs() {
		return args;
	}

	public long getTime() {
		return time;
	}

	public String getResult() {
		return result;
	}

	public long getMemory() {
		return memory;
	}
}
