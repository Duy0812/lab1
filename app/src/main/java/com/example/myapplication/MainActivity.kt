package com.example.myapplication

import android.util.Log
import android.widget.Button
import android.widget.RadioGroup
import android.widget.RadioButton
import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView // <-- Added this import
import android.view.View       // <-- Added this import
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.Calendar


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_linear)

        val mainView = findViewById<View>(R.id.main)
        if (mainView != null) {
            ViewCompat.setOnApplyWindowInsetsListener(mainView) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            }
        }

        val tvDate = findViewById<EditText>(R.id.tvDate)
        tvDate.setOnClickListener {
            showDatePicker(tvDate)
        }

        // 1. Find all the views
        val cbSelectAll = findViewById<android.widget.CheckBox>(R.id.cbSelectAll)
        val cbAndroid = findViewById<android.widget.CheckBox>(R.id.cbAndroid)
        val cbWebsite = findViewById<android.widget.CheckBox>(R.id.cbWebsite)
        val cbJava = findViewById<android.widget.CheckBox>(R.id.cbJava)
        val btnSubmit = findViewById<Button>(R.id.btnSubmit)
        val etName = findViewById<EditText>(R.id.etName)
        val etPhone = findViewById<EditText>(R.id.etPhone)
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etAddress = findViewById<EditText>(R.id.etAddress)
        val rgGender = findViewById<RadioGroup>(R.id.rgGender)

        // Find the new Console TextView
        val tvConsole = findViewById<TextView>(R.id.tvConsole)

        btnSubmit.setOnClickListener {
            // 1. Get text from EditTexts
            val name = etName.text.toString()
            val phone = etPhone.text.toString()
            val email = etEmail.text.toString()
            val address = etAddress.text.toString()
            val date = tvDate.text.toString()

            // 2. Get the selected Gender
            val selectedGenderId = rgGender.checkedRadioButtonId
            val gender = if (selectedGenderId != -1) {
                val selectedRadioButton = findViewById<RadioButton>(selectedGenderId)
                selectedRadioButton.text.toString()
            } else {
                "Chưa chọn"
            }

            // 3. Get the selected courses
            val courses = mutableListOf<String>()
            if (cbAndroid.isChecked) courses.add("Android")
            if (cbWebsite.isChecked) courses.add("Website")
            if (cbJava.isChecked) courses.add("Java cơ bản")
            val coursesText = courses.joinToString(", ")

            // 4. Build the final text string to show on screen
            val resultText = """
                ===== THÔNG TIN ĐĂNG KÝ =====
                Họ tên: $name
                SĐT: $phone
                Email: $email
                Địa chỉ: $address
                Ngày sinh: $date
                Giới tính: $gender
                Khóa học: $coursesText
                =============================
            """.trimIndent()

            // 5. Display the text on the screen and make it visible
            tvConsole.text = resultText
            tvConsole.visibility = View.VISIBLE
        }

        // 2. Listen for clicks on the "Select All" checkbox
        cbSelectAll.setOnCheckedChangeListener { _, isChecked ->
            // If cbSelectAll is checked (true), these will become true.
            // If cbSelectAll is unchecked (false), these will become false.
            cbAndroid.isChecked = isChecked
            cbWebsite.isChecked = isChecked
            cbJava.isChecked = isChecked
        }
    }

    // 3. Function to create and show the DatePickerDialog
    private fun showDatePicker(tvDate: EditText) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDay ->
                // Month starts at 0, so we add 1
                val date = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                tvDate.setText(date)
            },
            year, month, day
        )

        datePickerDialog.show()
    }
}