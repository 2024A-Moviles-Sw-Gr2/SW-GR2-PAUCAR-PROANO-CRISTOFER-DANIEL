package com.example.a04_kotlinapp
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class CreateUpdateDepartment : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_update_department)

        val name = findViewById<EditText>(R.id.input_department_name)
        val description = findViewById<EditText>(R.id.input_department_description)
        val companyId = findViewById<EditText>(R.id.input_department_company_id)
        val selectedDepartment = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("selectedDepartment", DepartmentEntity::class.java)
        } else {
            intent.getParcelableExtra<DepartmentEntity>("selectedDepartment")
        }
        val selectedCompany = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("selectedCompany", CompanyEntity::class.java)
        } else {
            intent.getParcelableExtra<CompanyEntity>("selectedCompany")
        }
        val create = intent.getBooleanExtra("create", true)

        if(create) {
            companyId.setText(selectedCompany!!.id.toString())

            // Create a department
            val btnCreateUpdateDepartment = findViewById<Button>(R.id.btn_create_update_department)
            btnCreateUpdateDepartment.setOnClickListener{
                DataBase.tables!!.createDepartment(
                    name.text.toString(),
                    description.text.toString(),
                    companyId.text.toString().toInt()
                )
                goToActivity(DepartmentList::class.java, selectedCompany)
            }
        } else {
            name.setText(selectedDepartment!!.name)
            description.setText(selectedDepartment.description)
            companyId.setText(selectedDepartment.company_id.toString())

            // Update a department
            val btnCreateUpdateDepartment = findViewById<Button>(R.id.btn_create_update_department)
            btnCreateUpdateDepartment.setOnClickListener{
                DataBase.tables!!.updateDepartment(
                    selectedDepartment.id,
                    name.text.toString(),
                    description.text.toString(),
                    companyId.text.toString().toInt()
                )
                goToActivity(DepartmentList::class.java, selectedCompany!!)
            }
        }
    }

    private fun goToActivity(
        activityClass: Class<*>,
        dataToPass: CompanyEntity
    ) {
        val intent = Intent(this, activityClass)
        intent.apply {
            putExtra("selectedCompany", dataToPass)
        }
        startActivity(intent)
    }
}
