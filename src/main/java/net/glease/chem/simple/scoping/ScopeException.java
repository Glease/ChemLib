package net.glease.chem.simple.scoping;

public class ScopeException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private final IScope<?, ?> scope;
	private final IScoped<?> issuer;

	public <T extends IScope<?, T>> ScopeException(String message, IScope<?, T> scope, IScoped<T> issuer) {
		super(String.format("%s scope: %s issuer: %s id: %s", message, scope, issuer, issuer.getId()));
		this.scope = scope;
		this.issuer = issuer;
	}

	public <T extends IScope<?, T>>  ScopeException(String message, Throwable cause, IScope<?, T> scope, IScoped<T> issuer) {
		super(String.format("%s scope: %s issuer: %s id: %s cause: %s: %s", message, scope, issuer, issuer.getId(),
				cause.getClass().getName(), cause.getMessage()), cause);
		this.scope = scope;
		this.issuer = issuer;
	}

	public IScope<?, ?> getScope() {
		return scope;
	}

	public IScoped<?> getIssuer() {
		return issuer;
	}
}
