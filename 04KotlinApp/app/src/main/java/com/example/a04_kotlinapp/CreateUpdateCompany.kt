package com.example.a04_kotlinapp

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class CreateUpdateCompany : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_update_company)

        val name = findViewById<EditText>(R.id.input_company_name)
        val industry = findViewById<EditText>(R.id.input_company_industry)
        val foundedYear = findViewById<EditText>(R.id.input_company_founded_year)
        val latitude = findViewById<EditText>(R.id.input_company_latitude)
        val longitude = findViewById<EditText>(R.id.input_company_longitude)

        val selectedCompany = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("selectedCompany", CompanyEntity::class.java)
        } else {
            intent.getParcelableExtra<CompanyEntity>("selectedCompany")
        }

        if (selectedCompany == null) {
            // Create a company
            val btnCreateUpdateCompany = findViewById<Button>(R.id.btn_create_update_company)
            btnCreateUpdateCompany.setOnClickListener {
                DataBase.tables!!.createCompany(
                    name.text.toString(),
                    industry.text.toString(),
                    foundedYear.text.toString().toInt(),
                    latitude.text.toString().toDouble(),
                    longitude.text.toString().toDouble()
                )
                goToActivity(MainActivity::class.java)
            }
        } else {
            name.setText(selectedCompany.name)
            industry.setText(selectedCompany.industry)
            foundedYear.setText(selectedCompany.foundedYear.toString())
            latitude.setText(selectedCompany.latitude.toString())
            longitude.setText(selectedCompany.longitude.toString())

            // Update a company
            val btnCreateUpdateCompany = findViewById<Button>(R.id.btn_create_update_company)
            btnCreateUpdateCompany.setOnClickListener {
                DataBase.tables!!.updateCompany(
                    selectedCompany.id,
                    name.text.toString(),
                    industry.text.toString(),
                    foundedYear.text.toString().toInt(),
                    latitude.text.toString().toDouble(),
                    longitude.text.toString().toDouble()
                )
                goToActivity(MainActivity::class.java)
            }
        }
    }

    private fun goToActivity(activityClass: Class<*>) {
        val intent = Intent(this, activityClass)
        startActivity(intent)
    }
}
