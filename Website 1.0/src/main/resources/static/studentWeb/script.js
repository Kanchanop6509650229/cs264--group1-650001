document.addEventListener("DOMContentLoaded", function () {
    // Function to populate input fields from localStorage
    function populateInputFieldsFromStorage() {
      const firstNameInput = document.getElementById("firstName");
      const lastNameInput = document.getElementById("lastName");
      const idInput = document.getElementById("studentId");
      const facultyInput = document.getElementById("faculty");
      const departmentInput = document.getElementById("department");
  
      // Check if input values are stored in localStorage
      if (localStorage.getItem("firstName")) {
        firstNameInput.value = localStorage.getItem("firstName");
      }
  
      if (localStorage.getItem("lastName")) {
        lastNameInput.value = localStorage.getItem("lastName");
      }
  
      if (localStorage.getItem("studentId")) {
        idInput.value = localStorage.getItem("studentId");
      }
  
      if (localStorage.getItem("faculty")) {
        facultyInput.value = localStorage.getItem("faculty");
      }
  
      if (localStorage.getItem("department")) {
        departmentInput.value = localStorage.getItem("department");
      }
    }
  
    // Check if the user is authenticated
    const isAuthenticated = sessionStorage.getItem("authenticated");
    if (isAuthenticated !== "true") {
      // If not authenticated, redirect to the login page
      window.location.href = "../index.html";
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
      const splitName = displayname_th.split(" ");
      firstNameInput.value = splitName[0];
      // Check if the input has a value and disable it
      lastNameInput.value = splitName[1];
      idInput.value = user_id;
      facultyInput.value = user_faculty;
      departmentInput.value = user_department;
  
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
  
      // Populate input fields from localStorage
      populateInputFieldsFromStorage();
    }
  });

function logout() {
  // Update the sessionStorage variable to indicate the user is not authenticated
  sessionStorage.setItem("authenticated", "false");

  // Redirect to the login page or any other desired page
  window.location.href = "../index.html"; // Change the URL as needed
}

let unsavedChanges = false;

// Function to check if any input fields are enabled
function checkUnsavedChanges() {
  const enabledInputs = document.querySelectorAll('input:enabled');
  unsavedChanges = enabledInputs.length > 0;
}

// Add an event listener to check for unsaved changes when the page is about to be unloaded
window.addEventListener('beforeunload', (e) => {
  if (unsavedChanges) {
    e.preventDefault();
    e.returnValue = "หากออกจากหน้าเว็บไซต์ข้อมูลของคุณจะไม่ถูกบันทึก โปรดกดบันทึกก่อนจะออกจากหน้าเว็บไซต์";
  }
});

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

    // Check if the inputs are currently disabled, only enable them if they are
    if (!studentYearInput.disabled) {
        return;
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

    checkUnsavedChanges();
}

// Add an event listener for form input changes to detect unsaved changes
document.getElementById("studentDataForm").addEventListener('change', checkUnsavedChanges);

// Save input values to localStorage when the form is submitted
function submitForm(event) {
    event.preventDefault(); // Prevent the default form submission
  
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
  
    // Save the input values to localStorage
    localStorage.setItem("firstName", jsonData["firstName"]);
    localStorage.setItem("lastName", jsonData["lastName"]);
    localStorage.setItem("studentId", jsonData["studentId"]);
    localStorage.setItem("faculty", jsonData["faculty"]);
    localStorage.setItem("department", jsonData["department"]);
  
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