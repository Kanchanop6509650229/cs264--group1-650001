document.addEventListener("DOMContentLoaded", function () {
    // Check if the user is authenticated
    const isAuthenticated = sessionStorage.getItem("authenticated");
    if (isAuthenticated !== "true") {
      // If not authenticated, redirect to the login page
      window.location.href = "../index.html";
      return;
    } else {

      let urlParamsString = sessionStorage.getItem("urlParams");
              let urlParams;

              if (urlParamsString) {
                  // If stored, parse the string to create a URLSearchParams object
                  urlParams = new URLSearchParams(urlParamsString);
              } else {
                  // If not stored, create a new URLSearchParams object
                  urlParams = new URLSearchParams(window.location.search);

                  // Store the URL parameters in sessionStorage
                  sessionStorage.setItem("urlParams", urlParams.toString());
              }
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
      // Get the "ขอเพิ่ม/ถอนรายวิชา (drop w)" link element
      const addDropLink = document.getElementById('addDropLink');

      // Add a click event listener to the link
      addDropLink.addEventListener('click', function (event) {
          // Prevent the default behavior of the link (i.e., prevent it from navigating)
          event.preventDefault();

          // Construct the URL for the next page with the studentId parameter
          const nextPageUrl = `Add_Drop_item/add_drop.html?studentId=${user_id}`;

          // Redirect to the next page
          window.location.href = nextPageUrl;
      });
      const QuitFormLink = document.getElementById('QuitForm');
  
        // Add a click event listener to the link
        QuitFormLink.addEventListener('click', function (event) {
            // Prevent the default behavior of the link (i.e., prevent it from navigating)
            event.preventDefault();
  
            // Construct the URL for the next page with the studentId parameter
            const nextPageUrl = `QuitForm/QuitForm.html?studentId=${user_id}`;
  
            // Redirect to the next page
            window.location.href = nextPageUrl;
        });
      
    // Fetch student data by studentId
    $.ajax({
      type: "GET",
      url: `/api/student/getStudentDataById?studentId=${user_id}`,
      contentType: "application/json",
      success: function (studentData) {
          if (studentData) {
              disableAllInputs();
              // Populate the input fields with fetched data
              document.getElementById("studentYear").value = studentData.studentYear;
              document.getElementById("addressNumber").value = studentData.addressNumber;
              document.getElementById("moo").value = studentData.moo;
              document.getElementById("tumbol").value = studentData.tumbol;
              document.getElementById("amphur").value = studentData.amphur;
              document.getElementById("province").value = studentData.province;
              document.getElementById("postalCode").value = studentData.postalCode;
              document.getElementById("mobilePhone").value = studentData.mobilePhone;
              document.getElementById("phone").value = studentData.phone;
              document.getElementById("teacher").value = studentData.advisor;
              // (Populate other fields accordingly)
          } else {
              console.log("No data received for studentId:", user_id);
              // Log the error to the console for debugging
          }
      },
      error: function (error) {
          console.log("Error fetching student data:", error);

          // Log the error to the console for debugging
          Swal.fire({ 
              icon: "error",
              title: "Error",
              text: "Failed to fetch student data. Please check the console for more details.",
          });
      },
  });
  // Add an event listener to check for unsaved changes when the page is about to be unloaded
      window.addEventListener('beforeunload', function (e) {
          const enabledInputs = document.querySelectorAll('input:enabled');
          if (enabledInputs.length > 0) {
              const confirmationMessage = "ข้อมูลอาจยังไม่ถูกบันทุุึก ยืนยันจะออกจากหน้าเว็ปไซต์หรือไม่";
              e.returnValue = confirmationMessage; // Standard for most browsers
              return confirmationMessage; // For some older browsers
          }
      });
      // Add an event listener for the 'unload' event to set sessionStorage if the user chooses to stay on the page
          window.addEventListener('unload', function () {
              const enabledInputs = document.querySelectorAll('input:enabled');
              if (enabledInputs.length > 0) {
                  // If the user stays on the page, set sessionStorage to indicate the user is still authenticated
                  sessionStorage.setItem("authenticated", "true");
              }
          });
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
}

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

    // Send an AJAX request to your controller with the correct data format
    $.ajax({
      type: "POST",
      url: "/api/student/submitStudentData", // Replace with your actual endpoint
      data: JSON.stringify(jsonData), // Convert to JSON format
      contentType: "application/json", // Set content type to JSON
      success: function (response) {
        disableAllInputs();
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

  function disableAllInputs() {
      const enabledInputs = document.querySelectorAll('input:enabled');
      enabledInputs.forEach((input) => {
          input.disabled = true;
      });
  }