package com.example.a04_kotlinapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SqliteHelper(
    context: Context?
) : SQLiteOpenHelper(context, "AndroidApp", null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {
        val createCompanyTable = """
            CREATE TABLE COMPANY(
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name VARCHAR(100),
                industry VARCHAR(50),
                foundedYear INTEGER
            );
        """.trimIndent()

        val createDepartmentTable = """
            CREATE TABLE DEPARTMENT(
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name VARCHAR(100),
                description VARCHAR(200),
                company_id INTEGER,
                FOREIGN KEY (company_id) REFERENCES COMPANY(id) ON DELETE CASCADE
            );
        """.trimIndent()

        db?.execSQL(createCompanyTable)
        db?.execSQL(createDepartmentTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {}

    fun getAllCompanies():ArrayList<CompanyEntity> {
        val lectureDB = readableDatabase
        val queryScript ="""
            SELECT * FROM COMPANY
        """.trimIndent()
        val queryResult = lectureDB.rawQuery(
            queryScript,
            emptyArray()
        )
        val response = arrayListOf<CompanyEntity>()

        if(queryResult.moveToFirst()) {
            do {
                response.add(
                    CompanyEntity(
                        queryResult.getInt(0),
                        queryResult.getString(1),
                        queryResult.getString(2),
                        queryResult.getInt(3)
                    )
                )
            } while(queryResult.moveToNext())
        }
        queryResult.close()
        lectureDB.close()

        return response
    }

    fun getDepartmentsByCompany(company_id: Int):ArrayList<DepartmentEntity> {
        val lectureDB = readableDatabase
        val queryScript ="""
            SELECT * FROM DEPARTMENT WHERE company_id=?
        """.trimIndent()
        val queryResult = lectureDB.rawQuery(
            queryScript,
            arrayOf(company_id.toString())
        )
        val response = arrayListOf<DepartmentEntity>()

        if(queryResult.moveToFirst()) {
            do {
                response.add(
                    DepartmentEntity(
                        queryResult.getInt(0),
                        queryResult.getString(1),
                        queryResult.getString(2),
                        queryResult.getInt(3)
                    )
                )
            } while(queryResult.moveToNext())
        }
        queryResult.close()
        lectureDB.close()

        return response
    }

    fun createCompany(
        name: String,
        industry: String,
        foundedYear: Int
    ): Boolean {
        val writeDB = writableDatabase
        val valuesToStore = ContentValues()
        valuesToStore.put("name", name)
        valuesToStore.put("industry", industry)
        valuesToStore.put("foundedYear", foundedYear)

        val storeResult = writeDB.insert(
            "COMPANY",
            null,
            valuesToStore
        )
        writeDB.close()

        return storeResult.toInt() != -1
    }

    fun createDepartment(
        name: String,
        description: String,
        company_id: Int
    ): Boolean {
        val writeDB = writableDatabase
        val valuesToStore = ContentValues()
        valuesToStore.put("name", name)
        valuesToStore.put("description", description)
        valuesToStore.put("company_id", company_id)

        val storeResult = writeDB.insert(
            "DEPARTMENT",
            null,
            valuesToStore
        )
        writeDB.close()

        return storeResult.toInt() != -1
    }

    fun updateCompany(
        id: Int,
        name: String,
        industry: String,
        foundedYear: Int
    ): Boolean {
        val writeDB = writableDatabase
        val valuesToUpdate = ContentValues()
        valuesToUpdate.put("name", name)
        valuesToUpdate.put("industry", industry)
        valuesToUpdate.put("foundedYear", foundedYear)

        val parametersUpdateQuery = arrayOf(id.toString())
        val updateResult = writeDB.update(
            "COMPANY",
            valuesToUpdate,
            "id=?",
            parametersUpdateQuery
        )
        writeDB.close()

        return updateResult != -1
    }

    fun updateDepartment(
        id: Int,
        name: String,
        description: String,
        company_id: Int
    ): Boolean {
        val writeDB = writableDatabase
        val valuesToUpdate = ContentValues()
        valuesToUpdate.put("name", name)
        valuesToUpdate.put("description", description)
        valuesToUpdate.put("company_id", company_id)

        val parametersUpdateQuery = arrayOf(id.toString())
        val updateResult = writeDB.update(
            "DEPARTMENT",
            valuesToUpdate,
            "id=?",
            parametersUpdateQuery
        )
        writeDB.close()

        return updateResult != -1
    }

    fun deleteCompany(id: Int): Boolean {
        val writeDB = writableDatabase
        val parametersDeleteQuery = arrayOf(id.toString())
        val deleteResult = writeDB.delete(
            "COMPANY",
            "id=?",
            parametersDeleteQuery
        )
        writeDB.close()

        return deleteResult != -1
    }

    fun deleteDepartment(id: Int): Boolean {
        val writeDB = writableDatabase
        val parametersDeleteQuery = arrayOf(id.toString())
        val deleteResult = writeDB.delete(
            "DEPARTMENT",
            "id=?",
            parametersDeleteQuery
        )
        writeDB.close()

        return deleteResult != -1
    }
}