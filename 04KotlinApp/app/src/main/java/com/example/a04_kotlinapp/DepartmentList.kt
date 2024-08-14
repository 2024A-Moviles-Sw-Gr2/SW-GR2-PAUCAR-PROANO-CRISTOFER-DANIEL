package com.example.a04_kotlinapp

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog

class DepartmentList : AppCompatActivity() {

    private var allDepartments: ArrayList<DepartmentEntity> = arrayListOf()
    private var adapter: ArrayAdapter<DepartmentEntity>? = null
    private var selectedRegisterPosition = -1
    private var selectedCompany: CompanyEntity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_department_list)

        selectedCompany = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("selectedCompany", CompanyEntity::class.java)
        } else {
            intent.getParcelableExtra<CompanyEntity>("selectedCompany")
        }

        val departmentsCompany = findViewById<TextView>(R.id.txt_view_company_name)
        departmentsCompany.text = selectedCompany!!.name

        DataBase.tables = SqliteHelper(this)
        val departmentsList = findViewById<ListView>(R.id.list_departments)
        allDepartments = DataBase.tables!!.getDepartmentsByCompany(selectedCompany!!.id)
        adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            allDepartments
        )

        departmentsList.adapter = adapter
        adapter!!.notifyDataSetChanged() // To update the UI

        val btnRedirectCreateDepartment = findViewById<Button>(R.id.btn_redirect_create_department)
        btnRedirectCreateDepartment.setOnClickListener {
            goToActivity(CreateUpdateDepartment::class.java, null, selectedCompany!!)
        }
        val btnBackToMain = findViewById<Button>(R.id.btn_back_to_main)
        btnBackToMain.setOnClickListener {
            goToActivity(MainActivity::class.java)
        }

        registerForContextMenu(departmentsList)
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)

        // Set options for the context menu
        val inflater = menuInflater
        inflater.inflate(R.menu.department_menu, menu)

        // Get ID of the selected item of the list
        val register = menuInfo as AdapterView.AdapterContextMenuInfo
        selectedRegisterPosition = register.position
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.mi_edit_department -> {
                goToActivity(CreateUpdateDepartment::class.java, allDepartments[selectedRegisterPosition], selectedCompany!!, false)
                return true
            }
            R.id.mi_delete_department -> {
                openDialog(allDepartments[selectedRegisterPosition].id)
                return true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    private fun goToActivity(
        activityClass: Class<*>,
        dataToPass: DepartmentEntity? = null,
        dataToPass2: CompanyEntity? = null,
        create: Boolean = true
    ) {
        val intent = Intent(this, activityClass)
        if (dataToPass != null) {
            intent.apply {
                putExtra("selectedDepartment", dataToPass)
            }
        }
        if (dataToPass2 != null) {
            intent.apply {
                putExtra("selectedCompany", dataToPass2)
            }
        }
        intent.apply {
            putExtra("create", create)
        }
        startActivity(intent)
    }

    private fun openDialog(registerIndex: Int) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("¿Está seguro de eliminar el departamento?")
        builder.setPositiveButton(
            "Eliminar"
        ) { _, _ ->
            DataBase.tables!!.deleteDepartment(registerIndex)
            allDepartments.clear()
            allDepartments.addAll(DataBase.tables!!.getDepartmentsByCompany(selectedCompany!!.id))
            adapter!!.notifyDataSetChanged()
        }
        builder.setNegativeButton("Cancelar", null)

        builder.create().show()
    }

}