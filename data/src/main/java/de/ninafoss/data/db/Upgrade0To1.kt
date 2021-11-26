package de.ninafoss.data.db

import org.greenrobot.greendao.database.Database
import javax.inject.Inject
import javax.inject.Singleton
import de.ninafoss.data.db.Sql.SqlCreateTableBuilder.ForeignKeyBehaviour

@Singleton
internal class Upgrade0To1 @Inject constructor() : DatabaseUpgrade(0, 1) {

	override fun internalApplyTo(db: Database, origin: Int) {
		db.beginTransaction()
		try {
			createLocationEntityTable(db)
			createMessageEntityTable(db)
			db.setTransactionSuccessful()
		} finally {
			db.endTransaction()
		}
	}

	private fun createLocationEntityTable(db: Database) {
		Sql.createTable("LOCATION_ENTITY") //
			.id() //
			.requiredText("NAME") //
			.requiredText("CODE") //
			.executeOn(db)
	}

	private fun createMessageEntityTable(db: Database) {
		Sql.createTable("MESSAGE_ENTITY") //
			.id() //
			.optionalInt("LOCATION_ID") //
			.requiredText("REMOTE_ID") //
			.requiredInt("REMOTE_VERSION") //
			.optionalText("INSTRUCTION") //
			.requiredText("SEVERITY") //
			.optionalText("CONTACT") //
			.requiredText("HEADLINE") //
			.requiredText("MESSAGE") //
			.requiredText("PROVIDER") //
			.requiredText("MSG_TYPE") //
			.requiredText("SENT_DATE") //
			.foreignKey("LOCATION_ID", "LOCATION_ENTITY", ForeignKeyBehaviour.ON_DELETE_SET_NULL) //
			.executeOn(db)
	}

}
