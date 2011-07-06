package com.tinkerpop.bench.operationFactory;

/**
 * @author Alex Averbuch (alex.averbuch@gmail.com)
 */
public class OperationArgs {

	private Object[] args = null;
	private Class<?> type = null;
	private String tag = null;

	public OperationArgs(Object[] args, Class<?> type) {
		this(args, type, "");
	}

	public OperationArgs(Object[] args, Class<?> type, String tag) {
		super();
		this.args = args;
		this.type = type;
		this.tag = tag;
	}

	public Object[] getArgs() {
		return args;
	}

	public Class<?> getType() {
		return type;
	}

	public String getName() {
		return ("".equals(tag)) ? type.getSimpleName() : type.getSimpleName()
				+ "-" + tag;
	}

}
