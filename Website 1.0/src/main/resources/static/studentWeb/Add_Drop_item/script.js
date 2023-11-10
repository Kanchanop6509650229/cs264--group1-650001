document.addEventListener("DOMContentLoaded", function () {
    // Check if the user is authenticated
    const isAuthenticated = sessionStorage.getItem("authenticated");
    if (isAuthenticated !== "true") {
        // If not authenticated, redirect to the login page
        window.location.href = "../../index.html";
        return;
    } else {
        // User is authenticated, continue with displaying content
        const urlParams = new URLSearchParams(window.location.search);
        const displayname_th = urlParams.get("displayname_th");
        const user_type = urlParams.get("type");
        const user_id = urlParams.get("id");
        const user_faculty = urlParams.get("faculty");
        const user_department = urlParams.get("department");

        const firstNameInput = document.getElementById("firstName");
        const lastNameInput = document.getElementById("lastName");
        const idInput = document.getElementById("studentId");
        const facultyInput = document.getElementById("faculty");
        const departmentInput = document.getElementById("department");

        // Function to fetch data from SQL by user_id
        const fetchDataFromSQL = () => {
            // Send an AJAX request to fetch data from SQL based on user_id
            $.ajax({
                type: "GET", // You may need to change the HTTP method to GET or POST as per your API design
                url: `/api/student/getStudentData?id=${user_id}`, // Replace with your actual endpoint
                success: function (data) {
                    // Check if data exists
                    if (data) {
                        // Data found in SQL, fill the input fields
                        firstNameInput.value = data.firstName;
                        lastNameInput.value = data.lastName;
                        idInput.value = data.studentId;
                        facultyInput.value = data.faculty;
                        departmentInput.value = data.department;
                    } else {
                        // Data not found in SQL, fetch from API
                        fetchDataFromAPI();
                    }
                },
                error: function (error) {
                    // Handle error
                    console.error("Error fetching data from SQL:", error);
                    fetchDataFromAPI();
                },
            });
        };

        // Function to fetch data from the API
        const fetchDataFromAPI = () => {
            // Send an AJAX request to fetch data from the API
            $.ajax({
                type: "GET", // You may need to change the HTTP method to GET or POST as per your API design
                url: `/api/student/getStudentDataFromAPI?id=${user_id}`, // Replace with your actual endpoint
                success: function (data) {
                    if (data) {
                        // Data found in the API, fill the input fields
                        firstNameInput.value = data.firstName;
                        lastNameInput.value = data.lastName;
                        idInput.value = data.studentId;
                        facultyInput.value = data.faculty;
                        departmentInput.value = data.department;
                    }
                },
                error: function (error) {
                    // Handle error
                    console.error("Error fetching data from API:", error);
                },
            });
        };

        // Fetch data from SQL or API based on user_id
        fetchDataFromSQL();

        // Set the values in the <h2> elements
        document.getElementById("name").textContent = displayname_th;
        const typeElement = document.getElementById("type");
        if (user_type === "student") {
            typeElement.textContent = "นักศึกษา";
        }

        if (displayname_th) {
            firstNameInput.disabled = true;
            lastNameInput.disabled = true;
        }
        if (user_id) {
            idInput.disabled = true;
        }
        if (user_faculty) {
            facultyInput.disabled = true;
        }
        if (user_department) {
            departmentInput.disabled = true;
        }
    }
});


function logout() {
  // Update the sessionStorage variable to indicate the user is not authenticated
  sessionStorage.setItem("authenticated", "false");

  // Redirect to the login page or any other desired page
  window.location.href = "../index.html"; // Change the URL as needed
}

function toggleEditing() {
    // Get references to the input fields by their IDs
    const studentYearInput = document.getElementById("studentYear");
    const addressNumberInput = document.getElementsByName("addressNumber")[0];
    const mooInput = document.getElementsByName("moo")[0];
    const tumbolInput = document.getElementsByName("tumbol")[0];
    const amphurInput = document.getElementsByName("amphur")[0];
    const provinceInput = document.getElementsByName("province")[0];
    const postalCodeInput = document.getElementsByName("postalCode")[0];
    const mobilePhoneInput = document.getElementsByName("mobilePhone")[0];
    const phoneInput = document.getElementsByName("phone")[0];
    const advisorInput = document.getElementById("teacher");

    // Function to toggle the disabled attribute
    function toggleDisabled(inputElement) {
        inputElement.disabled = !inputElement.disabled;
    }

    // Toggle the disabled attribute for each input field
    toggleDisabled(studentYearInput);
    toggleDisabled(addressNumberInput);
    toggleDisabled(mooInput);
    toggleDisabled(tumbolInput);
    toggleDisabled(amphurInput);
    toggleDisabled(provinceInput);
    toggleDisabled(postalCodeInput);
    toggleDisabled(mobilePhoneInput);
    toggleDisabled(phoneInput);
    toggleDisabled(advisorInput);
}

function submitForm(event) {
    event.preventDefault(); // Prevent the default form submission

    // Check if all input fields are disabled
        if (areAllInputFieldsDisabled()) {
            // Display an alert message
            Swal.fire({
                title: "คำเตือน",
                text: "หากต้องการบันทึกข้อมูล โปรดกดปุ่มแก้ไขและกรอกข้อมูลให้ครบถ้วนก่อน",
                icon: "warning",
            });
            return;
        }

    // Create a JSON object to hold the form data
    const jsonData = {};

    // Add values from enabled input fields
    const formData = $("#studentDataForm").serializeArray();
    formData.forEach((field) => {
        if (field.name === "studentYear") {
            jsonData[field.name] = parseInt(field.value); // Parse as integer
        } else {
            jsonData[field.name] = field.value;
        }
    });

    // Manually add values from disabled input fields
    jsonData["studentId"] = document.getElementById("studentId").value;
    jsonData["firstName"] = document.getElementById("firstName").value;
    jsonData["lastName"] = document.getElementById("lastName").value;
    jsonData["faculty"] = document.getElementById("faculty").value;
    jsonData["department"] = document.getElementById("department").value;

    // Send an AJAX request to your controller with the correct data format
    $.ajax({
        type: "POST",
        url: "/api/student/submitStudentData", // Replace with your actual endpoint
        data: JSON.stringify(jsonData), // Convert to JSON format
        contentType: "application/json", // Set content type to JSON
        success: function (response) {
            // Show a success message with SweetAlert2
            Swal.fire({
                title: "Success",
                text: response,
                icon: "success",
                showConfirmButton: false,
                timer: 2000, // Auto-close the alert after 2 seconds
            });
            disableAllInputFields();
        },
        error: function (error) {
            // Show an error message with SweetAlert2
            Swal.fire({
                title: "Error",
                text: "Failed to save data. Please try again.",
                icon: "error",
            });
        },
    });
}

// Function to disable all input fields
function disableAllInputFields() {
    const inputFields = document.querySelectorAll("input");
    inputFields.forEach((input) => {
        input.disabled = true;
    });

    // Also disable the "Submit" button
    document.getElementById("submit-button").disabled = true;
}

// Function to check if all input fields are disabled
function areAllInputFieldsDisabled() {
    const inputFields = document.querySelectorAll("input");
    for (let input of inputFields) {
        if (!input.disabled) {
            return false;
        }
    }
    return true;
}