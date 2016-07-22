package es.tekniker.eefrmwrk.database.sql.model;

public abstract class DBInstance {

	public abstract long getId();

	public String getTableName() {
		return this.getClass().getSimpleName();
	}
}
