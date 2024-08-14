package com.example.a04_kotlinapp

import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private var allCompanies: ArrayList<CompanyEntity> = arrayListOf()
    private var adapter: ArrayAdapter<CompanyEntity>? = null
    private var selectedRegisterPosition = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        DataBase.tables = SqliteHelper(this)
        val companiesList = findViewById<ListView>(R.id.list_companies)
        allCompanies = DataBase.tables!!.getAllCompanies()
        adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            allCompanies
        )

        companiesList.adapter = adapter
        adapter!!.notifyDataSetChanged() // To update the UI

        val btnRedirectCreateCompany = findViewById<Button>(R.id.btn_redirect_create_company)
        btnRedirectCreateCompany.setOnClickListener {
            goToActivity(CreateUpdateCompany::class.java)
        }

        val btnViewMap = findViewById<Button>(R.id.btn_view_map)
        btnViewMap.setOnClickListener {
            goToActivity(MapsActivity::class.java)
        }

        registerForContextMenu(companiesList)
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)

        // Set options for the context menu
        val inflater = menuInflater
        inflater.inflate(R.menu.company_menu, menu)

        // Get ID of the selected item of the list
        val register = menuInfo as AdapterView.AdapterContextMenuInfo
        selectedRegisterPosition = register.position
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.mi_edit_company -> {
                goToActivity(CreateUpdateCompany::class.java, allCompanies[selectedRegisterPosition])
                return true
            }
            R.id.mi_delete_company -> {
                openDialog(allCompanies[selectedRegisterPosition].id)
                return true
            }
            R.id.mi_company_departments -> {
                goToActivity(DepartmentList::class.java, allCompanies[selectedRegisterPosition])
                return true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    private fun goToActivity(
        activityClass: Class<*>,
        dataToPass: CompanyEntity? = null
    ) {
        val intent = Intent(this, activityClass)
        if (dataToPass != null) {
            intent.apply {
                putExtra("selectedCompany", dataToPass)
            }
        }
        startActivity(intent)
    }

    private fun openDialog(registerIndex: Int) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("¿Está seguro de eliminar la empresa?")
        builder.setPositiveButton(
            "Eliminar"
        ) { _, _ ->
            DataBase.tables!!.deleteCompany(registerIndex)
            allCompanies.clear()
            allCompanies.addAll(DataBase.tables!!.getAllCompanies())
            adapter!!.notifyDataSetChanged()
        }
        builder.setNegativeButton("Cancelar", null)

        builder.create().show()
    }
}
