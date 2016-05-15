package net.glease.chem.simple.scoping;

public class DuplicateElementInScopeException extends ScopeException {
	private static final long serialVersionUID = 1L;
	private final IScoped<?> exsited;
	private final String id;

	public <T extends IScope<?, T>> DuplicateElementInScopeException(IScoped<T> exsited, IScoped<T> added, IScope<?, T> scope, String id) {
		super("Duplicate element in scope. old: " + exsited, scope, added);
		this.exsited = exsited;
		this.id = id;
	}

	public IScoped<?> getExsited() {
		return exsited;
	}

	public String getId() {
		return id;
	}
}
