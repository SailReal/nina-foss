package de.ninafoss.data.db

import org.greenrobot.greendao.database.Database
import javax.inject.Inject
import javax.inject.Singleton
import de.ninafoss.data.db.Sql.SqlCreateTableBuilder.ForeignKeyBehaviour

@Singleton
internal class Upgrade0To1 @Inject constructor() : DatabaseUpgrade(0, 1) {

	override fun internalApplyTo(db: Database, origin: Int) {
		createLocationEntityTable(db)
		createMessageEntityTable(db)
	}

	private fun createLocationEntityTable(db: Database) {
		Sql.createTable("CLOUD_ENTITY") //
			.id() //
			.requiredText("TYPE") //
			.optionalText("ACCESS_TOKEN") //
			.optionalText("WEBDAV_URL") //
			.optionalText("USERNAME") //
			.optionalText("WEBDAV_CERTIFICATE") //
			.executeOn(db)
	}

	private fun createMessageEntityTable(db: Database) {
		Sql.createTable("VAULT_ENTITY") //
			.id() //
			.optionalInt("FOLDER_CLOUD_ID") //
			.optionalText("FOLDER_PATH") //
			.optionalText("FOLDER_NAME") //
			.requiredText("CLOUD_TYPE") //
			.optionalText("PASSWORD") //
			.foreignKey("FOLDER_CLOUD_ID", "CLOUD_ENTITY", ForeignKeyBehaviour.ON_DELETE_SET_NULL) //
			.executeOn(db)
		Sql.createUniqueIndex("IDX_VAULT_ENTITY_FOLDER_PATH_FOLDER_CLOUD_ID") //
			.on("VAULT_ENTITY") //
			.asc("FOLDER_PATH") //
			.asc("FOLDER_CLOUD_ID") //
			.executeOn(db)
	}

}
